package com.defaultapps.android_cryptocurrency_prices.ui.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BaseActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

import java.util.List;
import java.util.concurrent.TimeoutException;

import timber.log.Timber;


public class MainActivity extends BaseActivity implements MainContract.MainView {

    private ResponsePresenterImpl presenter;

    private ProgressBar progressBar;
    private CoinsAdapter coinsAdapter;
    private RecyclerView coinsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout errorLayout;
    private TextView errorTextView;
    private Button btnRetry;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 29;
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new ResponsePresenterImpl();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coinsRecyclerView = findViewById(R.id.coinsRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        errorLayout = findViewById(R.id.errorLayout);
        errorTextView = findViewById(R.id.error_txt);
        btnRetry = findViewById(R.id.buttonRetry);

        coinsAdapter = new CoinsAdapter();
        linearLayoutManager = new LinearLayoutManager(this);
        coinsRecyclerView.setLayoutManager(linearLayoutManager);
        coinsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        coinsRecyclerView.setAdapter(coinsAdapter);

        coinsRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                presenter.overview(currentPage * 50);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.overview(PAGE_START);
            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.overview(PAGE_START);
            }
        });

        presenter.overview(currentPage);
    }

    @Override
    public void showCoins(List<CoinModel> coins) {
        Timber.d("showCoins");
        if (currentPage == 0) {
            progressBar.setVisibility(View.GONE);
        } else {
            coinsAdapter.removeLoadingFooter();
            isLoading = false;
        }
        coinsAdapter.addAll(coins);
        if (currentPage <= TOTAL_PAGES) coinsAdapter.addLoadingFooter();
        else isLastPage = true;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected Presenter providePresenter() {
        return presenter;
    }

    @Override
    public void showErrorView(Throwable throwable) {
        if (errorLayout.getVisibility() == View.GONE) {
            swipeRefreshLayout.setRefreshing(false);
            if (!coinsAdapter.isEmpty()) {
                coinsAdapter.clear();
            }
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            errorTextView.setText(fetchErrorMessage(throwable));
        }
    }

    public void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }
}

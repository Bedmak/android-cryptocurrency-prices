package com.defaultapps.android_cryptocurrency_prices.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.android_cryptocurrency_prices.App;
import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;

import com.defaultapps.android_cryptocurrency_prices.ui.base.BaseActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;
import com.defaultapps.android_cryptocurrency_prices.ui.detailed.DetailedActivity;

import java.net.SocketTimeoutException;
import java.util.List;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;


public class MainActivity extends BaseActivity implements MainContract.MainView {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.coinsRecyclerView)
    RecyclerView coinsRecyclerView;

    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;

    @BindView(R.id.error_txt)
    TextView errorTextView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    ResponsePresenterImpl presenter;

    private CoinsAdapter coinsAdapter;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 29;
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((App) getApplicationContext()).getComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        presenter.overview(currentPage);
    }

    private void initViews() {

        coinsAdapter = new CoinsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.overview(PAGE_START));
    }

    @OnClick(R.id.buttonRetry)
    void onRetryClick() {
        presenter.overview(PAGE_START);
    }

    @Override
    public void showCoins(List<CoinModel> coins) {
        Timber.d("showCoins %s", currentPage);
        swipeRefreshLayout.setRefreshing(false);
        if (currentPage == 0) {
            progressBar.setVisibility(View.GONE);
        } else {
            coinsAdapter.removeLoadingFooter();
            isLoading = false;
        }
        coinsAdapter.addAll(coins);
        if (currentPage <= TOTAL_PAGES) coinsAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    protected Presenter providePresenter() {
        return presenter;
    }

    @Override
    public void showErrorView(Throwable throwable) {
        if (errorLayout.getVisibility() == View.GONE) {
            swipeRefreshLayout.setRefreshing(false);
            errorLayout.setVisibility(View.VISIBLE);
            if (!coinsAdapter.isEmpty()) {
                coinsAdapter.clear();
            }
            progressBar.setVisibility(View.GONE);

            errorTextView.setText(fetchErrorMessage(throwable));
        }
    }

    @Override
    public void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDetailed(int position) {
        Timber.d("onClick - " + position + " position");
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra(DetailedActivity.COIN_NO, position);
        startActivity(intent);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof SocketTimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }
}

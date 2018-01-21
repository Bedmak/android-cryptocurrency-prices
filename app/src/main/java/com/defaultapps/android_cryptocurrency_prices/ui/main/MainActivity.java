package com.defaultapps.android_cryptocurrency_prices.ui.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BaseActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

import java.util.List;

import timber.log.Timber;


public class MainActivity extends BaseActivity implements MainContract.MainView {

    private RecyclerView coinsRecyclerView;
    private CoinsAdapter coinsAdapter;
    private ResponsePresenterImpl presenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;

    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 0;
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;
    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;
    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES = 29;
    // indicates the current page which Pagination is fetching.
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new ResponsePresenterImpl();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coinsRecyclerView = findViewById(R.id.coinsRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        presenter.overview(0);

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
                presenter.overview(0);
            }
        });

    }

    @Override
    public void showCoins(List<CoinModel> coins) {
        Timber.d("showCoins");
        //coinsAdapter.setData(coins);
        if (currentPage == 0) {
            loadFirstPage(coins);
        } else {
            loadNextPage(coins);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadFirstPage(List<CoinModel> coins) {
        // fetching dummy data
        progressBar.setVisibility(View.GONE);
        coinsAdapter.addAll(coins);

        if (currentPage <= TOTAL_PAGES) coinsAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    private void loadNextPage(List<CoinModel> coins) {

        coinsAdapter.removeLoadingFooter();
        isLoading = false;

        coinsAdapter.addAll(coins);

        if (currentPage != TOTAL_PAGES) coinsAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    protected Presenter providePresenter() {
        return presenter;
    }
}

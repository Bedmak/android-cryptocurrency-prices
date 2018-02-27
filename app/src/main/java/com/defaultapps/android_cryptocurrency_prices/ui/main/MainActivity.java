package com.defaultapps.android_cryptocurrency_prices.ui.main;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.defaultapps.android_cryptocurrency_prices.data.utils.PreferenceRepository;
import com.defaultapps.android_cryptocurrency_prices.domain.Coin;
import com.defaultapps.android_cryptocurrency_prices.R;

import com.defaultapps.android_cryptocurrency_prices.data.utils.Constants;
import com.defaultapps.android_cryptocurrency_prices.ui.detail.DetailActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.settings.SettingsActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BaseActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

import java.net.SocketTimeoutException;
import java.util.List;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;


public class MainActivity extends BaseActivity implements MainContract.MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.timeChange)
    TextView timeChange;

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

    @Inject
    CoinsAdapter coinsAdapter;

    @Inject
    PreferenceRepository preferenceRepository;

    @Inject
    ConnectivityManager connectivityManager;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 29;
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        timeChange.setText(getPercentChange());
        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.overview(PAGE_START));
        presenter.overview(currentPage);
    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.settings_menu);
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.action_settings).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        });
    }

    private void initRecyclerView() {

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

    }

    private int getPercentChange() {
        switch (preferenceRepository.getChangesType()) {
            case "1 hour":
                return R.string.coin_change_1h;
            case "24 hours":
                return R.string.coin_change_24h;
            case "7 days":
                return R.string.coin_change_7d;
            default:
                return 0;
        }
    }

    @OnClick(R.id.buttonRetry)
    void onRetryClick() {
        presenter.overview(PAGE_START);
    }

    @Override
    public void showCoins(List<Coin> coins) {
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
    public void updateDisplay() {
        coinsAdapter.clear();
        progressBar.setVisibility(View.VISIBLE);
        currentPage = PAGE_START;
        timeChange.setText(getPercentChange());
        presenter.overview(currentPage);
    }

    @Override
    protected Presenter providePresenter() {
        return presenter;
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
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
    public void showDetailed(int position, String name) {
        Timber.d("onClick - " + position + " position");
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.COIN_NAME, name);
        intent.putExtra(Constants.COIN_NO, position);
        startActivity(intent);
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);
        boolean isNetworkConnected = connectivityManager.getActiveNetworkInfo() != null;
        if (!isNetworkConnected) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof SocketTimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }
}

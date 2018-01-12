package com.defaultapps.android_cryptocurrency_prices.ui.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BaseActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

import java.util.List;

import timber.log.Timber;


public class MainActivity extends BaseActivity implements MainContract.MainView {

    private RecyclerView coinsRecyclerView;
    private CoinsAdapter coinsAdapter;
    private ResponsePresenterImpl presenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new ResponsePresenterImpl();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coinsRecyclerView = findViewById(R.id.coinsRecyclerView);
        presenter.overview();

        coinsAdapter = new CoinsAdapter();
        coinsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coinsRecyclerView.setAdapter(coinsAdapter);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.overview();
            }
        });

    }

    @Override
    public void showCoins(List<ResponseFileModel> coins) {
        Timber.d("showCoins");
        coinsAdapter.setData(coins);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected Presenter providePresenter() {
        return presenter;
    }
}

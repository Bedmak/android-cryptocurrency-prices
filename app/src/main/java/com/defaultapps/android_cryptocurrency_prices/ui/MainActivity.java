package com.defaultapps.android_cryptocurrency_prices.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View {

    private RecyclerView coinsRecyclerView;
    private CoinsAdapter coinsAdapter;
    private ResponsePresenterImpl presenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coinsRecyclerView = findViewById(R.id.coinsRecyclerView);
        presenter = new ResponsePresenterImpl();
        presenter.onAttach(this);
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
        Log.v("MainActivity", "Refresh");
        coinsAdapter.setData(coins);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}

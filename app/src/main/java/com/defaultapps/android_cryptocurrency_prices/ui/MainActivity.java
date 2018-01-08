package com.defaultapps.android_cryptocurrency_prices.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View {

    private RecyclerView coinsRecyclerView;
    private CoinsAdapter coinsAdapter;
    private ResponsePresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coinsRecyclerView = findViewById(R.id.coinsRecyclerView);
        presenter = new ResponsePresenterImpl();
        presenter.onAttach(this);
        presenter.overview();


    }

    @Override
    public void showCoins(List<ResponseFileModel> coins) {
        coinsAdapter = new CoinsAdapter(coins);
        coinsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coinsRecyclerView.setAdapter(coinsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}

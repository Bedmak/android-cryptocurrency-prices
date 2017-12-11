package com.defaultapps.android_cryptocurrency_prices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.defaultapps.android_cryptocurrency_prices.network.NetworkService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkService ns = ((App) getApplication()).getNetworkService();
        ns.getResponse();
    }
}

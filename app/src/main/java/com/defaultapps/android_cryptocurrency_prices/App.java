package com.defaultapps.android_cryptocurrency_prices;

import android.app.Application;

import com.defaultapps.android_cryptocurrency_prices.data.network.NetworkService;

public class App extends Application {

    private NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();
    }

}

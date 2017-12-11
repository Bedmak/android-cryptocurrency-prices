package com.defaultapps.android_cryptocurrency_prices;

import android.app.Application;

import com.defaultapps.android_cryptocurrency_prices.network.NetworkService;

public class App extends Application {

    private NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();
        networkService = new NetworkService();
    }

    public NetworkService getNetworkService() {
        return networkService;
    }
}

package com.defaultapps.android_cryptocurrency_prices.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "https://api.coinmarketcap.com/v1/";

    public static CoinApi getCoinApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoinApi coinApi = retrofit.create(CoinApi.class);

        return coinApi;
    }

}

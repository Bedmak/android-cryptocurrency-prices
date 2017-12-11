package com.defaultapps.android_cryptocurrency_prices.network;


import android.util.Log;

import com.defaultapps.android_cryptocurrency_prices.models.ResponseFileModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private CoinApi coinApi;

    public NetworkService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://api.coinmarketcap.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        coinApi = retrofit.create(CoinApi.class);

    }

    public void getResponse() {
        coinApi.getListCryptocurrency(1).enqueue(new Callback<List<ResponseFileModel>>() {
            @Override
            public void onResponse(Call<List<ResponseFileModel>> call, Response<List<ResponseFileModel>> response) {
                List<ResponseFileModel> responseModel = new ArrayList<>();
                if (response.body() != null) {
                    responseModel.addAll(response.body());
                    Log.e("RESPONSE", "response - " + responseModel.get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseFileModel>> call, Throwable t) {

            }
        });
    }
}

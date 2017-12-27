package com.defaultapps.android_cryptocurrency_prices.data.network;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinApi {
    @GET("ticker/")
    Call<List<ResponseFileModel>> getListCryptocurrency();

    @GET("ticker/")
    Call<List<ResponseFileModel>> getListCryptocurrency(@Query("limit") int lim);

    @GET("ticker/")
    Call<List<ResponseFileModel>> getListCryptocurrency(@Query("start") int start, @Query("limit") int limit);
}

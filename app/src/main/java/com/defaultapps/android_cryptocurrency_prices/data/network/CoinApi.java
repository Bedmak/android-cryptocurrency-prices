package com.defaultapps.android_cryptocurrency_prices.data.network;

import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinApi {
    @GET("ticker/")
    Single<List<CoinModel>> getListCryptocurrency();

    @GET("ticker/")
    Single<List<CoinModel>> getListCryptocurrency(@Query("limit") int lim);

    @GET("ticker/")
    Single<List<CoinModel>> getListCryptocurrency(@Query("start") int start, @Query("limit") int limit);
}

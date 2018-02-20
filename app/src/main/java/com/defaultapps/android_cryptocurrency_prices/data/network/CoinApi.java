package com.defaultapps.android_cryptocurrency_prices.data.network;

import com.defaultapps.android_cryptocurrency_prices.data.models.CoinResponse;

import java.util.List;

import io.reactivex.Single;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinApi {
    @GET("ticker/")
    Single<List<CoinResponse>> getListCryptocurrency(@Query("start") int start, @Query("limit") int limit, @Query("convert") String convert);
}

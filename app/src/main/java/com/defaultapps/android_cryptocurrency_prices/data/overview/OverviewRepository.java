package com.defaultapps.android_cryptocurrency_prices.data.overview;

import com.defaultapps.android_cryptocurrency_prices.data.models.CoinResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface OverviewRepository {
    Single<List<CoinResponse>> getCoins(int start, int lim, String convert);
    Observable<String> providePublishSubject();
}

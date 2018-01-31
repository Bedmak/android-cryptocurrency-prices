package com.defaultapps.android_cryptocurrency_prices.data.overview;

import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;

import java.util.List;

import io.reactivex.Observable;

public interface CryptocurrencyOverview {

    Observable<List<CoinModel>> getCoins(int start, int lim);

}

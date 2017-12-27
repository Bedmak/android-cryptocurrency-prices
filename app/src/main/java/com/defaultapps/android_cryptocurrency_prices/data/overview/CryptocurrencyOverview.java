package com.defaultapps.android_cryptocurrency_prices.data.overview;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;

import java.util.List;

import io.reactivex.Single;

public interface CryptocurrencyOverview {

    Single<List<ResponseFileModel>> getCoins();

}

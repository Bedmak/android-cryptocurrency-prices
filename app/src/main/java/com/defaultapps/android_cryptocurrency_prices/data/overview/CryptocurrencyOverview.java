package com.defaultapps.android_cryptocurrency_prices.data.overview;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;

import java.util.List;

public interface CryptocurrencyOverview {

    void getCoins(retrofit2.Callback<List<ResponseFileModel>> callback);

}

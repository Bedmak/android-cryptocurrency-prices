package com.defaultapps.android_cryptocurrency_prices.data.overview;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.data.network.CoinApi;
import com.defaultapps.android_cryptocurrency_prices.data.network.NetworkService;

import java.util.List;

import retrofit2.Callback;


public class CryptocurrencyOverviewImpl implements CryptocurrencyOverview {

    CoinApi ns = NetworkService.getCoinApi();

    @Override
    public void getCoins(Callback<List<ResponseFileModel>> callback) {
        ns.getListCryptocurrency(1).enqueue(callback);
    }
}

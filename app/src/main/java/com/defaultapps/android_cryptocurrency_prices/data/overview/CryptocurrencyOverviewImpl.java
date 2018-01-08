package com.defaultapps.android_cryptocurrency_prices.data.overview;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.data.network.CoinApi;
import com.defaultapps.android_cryptocurrency_prices.data.network.NetworkService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CryptocurrencyOverviewImpl implements CryptocurrencyOverview {

    CoinApi ns = NetworkService.getCoinApi();

    @Override
    public Single<List<ResponseFileModel>> getCoins() {
        return ns.getListCryptocurrency(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public String getUsdChangesPrices(ResponseFileModel coin) {
        float price = Float.parseFloat(coin.getPriceUsd());
        float percents = Float.parseFloat(coin.getPercentChange1h());
        return Float.toString(price - (price / 100) * (100 - percents));
    }
}

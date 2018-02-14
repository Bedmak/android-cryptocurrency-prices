package com.defaultapps.android_cryptocurrency_prices.data.overview;

import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.data.network.CoinApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class CryptocurrencyOverviewImpl implements CryptocurrencyOverview {

    private CoinApi coinApi;

    @Inject
    CryptocurrencyOverviewImpl(CoinApi coinApi) {
        this.coinApi = coinApi;
    }

    @Override
    public Single<List<CoinModel>> getCoins(int start, int lim) {
        Timber.d("getCoins");
        return coinApi.getListCryptocurrency(start, lim)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

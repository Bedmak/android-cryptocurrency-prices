package com.defaultapps.android_cryptocurrency_prices.data.overview;

import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.data.network.CoinApi;
import com.defaultapps.android_cryptocurrency_prices.data.network.NetworkService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class CryptocurrencyOverviewImpl implements CryptocurrencyOverview {

    private final NetworkService networkService;

    @Inject
    public CryptocurrencyOverviewImpl(NetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public Single<List<CoinModel>> getCoins(int start, int lim) {
        Timber.d("getCoins");
        return networkService.getCoinApi().getListCryptocurrency(start, lim)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

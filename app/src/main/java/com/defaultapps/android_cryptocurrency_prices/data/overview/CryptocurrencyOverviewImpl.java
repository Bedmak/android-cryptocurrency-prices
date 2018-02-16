package com.defaultapps.android_cryptocurrency_prices.data.overview;

import com.defaultapps.android_cryptocurrency_prices.data.SchedulerProvider;
import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.data.network.CoinApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;


public class CryptocurrencyOverviewImpl implements CryptocurrencyOverview {

    private final CoinApi coinApi;
    private final SchedulerProvider schedulerProvider;

    @Inject
    CryptocurrencyOverviewImpl(CoinApi coinApi,
                               SchedulerProvider schedulerProvider) {
        this.coinApi = coinApi;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Single<List<CoinModel>> getCoins(int start, int lim) {
        Timber.d("getCoins");
        return coinApi.getListCryptocurrency(start, lim)
                .compose(schedulerProvider.applySchedulers());
    }

}

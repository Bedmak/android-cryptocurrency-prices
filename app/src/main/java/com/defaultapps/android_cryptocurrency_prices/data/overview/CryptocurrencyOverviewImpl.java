package com.defaultapps.android_cryptocurrency_prices.data.overview;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.data.network.CoinApi;
import com.defaultapps.android_cryptocurrency_prices.data.network.NetworkService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class CryptocurrencyOverviewImpl implements CryptocurrencyOverview {

    private final CoinApi ns;

    public CryptocurrencyOverviewImpl() {
        ns = NetworkService.getCoinApi();
    }

    @Override
    public Single<List<ResponseFileModel>> getCoins(int start, int lim) {
        Timber.d("getCoins");
        return ns.getListCryptocurrency(start, lim)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

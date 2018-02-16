package com.defaultapps.android_cryptocurrency_prices.ui.detailed;


import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;



import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class DetailedResponsePresenterImpl extends BasePresenter<DetailedContract.DetailedView> implements DetailedContract.DetailedResponsePresenter {

    private final CryptocurrencyOverviewImpl cryptocurrencyOverview;

    @Inject
    DetailedResponsePresenterImpl(CryptocurrencyOverviewImpl cryptocurrencyOverview,
                                  CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
        this.cryptocurrencyOverview = cryptocurrencyOverview;
    }

    @Override
    public void overview(int start) {
        cryptocurrencyOverview.getCoins(start, 1)
                .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                .map(coinModels -> coinModels.get(0))
                .doOnSuccess((coin) -> Timber.d("Show %s", coin.getName()))
                .subscribe(coin -> getView().displayData(coin), Timber::e);
    }

}

package com.defaultapps.android_cryptocurrency_prices.ui.main;


import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;

import timber.log.Timber;

public class ResponsePresenterImpl extends BasePresenter<MainContract.MainView> implements MainContract.ResponsePresenter {                                   // MVP - Presenter

    private final CryptocurrencyOverviewImpl cryptoOverview;

    ResponsePresenterImpl() {
        cryptoOverview = new CryptocurrencyOverviewImpl();
    }

    @Override
    public void overview(int start) {
        getView().hideErrorView();

        cryptoOverview.getCoins(start, 50)
                .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                .filter(coins -> coins != null)
                .subscribe(coins -> getView().showCoins(coins), e -> {
                    Timber.e(e);
                    getView().showErrorView(e);
                });
    }

}

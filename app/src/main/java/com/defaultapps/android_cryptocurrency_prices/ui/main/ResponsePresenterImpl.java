package com.defaultapps.android_cryptocurrency_prices.ui.main;


import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;

import javax.inject.Inject;

import timber.log.Timber;

public class ResponsePresenterImpl extends BasePresenter<MainContract.MainView> implements MainContract.ResponsePresenter {                                   // MVP - Presenter

    private final CryptocurrencyOverviewImpl cryptocurrencyOverview;

    @Inject
    ResponsePresenterImpl(CryptocurrencyOverviewImpl cryptocurrencyOverview) {
        this.cryptocurrencyOverview = cryptocurrencyOverview;
    }

    @Override
    public void overview(int start) {
        getView().hideErrorView();

        cryptocurrencyOverview.getCoins(start, 50)
                .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                .filter(coins -> coins != null)
                .subscribe(coins -> getView().showCoins(coins), e -> {
                    Timber.e(e);
                    getView().showErrorView(e);
                });
    }

}

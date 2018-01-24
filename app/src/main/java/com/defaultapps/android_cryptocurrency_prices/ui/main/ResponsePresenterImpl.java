package com.defaultapps.android_cryptocurrency_prices.ui.main;

import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        getCompositeDisposable().add(disposable);
                    }
                })
                .subscribe(new SingleObserver<List<CoinModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    Timber.d("onSubscribe");
                    }

                    @Override
                    public void onSuccess(List<CoinModel> coins) {
                        Timber.d("onSuccess");
                        if (coins != null && !coins.isEmpty()) {
                            getView().showCoins(coins);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        getView().showErrorView(e);
                    }
                });
    }

}

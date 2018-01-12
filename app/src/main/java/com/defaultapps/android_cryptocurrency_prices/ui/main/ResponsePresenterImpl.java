package com.defaultapps.android_cryptocurrency_prices.ui.main;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ResponsePresenterImpl extends BasePresenter<MainContract.MainView> implements MainContract.ResponsePresenter {                                   // MVP - Presenter

    private final CryptocurrencyOverviewImpl cryptoOverview;

    ResponsePresenterImpl() {
        cryptoOverview = new CryptocurrencyOverviewImpl();
    }

    @Override
    public void overview() {
        cryptoOverview.getCoins(0, 10).subscribe(new SingleObserver<List<ResponseFileModel>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Timber.d("onSubscribe");
            }

            @Override
            public void onSuccess(List<ResponseFileModel> coins) {
                Timber.d("onSuccess");
                if (coins != null && !coins.isEmpty()) {
                    getView().showCoins(coins);
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }
        });
    }

}

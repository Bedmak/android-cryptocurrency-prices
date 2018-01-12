package com.defaultapps.android_cryptocurrency_prices.ui.detailed;


import android.util.Log;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class DetailedResponsePresenterImpl extends BasePresenter<DetailedContract.DetailedView> implements DetailedContract.DetailedResponsePresenter {

    private final CryptocurrencyOverviewImpl cryptoOverview;

    DetailedResponsePresenterImpl() {
        cryptoOverview = new CryptocurrencyOverviewImpl();
    }

    @Override
    public void overview(int start) {
        cryptoOverview.getCoins(start, 1).subscribe(new SingleObserver<List<ResponseFileModel>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Timber.d("onSubscribe");
            }

            @Override
            public void onSuccess(List<ResponseFileModel> coin) {
                Timber.d("onSuccess");
                if (coin != null && !coin.isEmpty()) {
                    getView().displayData(coin.get(0));
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }
        });
    }

}

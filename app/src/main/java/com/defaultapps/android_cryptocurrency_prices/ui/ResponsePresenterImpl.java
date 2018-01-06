package com.defaultapps.android_cryptocurrency_prices.ui;

import android.util.Log;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class ResponsePresenterImpl implements ResponsePresenter {                                   // MVP - Presenter

    private CryptocurrencyOverviewImpl cryptoOverview = new CryptocurrencyOverviewImpl();           // MVP - Model

    private View view;                                                                              // MVP - View - MainActivity


    @Override
    public void onAttach(View view) {
        this.view = view;
    }

    @Override
    public void overview() {
        cryptoOverview.getCoins().subscribe(new SingleObserver<List<ResponseFileModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<ResponseFileModel> coins) {
                if (coins != null && !coins.isEmpty()) {
                    view.showCoins(coins);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Response", "error - ", e);
            }
        });
    }

    @Override
    public void onDetach() {
        view = null;
    }
}

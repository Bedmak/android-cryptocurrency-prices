package com.defaultapps.android_cryptocurrency_prices.ui;

import android.util.Log;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;

import org.reactivestreams.Subscriber;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponsePresenterImpl implements ResponsePresenter {

    private CryptocurrencyOverviewImpl cryptoOverview = new CryptocurrencyOverviewImpl(); // MVP - Model

    private View view;  // MVP - View - MainActivity

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
                    view.showCoin(coins.get(0).getName() + " - " + coins.get(0).getPriceUsd());
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

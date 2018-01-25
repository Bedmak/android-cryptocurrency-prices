package com.defaultapps.android_cryptocurrency_prices.ui.detailed;


import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class DetailedResponsePresenterImpl extends BasePresenter<DetailedContract.DetailedView> implements DetailedContract.DetailedResponsePresenter {

    private final CryptocurrencyOverviewImpl cryptoOverview;

    DetailedResponsePresenterImpl() {
        cryptoOverview = new CryptocurrencyOverviewImpl();
    }

    @Override
    public void overview(int start) {
        cryptoOverview.getCoins(start, 1)
                .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                .subscribe(new SingleObserver<List<CoinModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.d("onSubscribe");
                    }

                    @Override
                    public void onSuccess(List<CoinModel> coin) {
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

package com.defaultapps.android_cryptocurrency_prices.ui.detailed;


import com.defaultapps.android_cryptocurrency_prices.data.overview.OverviewRepositoryImpl;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;



import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class DetailPresenterImpl extends BasePresenter<DetailContract.DetailView> implements DetailContract.DetailPresenter {

    private final OverviewRepositoryImpl cryptocurrencyOverview;

    @Inject
    DetailPresenterImpl(OverviewRepositoryImpl cryptocurrencyOverview,
                        CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
        this.cryptocurrencyOverview = cryptocurrencyOverview;
    }

    @Override
    public void overview(int start) {
        cryptocurrencyOverview.getCoins(start, 1, "USD")
                .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                .map(coinModels -> coinModels.get(0))
                .doOnSuccess((coin) -> Timber.d("Show %s", coin.getName()))
                .subscribe(coin -> getView().displayData(coin), Timber::e);
    }

}

package com.defaultapps.android_cryptocurrency_prices.ui.detail;


import com.defaultapps.android_cryptocurrency_prices.data.overview.OverviewRepositoryImpl;
import com.defaultapps.android_cryptocurrency_prices.data.utils.PreferenceRepository;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;



import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class DetailPresenterImpl extends BasePresenter<DetailContract.DetailView> implements DetailContract.DetailPresenter {

    private final OverviewRepositoryImpl cryptocurrencyOverview;
    private PreferenceRepository preferenceRepository;

    @Inject
    DetailPresenterImpl(OverviewRepositoryImpl cryptocurrencyOverview,
                        PreferenceRepository preferenceRepository,
                        CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
        this.cryptocurrencyOverview = cryptocurrencyOverview;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void overview(int start) {
        cryptocurrencyOverview.getCoins(start, 1, preferenceRepository.getMoneyType())
                .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                .map(coinModels -> coinModels.get(0))
                .doOnSuccess((coin) -> Timber.d("Show %s", coin.getName()))
                .subscribe(coin -> getView().displayData(coin), Timber::e);
    }

}

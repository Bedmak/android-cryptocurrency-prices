package com.defaultapps.android_cryptocurrency_prices.ui.main;


import com.defaultapps.android_cryptocurrency_prices.data.utils.PreferenceRepository;
import com.defaultapps.android_cryptocurrency_prices.ui.common.CoinsMapper;
import com.defaultapps.android_cryptocurrency_prices.data.overview.OverviewRepositoryImpl;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class ResponsePresenterImpl extends BasePresenter<MainContract.MainView> implements MainContract.ResponsePresenter {                                   // MVP - Presenter

    private final OverviewRepositoryImpl overviewRepository;
    private PreferenceRepository preferenceRepository;
    private CoinsMapper coinsMapper;

    @Inject
    ResponsePresenterImpl(OverviewRepositoryImpl overviewRepository,
                          PreferenceRepository preferenceRepository,
                          CompositeDisposable compositeDisposable,
                          CoinsMapper coinsMapper) {
        super(compositeDisposable);
        this.preferenceRepository = preferenceRepository;
        this.overviewRepository = overviewRepository;
        this.coinsMapper = coinsMapper;
    }

    @Override
    public void overview(int start) {
        getView().hideErrorView();

        overviewRepository.getCoins(start, 50, preferenceRepository.getMoneyType())
                .map(coinsMapper)
                .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                .filter(coins -> coins != null)
                .subscribe(coins -> getView().showCoins(coins), e -> {
                    Timber.e(e);
                    getView().showErrorView(e);
                });
    }

    @Override
    public void onAttach(MainContract.MainView view) {
        super.onAttach(view);
        overviewRepository.providePublishSubject()
                .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                .subscribe(key -> getView().updateDisplay());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

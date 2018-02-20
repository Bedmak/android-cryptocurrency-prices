package com.defaultapps.android_cryptocurrency_prices.ui.settings;


import com.defaultapps.android_cryptocurrency_prices.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SettingsPresenterImpl extends BasePresenter<SettingsContract.SettingsView>
        implements SettingsContract.SettingsPresenter {

    @Inject
    public SettingsPresenterImpl(CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
    }

}

package com.defaultapps.android_cryptocurrency_prices.di.module;


import android.app.Activity;
import android.content.Context;

import com.defaultapps.android_cryptocurrency_prices.di.ActivityContext;
import com.defaultapps.android_cryptocurrency_prices.di.scope.PerActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.MvpView;
import com.defaultapps.android_cryptocurrency_prices.ui.main.MainContract;


import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private MvpView mvpView;

    public ActivityModule(MvpView mvpView) {
        this.mvpView = mvpView;
    }

    @PerActivity
    @Provides
    MvpView provideMvpView() {
        return mvpView;
    }
}
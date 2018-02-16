package com.defaultapps.android_cryptocurrency_prices.di.module;


import android.app.Activity;

import com.defaultapps.android_cryptocurrency_prices.di.scope.PerActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.MvpView;


import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    MvpView provideMvpView() {
        return (MvpView) activity;
    }

    @PerActivity
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
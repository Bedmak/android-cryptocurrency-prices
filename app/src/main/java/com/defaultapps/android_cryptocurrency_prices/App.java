package com.defaultapps.android_cryptocurrency_prices;

import android.app.Application;
import android.preference.PreferenceManager;

import com.defaultapps.android_cryptocurrency_prices.di.component.ApplicationComponent;
import com.defaultapps.android_cryptocurrency_prices.di.component.DaggerApplicationComponent;
import com.defaultapps.android_cryptocurrency_prices.di.module.ApplicationModule;

import timber.log.Timber;


public class App extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.setDefaultValues(this, R.xml.preference, false);
        initDaggerAppComponent();
        applicationComponent.inject(this);
        Timber.plant(new Timber.DebugTree());
    }

    private void initDaggerAppComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

}

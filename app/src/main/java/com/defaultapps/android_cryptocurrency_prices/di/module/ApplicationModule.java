package com.defaultapps.android_cryptocurrency_prices.di.module;


import android.app.Application;
import android.content.Context;

import com.defaultapps.android_cryptocurrency_prices.data.AppSchedulerProvider;
import com.defaultapps.android_cryptocurrency_prices.data.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}

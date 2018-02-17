package com.defaultapps.android_cryptocurrency_prices.di.module;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.defaultapps.android_cryptocurrency_prices.data.AppSchedulerProvider;
import com.defaultapps.android_cryptocurrency_prices.data.SchedulerProvider;
import com.defaultapps.android_cryptocurrency_prices.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @ApplicationContext
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

    @Provides
    ConnectivityManager provideConnectivityManager(@ApplicationContext Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}

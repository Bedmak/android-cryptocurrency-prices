package com.defaultapps.android_cryptocurrency_prices.di.component;


import android.content.Context;

import com.defaultapps.android_cryptocurrency_prices.App;
import com.defaultapps.android_cryptocurrency_prices.data.SchedulerProvider;
import com.defaultapps.android_cryptocurrency_prices.data.network.CoinApi;
import com.defaultapps.android_cryptocurrency_prices.data.network.NetworkModule;
import com.defaultapps.android_cryptocurrency_prices.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class
})
public interface ApplicationComponent {

    void inject(App app);

    Context context();
    CoinApi coinApi();
    SchedulerProvider schedulerProvider();

}

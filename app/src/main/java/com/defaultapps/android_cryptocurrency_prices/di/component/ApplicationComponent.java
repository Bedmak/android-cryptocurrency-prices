package com.defaultapps.android_cryptocurrency_prices.di.component;


import android.content.Context;
import android.net.ConnectivityManager;

import com.defaultapps.android_cryptocurrency_prices.App;
import com.defaultapps.android_cryptocurrency_prices.data.SchedulerProvider;
import com.defaultapps.android_cryptocurrency_prices.data.network.CoinApi;
import com.defaultapps.android_cryptocurrency_prices.data.network.NetworkModule;
import com.defaultapps.android_cryptocurrency_prices.data.utils.PreferenceRepository;
import com.defaultapps.android_cryptocurrency_prices.di.ApplicationContext;
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

    @ApplicationContext
    Context context();

    ConnectivityManager connectivityManager();
    CoinApi coinApi();
    SchedulerProvider schedulerProvider();
    PreferenceRepository preferenceRepository();

}

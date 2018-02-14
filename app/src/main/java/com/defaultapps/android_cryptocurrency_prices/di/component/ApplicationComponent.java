package com.defaultapps.android_cryptocurrency_prices.di.component;


import android.content.Context;

import com.defaultapps.android_cryptocurrency_prices.App;
import com.defaultapps.android_cryptocurrency_prices.data.network.NetworkModule;
import com.defaultapps.android_cryptocurrency_prices.di.module.ApplicationModule;
import com.defaultapps.android_cryptocurrency_prices.ui.detailed.DetailedActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class
})
public interface ApplicationComponent {

    void inject(App app);
    void inject(MainActivity mainActivity);
    void inject(DetailedActivity detailedActivity);

    Context context();

    //ResponsePresenterImpl responsePresenterImpl();
}

package com.defaultapps.android_cryptocurrency_prices.di.component;


import com.defaultapps.android_cryptocurrency_prices.di.module.ActivityModule;
import com.defaultapps.android_cryptocurrency_prices.di.scope.PerActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.detailed.DetailActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.main.MainActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.settings.SettingsActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(DetailActivity detailActivity);
    void inject(SettingsActivity settingsActivity);
}

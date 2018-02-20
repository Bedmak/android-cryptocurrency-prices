package com.defaultapps.android_cryptocurrency_prices.data.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.defaultapps.android_cryptocurrency_prices.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class PreferenceRepository {

    private SharedPreferences preferences;

    @Inject
    PreferenceRepository(@ApplicationContext Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public String getMoneyType() {
        return preferences.getString(Constants.PREFERENCE_CURRENCIES, "");
    }

    public String getChangesType() {
        return preferences.getString(Constants.PREFERENCE_CHANGES, "");
    }

}

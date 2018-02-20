package com.defaultapps.android_cryptocurrency_prices.ui.settings;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.utils.Constants;

import javax.inject.Inject;



/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {



    @Inject
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        setEstablishedSummary(getPreferenceScreen().getSharedPreferences());
    }

    public void setEstablishedSummary(SharedPreferences sp) {
        findPreference(Constants.PREFERENCE_CURRENCIES).setSummary(sp.getString(Constants.PREFERENCE_CURRENCIES, ""));
        findPreference(Constants.PREFERENCE_CHANGES).setSummary(sp.getString(Constants.PREFERENCE_CHANGES, ""));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        findPreference(key).setSummary(sharedPreferences.getString(key, ""));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}

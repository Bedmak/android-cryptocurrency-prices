package com.defaultapps.android_cryptocurrency_prices.ui.settings;


import android.os.Bundle;


import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BaseActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

import javax.inject.Inject;



public class SettingsActivity extends BaseActivity implements SettingsContract.SettingsView {


    @Inject
    SettingsPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsFragment settingsFragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_frame, settingsFragment)
                .commit();
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected Presenter providePresenter() {
        return presenter;
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_settings;
    }


}

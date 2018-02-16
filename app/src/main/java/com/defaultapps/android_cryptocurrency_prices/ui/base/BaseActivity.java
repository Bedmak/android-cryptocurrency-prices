package com.defaultapps.android_cryptocurrency_prices.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.defaultapps.android_cryptocurrency_prices.App;
import com.defaultapps.android_cryptocurrency_prices.di.component.ActivityComponent;
import com.defaultapps.android_cryptocurrency_prices.di.component.DaggerActivityComponent;
import com.defaultapps.android_cryptocurrency_prices.di.module.ActivityModule;


public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    protected ActivityComponent activityComponent;
    private Presenter presenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App) getApplication()).getComponent())
                .build();
        inject();
        presenter = providePresenter();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    protected abstract Presenter providePresenter();

    public void inject() {}
}

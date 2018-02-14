package com.defaultapps.android_cryptocurrency_prices.di.component;

import com.defaultapps.android_cryptocurrency_prices.ui.main.ResponsePresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component
public interface ResponsePresenterComponent {
    ResponsePresenterImpl responsePresenterImpl();
}

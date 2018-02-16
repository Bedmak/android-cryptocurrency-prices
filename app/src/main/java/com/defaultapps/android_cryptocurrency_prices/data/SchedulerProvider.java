package com.defaultapps.android_cryptocurrency_prices.data;


import io.reactivex.SingleTransformer;

public interface SchedulerProvider {
    <T>SingleTransformer<T,T> applySchedulers();
}

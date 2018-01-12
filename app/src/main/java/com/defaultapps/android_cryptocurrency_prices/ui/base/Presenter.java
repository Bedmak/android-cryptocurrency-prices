package com.defaultapps.android_cryptocurrency_prices.ui.base;

public interface Presenter<View extends MvpView> {
    void onAttach(View view);
    void onDetach();
}

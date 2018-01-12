package com.defaultapps.android_cryptocurrency_prices.ui.base;

public abstract class BasePresenter<View extends MvpView> implements Presenter<View> {

    private View view;

    @Override
    public void onAttach(View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    public View getView() { return view; }
}

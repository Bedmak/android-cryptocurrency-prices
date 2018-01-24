package com.defaultapps.android_cryptocurrency_prices.ui.base;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<View extends MvpView> implements Presenter<View> {

    private View view;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onAttach(View view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
        view = null;
    }

    public View getView() { return view; }

    public CompositeDisposable getCompositeDisposable() { return compositeDisposable; }
}

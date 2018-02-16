package com.defaultapps.android_cryptocurrency_prices.ui.base;


import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<View extends MvpView> implements Presenter<View> {

    private View view;
    private CompositeDisposable compositeDisposable;

    public BasePresenter(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
        view = null;
    }

    protected View getView() { return view; }

    protected CompositeDisposable getCompositeDisposable() { return compositeDisposable; }
}

package com.defaultapps.android_cryptocurrency_prices.ui.main;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.ui.base.MvpView;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

import java.util.List;

public interface MainContract {

    interface MainView extends MvpView {
        void showCoins(List<ResponseFileModel> coins);
    }

    interface ResponsePresenter extends Presenter<MainView> {
        void overview();
    }
}

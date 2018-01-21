package com.defaultapps.android_cryptocurrency_prices.ui.detailed;

import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.ui.base.MvpView;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

public interface DetailedContract {

    interface DetailedView extends MvpView {
        void displayData(CoinModel coin);
    }

    interface DetailedResponsePresenter extends Presenter<DetailedView> {
        void overview(int start);
    }

}

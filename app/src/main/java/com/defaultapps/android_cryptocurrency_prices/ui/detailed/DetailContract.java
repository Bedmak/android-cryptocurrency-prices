package com.defaultapps.android_cryptocurrency_prices.ui.detailed;

import com.defaultapps.android_cryptocurrency_prices.data.models.CoinResponse;
import com.defaultapps.android_cryptocurrency_prices.ui.base.MvpView;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

public interface DetailContract {

    interface DetailView extends MvpView {
        void displayData(CoinResponse coin);
    }

    interface DetailPresenter extends Presenter<DetailView> {
        void overview(int start);
    }

}

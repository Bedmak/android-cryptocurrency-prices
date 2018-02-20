package com.defaultapps.android_cryptocurrency_prices.ui.main;

import com.defaultapps.android_cryptocurrency_prices.domain.Coin;
import com.defaultapps.android_cryptocurrency_prices.ui.base.MvpView;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

import java.util.List;

public interface MainContract {

    interface MainView extends MvpView {
        void showCoins(List<Coin> coins);
        void updateDisplay();
        void showErrorView(Throwable t);
        void hideErrorView();
        void showDetailed(int position, String name);
    }

    interface ResponsePresenter extends Presenter<MainView> {
        void overview(int start);
    }
}

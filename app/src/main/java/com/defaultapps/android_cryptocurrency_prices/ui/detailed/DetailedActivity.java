package com.defaultapps.android_cryptocurrency_prices.ui.detailed;

import android.os.Bundle;
import android.widget.TextView;

import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BaseActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

import timber.log.Timber;

public class DetailedActivity extends BaseActivity implements DetailedContract.DetailedView {

    private DetailedResponsePresenterImpl presenter;

    public static final String COIN_NO = "coin_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new DetailedResponsePresenterImpl();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        presenter.overview(getIntent().getIntExtra(COIN_NO, 0));
    }

    @Override
    protected Presenter providePresenter() {
        return presenter;
    }

    @Override
    public void displayData(ResponseFileModel coin) {
        Timber.d("displayData");
        ((TextView) findViewById(R.id.detailedCoinName)).setText(coin.getName());
    }
}

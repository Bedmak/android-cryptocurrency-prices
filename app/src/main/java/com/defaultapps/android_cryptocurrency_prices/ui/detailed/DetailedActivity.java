package com.defaultapps.android_cryptocurrency_prices.ui.detailed;

import android.os.Bundle;
import android.widget.TextView;

import com.defaultapps.android_cryptocurrency_prices.App;
import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BaseActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;

public class DetailedActivity extends BaseActivity implements DetailedContract.DetailedView {

    @Inject
    DetailedResponsePresenterImpl presenter;

    public static final String COIN_NO = "coin_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);
        presenter.overview(getIntent().getIntExtra(COIN_NO, 0));
    }

    @Override
    protected Presenter providePresenter() {
        return presenter;
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void displayData(CoinModel coin) {
        Timber.d("displayData");
        ((TextView) findViewById(R.id.detailedCoinName)).setText(coin.getName());
    }
}

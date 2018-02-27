package com.defaultapps.android_cryptocurrency_prices.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.CoinResponse;
import com.defaultapps.android_cryptocurrency_prices.ui.base.BaseActivity;
import com.defaultapps.android_cryptocurrency_prices.ui.base.Presenter;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.defaultapps.android_cryptocurrency_prices.data.utils.Constants.COIN_NAME;
import static com.defaultapps.android_cryptocurrency_prices.data.utils.Constants.COIN_NO;

public class DetailActivity extends BaseActivity implements DetailContract.DetailView {

    @BindView(R.id.detailed_toolbar)
    Toolbar toolbar;

    @Inject
    DetailPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(getIntent().getStringExtra(COIN_NAME));
        presenter.overview(getIntent().getIntExtra(COIN_NO, 0));
    }

    private void initToolbar(String name) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setTitle(name + " detail");
    }

    @Override
    protected Presenter providePresenter() {
        return presenter;
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void displayData(CoinResponse coin) {
        Timber.d("displayData");
        // display data here
    }
}

package com.defaultapps.android_cryptocurrency_prices.data.overview;

import android.content.SharedPreferences;

import com.defaultapps.android_cryptocurrency_prices.data.SchedulerProvider;
import com.defaultapps.android_cryptocurrency_prices.data.models.CoinResponse;
import com.defaultapps.android_cryptocurrency_prices.data.network.CoinApi;
import com.defaultapps.android_cryptocurrency_prices.data.utils.Constants;
import com.defaultapps.android_cryptocurrency_prices.data.utils.PreferenceRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;


public class OverviewRepositoryImpl implements OverviewRepository, SharedPreferences.OnSharedPreferenceChangeListener {

    private final CoinApi coinApi;
    private final SchedulerProvider schedulerProvider;
    private PreferenceRepository preferenceRepository;
    private PublishSubject<String> subject;

    @Inject
    OverviewRepositoryImpl(CoinApi coinApi,
                           SchedulerProvider schedulerProvider,
                           PreferenceRepository preferenceRepository) {
        this.coinApi = coinApi;
        this.schedulerProvider = schedulerProvider;
        this.preferenceRepository = preferenceRepository;
        this.preferenceRepository.getPreferences().registerOnSharedPreferenceChangeListener(this);
        subject = PublishSubject.create();
    }

    @Override
    public Single<List<CoinResponse>> getCoins(int start, int lim, String convert) {
        Timber.d("getCoins");
        return coinApi.getListCryptocurrency(start, lim, convert)
                .compose(schedulerProvider.applySchedulers());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Constants.PREFERENCE_CURRENCIES) || key.equals(Constants.PREFERENCE_CHANGES))
            subject.onNext(key);
    }

    public Observable<String> providePublishSubject() {
        return subject;
    }
}

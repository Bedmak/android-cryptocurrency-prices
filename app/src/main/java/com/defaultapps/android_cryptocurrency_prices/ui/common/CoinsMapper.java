package com.defaultapps.android_cryptocurrency_prices.ui.common;


import com.defaultapps.android_cryptocurrency_prices.data.models.CoinResponse;
import com.defaultapps.android_cryptocurrency_prices.data.utils.PreferenceRepository;
import com.defaultapps.android_cryptocurrency_prices.domain.Coin;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


public class CoinsMapper implements Function<List<CoinResponse>,List<Coin>> {


    private PreferenceRepository preferenceRepository;

    @Inject
    public CoinsMapper(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }


    @Override
    public List<Coin> apply(List<CoinResponse> coinResponseList) throws Exception {
        return Observable.fromIterable(coinResponseList)
                .map(coinModel ->
                        new Coin(coinModel.getId(),
                                coinModel.getName(),
                                coinModel.getSymbol(),
                                choosePriceType(coinModel),
                                choosePercentChange(coinModel),
                                coinModel.getPercentChange1h(),
                                coinModel.getPercentChange24h(),
                                coinModel.getPercentChange7d()))
                .toList()
                .blockingGet();
    }

    private float choosePriceType(CoinResponse coinResponse) {
        switch (preferenceRepository.getMoneyType()) {
            case "USD":
                return Float.parseFloat(coinResponse.getPriceUsd());
            case "EUR":
                return Float.parseFloat(coinResponse.getPriceEur());
            case "RUB":
                return Float.parseFloat(coinResponse.getPriceRub());
            default:
                return 0;
        }
    }

    private float choosePercentChange(CoinResponse coinResponse) {
        switch (preferenceRepository.getChangesType()) {
            case "1 hour":
                return Float.parseFloat(coinResponse.getPercentChange1h());
            case "24 hours":
                return Float.parseFloat(coinResponse.getPercentChange24h());
            case "7 days":
                return Float.parseFloat(coinResponse.getPercentChange7d());
            default:
                return 0;
        }
    }
}

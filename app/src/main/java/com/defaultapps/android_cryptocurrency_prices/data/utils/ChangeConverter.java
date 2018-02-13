package com.defaultapps.android_cryptocurrency_prices.data.utils;

import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;

public class ChangeConverter {

    public static String getUsdChangesPrices(CoinModel coin) {
        float price = Float.parseFloat(coin.getPriceUsd());
        float percents = Float.parseFloat(coin.getPercentChange1h());
        float changePrice = price - (price / 100) * (100 - percents);
        return Float.toString(round(changePrice));
    }

    private static float round(float number) {
        double tmp = number * 100;
        return (float) (int) ((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / 100;
    }
}

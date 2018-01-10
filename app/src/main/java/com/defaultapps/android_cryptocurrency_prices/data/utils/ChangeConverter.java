package com.defaultapps.android_cryptocurrency_prices.data.utils;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;

public class ChangeConverter {

    public static String getUsdChangesPrices(ResponseFileModel coin) {
        float price = Float.parseFloat(coin.getPriceUsd());
        float percents = Float.parseFloat(coin.getPercentChange1h());
        float changePrice = price - (price / 100) * (100 - percents);
        return Float.toString(round(changePrice, 2));
    }

    private static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        double tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / pow;
    }
}

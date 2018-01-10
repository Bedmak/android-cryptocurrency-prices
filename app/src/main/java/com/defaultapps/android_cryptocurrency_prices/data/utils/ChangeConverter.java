package com.defaultapps.android_cryptocurrency_prices.data.utils;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;

/**
 * Created by Bedmak on 09.01.2018.
 */

public class ChangeConverter {

    public static String getUsdChangesPrices(ResponseFileModel coin) {
        float price = Float.parseFloat(coin.getPriceUsd());
        float percents = Float.parseFloat(coin.getPercentChange1h());
        return Float.toString(price - (price / 100) * (100 - percents));
    }
}

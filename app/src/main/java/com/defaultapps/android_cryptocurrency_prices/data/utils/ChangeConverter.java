package com.defaultapps.android_cryptocurrency_prices.data.utils;


public class ChangeConverter {

    public static String getChangesPrices(float price, float percents) {
        float changePrice = price - (price / 100) * (100 - percents);
        return Float.toString(round(changePrice));
    }

    private static float round(float number) {
        double tmp = number * 100;
        return (float) (int) ((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / 100;
    }
}

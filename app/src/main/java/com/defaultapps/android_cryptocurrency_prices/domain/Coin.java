package com.defaultapps.android_cryptocurrency_prices.domain;


public class Coin {
    private String id;
    private String symbol;
    private String name;
    private float price;
    private float percentChange;
    private String percentChange1h;
    private String percentChange24h;
    private String percentChange7d;

    public Coin() {

    }

    public Coin(String id, String name, String symbol,
                float price, float change, String change1h, String change24h, String change7d) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.percentChange = change;
        this.percentChange1h = change1h;
        this.percentChange24h = change24h;
        this.percentChange7d = change7d;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getPrice() {
        return price;
    }

    public float getPercentChange() { return percentChange; }

    public String getPercentChange1h() {
        return percentChange1h;
    }

    public String getPercentChange24h() {
        return percentChange24h;
    }

    public String getPercentChange7d() {
        return percentChange7d;
    }
}

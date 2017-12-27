package com.defaultapps.android_cryptocurrency_prices.ui;

import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponsePresenterImpl implements ResponsePresenter {

    private CryptocurrencyOverviewImpl cryptoOverview = new CryptocurrencyOverviewImpl(); // MVP - Model

    private View view;  // MVP - View - MainActivity

    @Override
    public void onAttach(View view) {
        this.view = view;
    }

    @Override
    public void overview() {
        cryptoOverview.getCoins(new Callback<List<ResponseFileModel>>() {
            @Override
            public void onResponse(Call<List<ResponseFileModel>> call, Response<List<ResponseFileModel>> response) {
                List<ResponseFileModel> coins = response.body();
                if (coins != null && !coins.isEmpty()) {
                    view.showCoin(coins.get(0).getName() + " - " + coins.get(0).getPriceUsd());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseFileModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDetach() {
        view = null;
    }
}

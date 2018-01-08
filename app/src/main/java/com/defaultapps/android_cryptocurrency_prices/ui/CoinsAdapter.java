package com.defaultapps.android_cryptocurrency_prices.ui;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.ResponseFileModel;
import com.defaultapps.android_cryptocurrency_prices.data.overview.CryptocurrencyOverviewImpl;

import java.util.List;

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder> {

    private CryptocurrencyOverviewImpl cryptoOverview = new CryptocurrencyOverviewImpl();

    List<ResponseFileModel> coins;

    private int changeFlag = 1;

    public CoinsAdapter(List<ResponseFileModel> coins) {
        this.coins = coins;
    }

    public static class CoinsViewHolder extends RecyclerView.ViewHolder {

        LinearLayout coinContainer;
        TextView coinName;
        TextView coinPrice;
        TextView coinChange;

        public CoinsViewHolder(View v) {
            super(v);
            coinContainer = v.findViewById(R.id.coinContainer);
            coinName = v.findViewById(R.id.coinName);
            coinPrice = v.findViewById(R.id.coinPrice);
            coinChange = v.findViewById(R.id.coinChange);
        }
    }

    @Override
    public CoinsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin, parent, false);
        final CoinsViewHolder vh = new CoinsViewHolder(v);
        vh.coinChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changeFlag == 1) {
                    changeFlag = 2;
                } else {
                    changeFlag = 1;
                }
                notifyDataSetChanged();
            }
        });

        vh.coinContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // вывод подробной информации
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(CoinsViewHolder holder, int position) {
        ResponseFileModel coin = coins.get(position);
        holder.coinName.setText(coin.getName());
        holder.coinPrice.setText(coin.getPriceUsd());
        if (changeFlag == 1) {
            holder.coinChange.setText(coin.getPercentChange1h() + " %");
        } else {
            holder.coinChange.setText(cryptoOverview.getUsdChangesPrices(coin));
        }
        if (Float.parseFloat(coin.getPercentChange1h()) > 0) {
            holder.coinChange.setBackgroundColor(Color.GREEN);
        } else {
            holder.coinChange.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }
}

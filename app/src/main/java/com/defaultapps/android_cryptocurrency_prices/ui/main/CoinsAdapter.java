package com.defaultapps.android_cryptocurrency_prices.ui.main;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.data.utils.ChangeConverter;
import com.defaultapps.android_cryptocurrency_prices.ui.detailed.DetailedActivity;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private final List<CoinModel> coins;

    private int changeFlag = 1;
    private boolean isLoadingAdded = false;

    CoinsAdapter() {
        coins = new ArrayList<>();
    }

    static class CoinsViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        LinearLayout coinContainer;
        TextView coinName;
        TextView coinPrice;
        TextView coinChange;

        CoinsViewHolder(View v) {
            super(v);
            context = v.getContext();
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
                Timber.d("Change onClick");
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
                Timber.d("onClick - " + vh.getAdapterPosition() + " position");
                Intent intent = new Intent(vh.context, DetailedActivity.class);
                intent.putExtra(DetailedActivity.COIN_NO, vh.getAdapterPosition());
                vh.context.startActivity(intent);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(CoinsViewHolder holder, int position) {
        CoinModel coin = coins.get(position);
        holder.coinName.setText(String.format("%s (%s)", coin.getName(), coin.getSymbol()));
        holder.coinPrice.setText(coin.getPriceUsd());
        if (changeFlag == 1) {
            holder.coinChange.setText(String.format("%s %%", coin.getPercentChange1h()));
        } else {
            holder.coinChange.setText(ChangeConverter.getUsdChangesPrices(coin));
        }
        /* if (Float.parseFloat(coin.getPercentChange1h()) > 0) {
            holder.coinChange.setBackgroundColor(Color.GREEN);
        } else {
            holder.coinChange.setBackgroundColor(Color.RED);
        } */
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == coins.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void setData(List<CoinModel> responseCoins) {
        coins.clear();
        coins.addAll(responseCoins);
        notifyDataSetChanged();
    }

    public void add(CoinModel coin) {
        coins.add(coin);
        notifyItemInserted(coins.size() - 1);
    }

    public void addAll(List<CoinModel> cList) {
        for (CoinModel c : cList) {
            add(c);
        }
    }

    public void remove(CoinModel city) {
        int position = coins.indexOf(city);
        if (position > -1) {
            coins.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new CoinModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = coins.size() - 1;
        CoinModel item = getItem(position);

        if (item != null) {
            coins.remove(position);
            notifyItemRemoved(position);
        }
    }

    public CoinModel getItem(int position) {
        return coins.get(position);
    }
}

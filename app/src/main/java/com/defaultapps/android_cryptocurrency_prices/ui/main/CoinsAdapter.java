package com.defaultapps.android_cryptocurrency_prices.ui.main;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.models.CoinModel;
import com.defaultapps.android_cryptocurrency_prices.data.utils.ChangeConverter;
import com.defaultapps.android_cryptocurrency_prices.data.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class CoinsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MainContract.MainView mainView;
    private final List<CoinModel> coins;

    private int changeFlag = 1;
    private boolean isLoadingAdded = false;

    CoinsAdapter(MainContract.MainView mainView) {
        this.mainView = mainView;
        coins = new ArrayList<>();
    }

    public class CoinsViewHolder extends RecyclerView.ViewHolder {

        LinearLayout coinContainer;
        ImageView coinImg;
        TextView coinName;
        TextView coinPrice;
        TextView coinChange;

        CoinsViewHolder(View v) {
            super(v);
            coinContainer = v.findViewById(R.id.coinContainer);
            coinImg = v.findViewById(R.id.coinImg);
            coinName = v.findViewById(R.id.coinName);
            coinPrice = v.findViewById(R.id.coinPrice);
            coinChange = v.findViewById(R.id.coinChange);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case Constants.ITEM:
                viewHolder = getCoinsViewHolder(parent,inflater);
                break;
            case Constants.LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(v2);
                break;
        }
        return viewHolder;
    }

    private RecyclerView.ViewHolder getCoinsViewHolder(ViewGroup parent, LayoutInflater inflater) {
        final CoinsViewHolder vh;
        View v1 = inflater.inflate(R.layout.item_coin, parent, false);
        vh = new CoinsViewHolder(v1);

        vh.coinChange.setOnClickListener(view -> {
            Timber.d("Change onClick");
            if (changeFlag == 1) {
                changeFlag = 2;
            } else {
                changeFlag = 1;
            }
            notifyDataSetChanged();
        });
        vh.coinContainer.setOnClickListener(view -> mainView.showDetailed(vh.getAdapterPosition()));
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CoinModel coin = coins.get(position);

        switch (getItemViewType(position)) {
            case Constants.ITEM:
               final CoinsViewHolder coinsVH = (CoinsViewHolder) holder;
                Glide
                        .with(holder.itemView)
                        .load(Constants.IMAGE_BASE_URL + coin.getId() + Constants.IMAGE_FORMAT)
                        .into(coinsVH.coinImg);

                coinsVH.coinName.setText(String.format("%s (%s)", coin.getName(), coin.getSymbol()));
                coinsVH.coinPrice.setText(coin.getPriceUsd());
                if (changeFlag == 1) {
                    coinsVH.coinChange.setText(String.format("%s %%", coin.getPercentChange1h()));
                } else {
                    coinsVH.coinChange.setText(ChangeConverter.getUsdChangesPrices(coin));
                }
                if (Float.parseFloat(coin.getPercentChange1h()) > 0) {
                    coinsVH.coinChange.setBackgroundColor(Color.GREEN);
                } else {
                    coinsVH.coinChange.setBackgroundColor(Color.RED);
                }
                break;
            case Constants.LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == coins.size() - 1 && isLoadingAdded) ? Constants.LOADING : Constants.ITEM;
    }

    /*
    Helpers methods
     */
    public void add(CoinModel coin) {
        coins.add(coin);
        notifyItemInserted(coins.size() - 1);
    }

    public void addAll(List<CoinModel> cList) {
        for (CoinModel c : cList) {
            add(c);
        }
    }

    public void remove(CoinModel coin) {
        int position = coins.indexOf(coin);
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

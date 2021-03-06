package com.defaultapps.android_cryptocurrency_prices.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.defaultapps.android_cryptocurrency_prices.domain.Coin;
import com.defaultapps.android_cryptocurrency_prices.R;
import com.defaultapps.android_cryptocurrency_prices.data.utils.ChangeConverter;
import com.defaultapps.android_cryptocurrency_prices.data.utils.Constants;
import com.defaultapps.android_cryptocurrency_prices.di.ActivityContext;
import com.defaultapps.android_cryptocurrency_prices.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CoinsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private MainContract.MainView mainView;
    private final List<Coin> coins;

    private int changeFlag = 1;
    private boolean isLoadingAdded = false;

    @Inject
    CoinsAdapter(@ActivityContext Context context, MvpView mvpView) {
        this.context = context;
        this.mainView = (MainContract.MainView) mvpView;
        coins = new ArrayList<>();
    }

    public class CoinsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.coinContainer) LinearLayout coinContainer;
        @BindView(R.id.coinImg) ImageView coinImg;
        @BindView(R.id.coinName) TextView coinName;
        @BindView(R.id.coinPrice) TextView coinPrice;
        @BindView(R.id.coinChange) TextView coinChange;

        CoinsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
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
        LayoutInflater inflater = LayoutInflater.from(context);
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
        vh.coinContainer.setOnClickListener(view ->
                mainView.showDetailed(vh.getAdapterPosition(), coins.get(vh.getAdapterPosition()).getName()));
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Coin coin = coins.get(position);

        switch (getItemViewType(position)) {
            case Constants.ITEM:
               final CoinsViewHolder coinsVH = (CoinsViewHolder) holder;
                Glide
                        .with(context)
                        .load(Constants.IMAGE_BASE_URL + coin.getId() + Constants.IMAGE_FORMAT)
                        .into(coinsVH.coinImg);

                coinsVH.coinName.setText(String.format("%s (%s)", coin.getName(), coin.getSymbol()));
                coinsVH.coinPrice.setText(String.format("%s", coin.getPrice()));
                if (changeFlag == 1) {
                    coinsVH.coinChange.setText(String.format("%s %%", coin.getPercentChange()));
                } else {
                    coinsVH.coinChange.setText(ChangeConverter.getChangesPrices(coin.getPrice(), coin.getPercentChange()));
                }
                if (coin.getPercentChange() > 0) {
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
    public void add(Coin coin) {
        coins.add(coin);
        notifyItemInserted(coins.size() - 1);
    }

    public void addAll(List<Coin> cList) {
        for (Coin c : cList) {
            add(c);
        }
    }

    public void remove(Coin coin) {
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
        add(new Coin());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = coins.size() - 1;
        Coin coin = getItem(position);

        if (coin != null) {
            coins.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Coin getItem(int position) {
        return coins.get(position);
    }
}

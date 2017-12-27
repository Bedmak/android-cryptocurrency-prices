package com.defaultapps.android_cryptocurrency_prices.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.defaultapps.android_cryptocurrency_prices.R;



public class MainActivity extends AppCompatActivity implements View {

    TextView responseView;
    private ResponsePresenterImpl pressenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseView = findViewById(R.id.text_view);
        pressenter = new ResponsePresenterImpl();
        pressenter.onAttach(this);
        pressenter.overview();

    }

    @Override
    public void showCoin(String coin) {
        responseView.setText(coin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pressenter.onDetach();
    }
}

package com.ps.sm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;

import com.ps.sm.activity.HomeActivity;
import com.ps.sm.common.StockMarketBaseActivity;
import com.ps.sm.databinding.ActivityMainBinding;
import com.ps.sm.session.SessionManager;


public class MainActivity extends StockMarketBaseActivity {
    private String TAG = "MainActivity";
    protected int SPLASH_TIME_OUT = 2000;
    Context mContext;
    SessionManager sessionManager;
    ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initValues();
        startSplashTimer();
    }

    private void initValues() {
        mContext = this;
        sessionManager = new SessionManager(mContext);
    }

    private void startSplashTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                enterActivityAnimation();
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
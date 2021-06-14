package com.ps.sm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ps.sm.R;
import com.ps.sm.common.StockMarketBaseActivity;
import com.ps.sm.databinding.ActivityItemFilterBinding;
import com.ps.sm.session.SessionManager;

public class ItemFilterActivity extends StockMarketBaseActivity {
    private String TAG = "ItemFilterActivity";
    ActivityItemFilterBinding binding;
    Context mContext;
    SessionManager sessionManager;
    private boolean ratioLLIsVisible = false;
    private  boolean rewardsLLIsVisible= false;
    private  boolean riskLLIsVisible= false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_item_filter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_filter);
        initValues();
        clickEvents();
    }

    private void initValues() {
        mContext = this;
        sessionManager = new SessionManager(mContext);

    }

    private void clickEvents() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                closeToBottomAnimation();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                closeToBottomAnimation();
            }
        });
     binding.ratioCB.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (ratioLLIsVisible == false) {
                 binding.ratioLL.setVisibility(View.VISIBLE);
                 ratioLLIsVisible = true;
             }
             else if (ratioLLIsVisible==true)
             {
                 binding.ratioLL.setVisibility(View.GONE);
                 ratioLLIsVisible=false;
             }

         }
     });
        binding.rewardsCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardsLLIsVisible == false) {
                    binding.rewardsLL.setVisibility(View.VISIBLE);
                    rewardsLLIsVisible = true;
                }
                else if (rewardsLLIsVisible==true)
                {
                    binding.rewardsLL.setVisibility(View.GONE);
                    rewardsLLIsVisible=false;
                }

            }
        });
        binding.riskCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (riskLLIsVisible == false) {
                    binding.riskLL.setVisibility(View.VISIBLE);
                    riskLLIsVisible = true;
                }
                else if (riskLLIsVisible==true)
                {
                    binding.riskLL.setVisibility(View.GONE);
                    riskLLIsVisible=false;
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        closeToBottomAnimation();
    }
}
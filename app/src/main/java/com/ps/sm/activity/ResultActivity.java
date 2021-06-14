package com.ps.sm.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ps.sm.R;
import com.ps.sm.adapter.CategoryAdapter;
import com.ps.sm.adapter.ResultAdapter;
import com.ps.sm.adapter.StrategyAdapter;
import com.ps.sm.common.StockMarketBaseActivity;
import com.ps.sm.database.StockDAO;
import com.ps.sm.databinding.ActivityResultBinding;
import com.ps.sm.dto.ResultDTO;
import com.ps.sm.dto.StrategyDTO;
import com.ps.sm.session.SessionManager;

import java.util.ArrayList;

public class ResultActivity extends StockMarketBaseActivity {
    private String TAG = "ResultActivity";
    SessionManager sessionManager;
    Context mContext;
    ActivityResultBinding binding;
    Bundle bundle;
    ArrayList<Integer> integerArrayList;
    ResultAdapter resultAdapter;
    StrategyAdapter strategyAdapter;
    public ArrayList<StrategyDTO> strategyDTOArrayList;
    public ArrayList<ResultDTO> resultDTOs;
    StockDAO stockDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result);
        initValues();
        setValues();
        clickEvents();
        getArrayForStrategy();
        setData();
        setResultAdapter();
        /* setRecyleStrategyAdapter();*/
    }

    private void setValues() {
        integerArrayList = new ArrayList<>();
        try {
            if (bundle != null) {
                binding.resultTV.setText(bundle.getString("title"));
                binding.tvBankName.setText(bundle.getString("bankName"));
                binding.tvCategory.setText(bundle.getString("catName"));
                binding.tvStrategy.setText("Strategy : " + bundle.getString("strategyName"));
                binding.tvQuantity.setText("Quantity : " + bundle.getString("quantity"));
            }
        } catch (Exception e) {
            Log.d(TAG, "setValues:Error\t\t\t " + e);
        }
    }

    private void initValues() {
        mContext = this;
        sessionManager = new SessionManager(mContext);
        bundle = getIntent().getExtras();
        strategyDTOArrayList = new ArrayList<>();
        resultDTOs = new ArrayList<>();
        stockDAO = new StockDAO(ResultActivity.this);
    }

    private void clickEvents() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                exitActivityAnimation();
            }
        });

        binding.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ItemFilterActivity.class));
                openToTopAnimation();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        exitActivityAnimation();
    }

    private void setResultAdapter() {
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        resultAdapter = new ResultAdapter(mContext, resultDTOs);
        binding.recycleResultView.setLayoutManager(horizontalLayoutManager1);
        binding.recycleResultView.setAdapter(resultAdapter);

    }

    public void getArrayForStrategy() {
        strategyDTOArrayList.clear();
        stockDAO.open();
        strategyDTOArrayList = stockDAO.getStrategy(bundle.getString("cat_id"));
        stockDAO.close();
        Log.d(TAG, "setArrayForStrategy: " + strategyDTOArrayList.size());
        if (CategoryAdapter.categoryAdapter != null) {
            if (CategoryAdapter.categoryAdapter.selectedPosition == -1) {
                strategyDTOArrayList.clear();
            }
        }
        setStrategyAdapter();
    }

    private void setData() {
        resultDTOs.clear();
        if (FillValuesActivity.valuesActivity != null) {
            int strike = Integer.parseInt(FillValuesActivity.valuesActivity.bundle.getString("strikeGap"));
            int total_strike = Integer.parseInt(FillValuesActivity.valuesActivity.bundle.getString("totalStrike"));
            Log.d(TAG, "setData: " + strike + "\t\t\ttotal_strike\t\t\t" + total_strike);
            int strike1 = 0;
            int mid = 0 + ((FillValuesActivity.valuesActivity.storeDTOArrayList.size() - 1) - 0) / 2;
            int index = 0;
            for (int i = mid; i < FillValuesActivity.valuesActivity.storeDTOArrayList.size(); i++) {
                index = i;
                for (int j = 0; j < total_strike; j++) {
                    ResultDTO resultDTO = new ResultDTO();
                    if (j == 0) {
                        strike1 = FillValuesActivity.valuesActivity.storeDTOArrayList.get(i).getPrice_val();
                    }
                    resultDTO.setStrike1(strike1 + "");
                    resultDTO.setStrike2((FillValuesActivity.valuesActivity.storeDTOArrayList.get(i).getPrice_val() + (strike * (j + 1))) + "");
                    resultDTO.setPrice1(FillValuesActivity.valuesActivity.storeDTOArrayList.get(i).getCall_val());
                    // we are adding index as we need next row 1 index
                    resultDTO.setPrice2(FillValuesActivity.valuesActivity.storeDTOArrayList.get(index + 1).getCall_val());

                    double risk = Double.parseDouble(FillValuesActivity.valuesActivity.storeDTOArrayList.get(i).getCall_val()) -
                            Double.parseDouble(FillValuesActivity.valuesActivity.storeDTOArrayList.get(index + 1).getCall_val());
                    /*Double.parseDouble(FillValuesActivity.valuesActivity.storeDTOArrayList.get(i).getPut_val());*/

                    resultDTO.setRisk((risk * Double.parseDouble(bundle.getString("quantity"))) + "");


                    double reward = (Double.parseDouble(resultDTO.getStrike1()) - Double.parseDouble(resultDTO.getStrike2())) - risk;
                    resultDTO.setReward((reward * Double.parseDouble(bundle.getString("quantity"))) + "");
                    double ubev = Double.parseDouble(resultDTO.getStrike1()) + risk;
                    resultDTO.setUbev(ubev + ""); // break even point
                    resultDTO.setLbev(ubev + ""); // break even point
                    double ratio = 0;
                    try {
                        ratio = reward / risk;
                    } catch (Exception e) {
                        Log.d(TAG, "setData:\tException " + e);
                    }
//                    resultDTO.setRatio(ratio + ""); // break even point
                    resultDTO.setRatio(String.format("%.2f", ratio));
//                    resultDTO.setRisk(risk);
                    resultDTO.setStrategy(strategyDTOArrayList.get(0).getName());
                    resultDTOs.add(resultDTO);
                    index++;
                }
                total_strike--;
                /* index++;*/
            }
        }
    }


//    Max Risk= Net Premium =30 -25 = 5
//    Max Reward = Difference between two
//    FORMULAE
//    strike-net premium= 20-5 = 15
//    Breakeven Point (BEP) = Lower strike + Net
//    premium = 720 + 5 = 725


    private void setStrategyAdapter() {
        strategyAdapter = new StrategyAdapter(mContext, strategyDTOArrayList);
        binding.recycleStrategy.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        binding.recycleStrategy.setAdapter(strategyAdapter);
    }


}
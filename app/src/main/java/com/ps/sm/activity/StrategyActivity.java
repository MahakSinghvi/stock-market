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
import com.ps.sm.adapter.StrategyAdapter;
import com.ps.sm.common.StockMarketBaseActivity;
import com.ps.sm.database.StockDAO;
import com.ps.sm.databinding.ActivityStrategyBinding;
import com.ps.sm.dto.CategoryDTO;
import com.ps.sm.dto.ItemDTO;
import com.ps.sm.dto.StrategyDTO;
import com.ps.sm.session.SessionManager;

import java.util.ArrayList;

public class StrategyActivity extends StockMarketBaseActivity {
    private String TAG = "StrategyActivity";
    SessionManager sessionManager;
    Context mContext;
    ActivityStrategyBinding binding;
    ArrayList<CategoryDTO> categoryDTOArrayList;
    CategoryAdapter categoryAdapter;
    StrategyAdapter strategyAdapter;
    public ArrayList<StrategyDTO> strategyDTOArrayList;
    public static StrategyActivity strategyActivity;
    StockDAO stockDAO;
    Bundle bundle;
    ArrayList<Integer> integerArrayList;
    ItemDTO itemDTO;
    String cat_id = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_strategy);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_strategy);
        initValues();
        getCategoryItems();
        addCategoryItemToArray();
        /*getArrayForStrategy(0, 0);*/
        addStrategyItemToArray();
        clickEvents();
    }

    private void initValues() {
        integerArrayList = new ArrayList<>();
        strategyActivity = this;
        mContext = this;
        sessionManager = new SessionManager(mContext);
        categoryDTOArrayList = new ArrayList<>();
        strategyDTOArrayList = new ArrayList<>();
//        strategyDTOArrayList.add(new StrategyDTO("", "", ""));
        stockDAO = new StockDAO(StrategyActivity.this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            binding.textName.setText(bundle.getString("title"));

        }


    }

    private void addCategoryItemToArray() {
        if (categoryDTOArrayList.size() < 1) {
            CategoryDTO categoryDTO1 = new CategoryDTO("Volatile");
            CategoryDTO categoryDTO2 = new CategoryDTO("Non-Volatile");
            CategoryDTO categoryDTO3 = new CategoryDTO("Bullish");
            CategoryDTO categoryDTO4 = new CategoryDTO("Bearish");
            StockDAO _stockDAO_ = new StockDAO(StrategyActivity.this);
            _stockDAO_.open();
            _stockDAO_.addCategoryToLocalDB(categoryDTO1);
            _stockDAO_.addCategoryToLocalDB(categoryDTO2);
            _stockDAO_.addCategoryToLocalDB(categoryDTO3);
            _stockDAO_.addCategoryToLocalDB(categoryDTO4);
            _stockDAO_.close();
            getCategoryItems();
            Log.d(TAG, "getCategoryItems:\t\tafter notify " + categoryDTOArrayList.size());
        }
    }

    private void addStrategyItemToArray() {
        if (strategyDTOArrayList.size() < 1) {
            StockDAO _stockDAO_ = new StockDAO(StrategyActivity.this);
            _stockDAO_.open();
            _stockDAO_.deleteStrategyFromLocalDB();
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("1", "Long Straddle", categoryDTOArrayList.get(0).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("2", "Long Strangle", categoryDTOArrayList.get(0).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("3", "Long guts", categoryDTOArrayList.get(0).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("4", "Strap", categoryDTOArrayList.get(0).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("5", "Strip", categoryDTOArrayList.get(0).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("6", "Short call Butterfly", categoryDTOArrayList.get(0).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("7", "Short call Condor", categoryDTOArrayList.get(0).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("8", "Short Put Butterfly", categoryDTOArrayList.get(0).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("9", "Short  Put Condor", categoryDTOArrayList.get(0).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("10", "Long Iron Butterfly", categoryDTOArrayList.get(0).getId()));

            //when category is 2
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("11", "Short Straddle", categoryDTOArrayList.get(1).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("12", "Short Strangle", categoryDTOArrayList.get(1).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("13", "Short guts", categoryDTOArrayList.get(1).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("14", "Long call Butterfly", categoryDTOArrayList.get(1).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("15", "Long call Condor", categoryDTOArrayList.get(1).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("16", "Short Iron Butterfly", categoryDTOArrayList.get(1).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("17", "Long  Put Condor", categoryDTOArrayList.get(1).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("18", " My Strategy", categoryDTOArrayList.get(1).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("19", "Long Put Butterfly", categoryDTOArrayList.get(1).getId()));
            // when catregory is 3
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("20", " Bull Call Spread", categoryDTOArrayList.get(2).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("21", " Bull PUt Spread", categoryDTOArrayList.get(2).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("22", "Call Ratio Back Spread", categoryDTOArrayList.get(2).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("23", "Bear Call Ladder", categoryDTOArrayList.get(2).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("24", "Long  Combo", categoryDTOArrayList.get(2).getId()));
            //when category is 4
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("25", " Bear Put Spread", categoryDTOArrayList.get(3).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("26", " Bear Call Spread", categoryDTOArrayList.get(3).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("27", "Put  Ratio Back Spread", categoryDTOArrayList.get(3).getId()));
            _stockDAO_.addStrategyToLocalDB(new StrategyDTO("28", "Bull Call Ladder", categoryDTOArrayList.get(3).getId()));
            _stockDAO_.close();
        }
    }

    private void getCategoryItems() {
        categoryDTOArrayList.clear();
        stockDAO.open();
        categoryDTOArrayList = stockDAO.getAllCategory();
        stockDAO.close();
        setCategoryAdapter();
        Log.d(TAG, "getCategoryItems: " + categoryDTOArrayList.size());
    }

//    private void getAllStrategy() {
//        strategyDTOArrayList.clear();
//        stockDAO.open();
//        strategyDTOArrayList = stockDAO.getStrategy(arr)
//
//
//
//    }

  /*  private void getStrategyItems() {
        strategyDTOArrayList.clear();
        stockDAO.open();
        strategyDTOArrayList = stockDAO.getStrategy();
        stockDAO.close();
        Log.d(TAG, "getStrategyItems: " + strategyDTOArrayList.size());
    }*/

    private void setCategoryAdapter() {
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        categoryAdapter = new CategoryAdapter(mContext, categoryDTOArrayList);
        binding.recycleCategory.setLayoutManager(horizontalLayoutManager1);
        binding.recycleCategory.setAdapter(categoryAdapter);
    }

    private void setStrategyAdapter() {
        strategyAdapter = new StrategyAdapter(mContext, strategyDTOArrayList);
        binding.recycleStrategy.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recycleStrategy.setAdapter(strategyAdapter);
    }

    public void getArrayForStrategy(int loopStart, int loopStop) {
        strategyDTOArrayList.clear();
        stockDAO.open();
        cat_id = categoryDTOArrayList.get(loopStart).getId();
        strategyDTOArrayList = stockDAO.getStrategy(categoryDTOArrayList.get(loopStart).getId());
        stockDAO.close();
        Log.d(TAG, "setArrayForStrategy: " + strategyDTOArrayList.size());
        if (CategoryAdapter.categoryAdapter != null) {
            if (CategoryAdapter.categoryAdapter.selectedPosition == -1) {
                strategyDTOArrayList.clear();
            }
        }
        setStrategyAdapter();
    }

    private void clickEvents() {
        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etBank.getText().toString().trim().length() < 1) {
                    customToast("Enter name", true);
                } else {
                    binding.nestedView.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();

            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                exitActivityAnimation();
            }
        });
    }

    private void checkValidation() {
        if (CategoryAdapter.categoryAdapter.selectedPosition == -1) {
            customToast("Select Category", true);
        } /*else if (StrategyAdapter.strategyAdapter.selectedPosition == -1) {
            customToast("Select Strategy", true);
        } */ else {

            String catName = categoryDTOArrayList.get(CategoryAdapter.categoryAdapter.selectedPosition).getName();
            /*String strategyName = strategyDTOArrayList.get(StrategyAdapter.strategyAdapter.selectedPosition).getName();*/

            startActivity(new Intent(mContext, ResultActivity.class)
                    .putExtra("catName", catName)
                    .putExtra("strategyName", "")
                    .putExtra("quantity", bundle.getString("lotSize"))
                    .putExtra("title", binding.textName.getText().toString().trim())
                    .putExtra("bankName", "")
                    .putExtra("buyValue", "")
                    .putExtra("sellValue", "")
                    .putExtra("cat_id", cat_id)
            );
            enterActivityAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        exitActivityAnimation();
    }
}
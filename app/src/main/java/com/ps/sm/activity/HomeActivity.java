package com.ps.sm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ps.sm.R;
import com.ps.sm.adapter.AllItemsAdapter;
import com.ps.sm.common.StockMarketBaseActivity;
import com.ps.sm.database.StockDAO;
import com.ps.sm.databinding.ActivityHomeBinding;
import com.ps.sm.dto.CategoryDTO;
import com.ps.sm.dto.ItemDTO;
import com.ps.sm.session.SessionManager;

import java.util.ArrayList;


public class HomeActivity extends StockMarketBaseActivity {
    private String TAG = "HomeActivity";
    private SessionManager sessionManager;
    private Context mContext;
    private ActivityHomeBinding binding;
    private AllItemsAdapter allItemsAdapter;
    private ArrayList<ItemDTO> itemDTOs;
    private StockDAO stockDAO;
    public static HomeActivity homeActivity;
    private String vSearch = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initValues();
        setDataArray();
        clickEvents();
    }

    private void clickEvents() {
        binding.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ItemFilterActivity.class));
//                openToTopAnimation();
//                enterActivityAnimation();
            }
        });

        binding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AddActivity.class));
                enterActivityAnimation();
            }
        });
        binding.searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                vSearch = s.toString();
                setDataArray();
            }
        });
    }

    private void initValues() {
        homeActivity = this;
        mContext = this;
        sessionManager = new SessionManager(mContext);
        stockDAO = new StockDAO(HomeActivity.this);
        itemDTOs = new ArrayList<>();
    }

    public void setDataArray() {
        itemDTOs.clear();
        stockDAO.open();
        itemDTOs = stockDAO.getAllSurveyData(vSearch);
        stockDAO.close();
        binding.txtCenterToolbar.setText("All items (" + itemDTOs.size() + ")");
        Log.d(TAG, "onCreate: " + itemDTOs.size());
        setItemAdapter();
    }

    private void setItemAdapter() {
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        allItemsAdapter = new AllItemsAdapter(mContext, itemDTOs);
        binding.recycleAllItems.setLayoutManager(horizontalLayoutManager1);
        binding.recycleAllItems.setAdapter(allItemsAdapter);
    }

    public void deleteItem(int pos) {
        stockDAO.open();
        boolean isDeleted = stockDAO.deleteStockById(itemDTOs.get(pos).getId());
        stockDAO.close();
        if (isDeleted) {
            itemDTOs.remove(pos);
            allItemsAdapter.notifyDataSetChanged();
            binding.txtCenterToolbar.setText("All items (" + (itemDTOs.size()) + ")");
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

    }
}
package com.ps.sm.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ps.sm.R;
import com.ps.sm.adapter.CategoryAdapter;
import com.ps.sm.adapter.ValuesAdapter;
import com.ps.sm.common.StockMarketBaseActivity;
import com.ps.sm.database.StockDAO;
import com.ps.sm.databinding.ActivityFillValuesBinding;
import com.ps.sm.dto.StoreDTO;
import com.ps.sm.dto.StrategyDTO;
import com.ps.sm.session.SessionManager;

import java.util.ArrayList;
import java.util.Collections;

public class FillValuesActivity extends StockMarketBaseActivity {
    private String TAG = "AddNextActivity";
    SessionManager sessionManager;
    Context mContext;
    ActivityFillValuesBinding binding;
    ValuesAdapter valuesAdapter;
    int _value = 0;
    //    ArrayList<Integer> integerArrayList;
    int strikeGapValue = 0;
    int totalStrikeValue = 0;
    public Bundle bundle;
   public ArrayList<StoreDTO> storeDTOArrayList;
    StockDAO stockDAO;
public static  FillValuesActivity valuesActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_next);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fill_values);
        valuesActivity=this;
        initValues();
//        setItemAdapter();
        clickEvents();
        getArrayForFillValues();
    }

    private void clickEvents() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                exitActivityAnimation();
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockDAO.open();
                boolean isDeleted=stockDAO.deleteStoreValueById(bundle.getString("stock_id"));
                stockDAO.close();
                Toast.makeText(mContext, ""+isDeleted, Toast.LENGTH_SHORT).show();
                storeValueInDB();
              /*  startActivity(new Intent(mContext, StrategyActivity.class)
                        .putExtra("title", binding.tvName.getText().toString().trim())
                        .putExtra("lotSize", bundle.getString("lotSize"))
                );

                enterActivityAnimation();*/

            }
        });

        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.etCmp.getText().toString().trim().length() < 1) {
                    customToast("Enter CMP", true);
                } else {
                    /* integerArrayList.clear();*/
                    storeDTOArrayList.clear();
                    binding.linearResult.setVisibility(View.VISIBLE);
                    _value = Integer.parseInt(binding.etCmp.getText().toString().trim());
                    getNewValue();
                    getArrayValues();
                    setTopAdapter();
                }

            }
        });
    }

    private void storeValueInDB() {
        try {
            StockDAO stockDAO = new StockDAO(FillValuesActivity.this);
            stockDAO.open();
            if (ValuesAdapter.valuesAdapter != null) {
                for (int i = 0; i < storeDTOArrayList.size(); i++) {
                    StoreDTO storeDTO = new StoreDTO();
                    storeDTO.setStock_id(bundle.getString("stock_id"));
                    storeDTO.setCmp(binding.etCmp.getText().toString().trim());
                    storeDTO.setCall_val(storeDTOArrayList.get(i).getCall_val());
                    storeDTO.setPrice_val(storeDTOArrayList.get(i).getPrice_val());
                    storeDTO.setPut_val(storeDTOArrayList.get(i).getPut_val());
                    stockDAO.addStoreValueToLocalDB(storeDTO);
                    Log.d(TAG, "storeValueInDB:\t.binding.etPut\t\n " + storeDTOArrayList.get(i).getCall_val()+"--"+storeDTOArrayList.get(i).getPut_val());
                }
            }
            stockDAO.close();
            startActivity(new Intent(mContext, StrategyActivity.class)
                    .putExtra("title", binding.tvName.getText().toString().trim())
                    .putExtra("lotSize", bundle.getString("lotSize"))
            );
            enterActivityAnimation();

        } catch (Exception e) {
            Log.d(TAG, "storeValueInDB: " + e);
        }
    }

    String convertValue;

    private void getNewValue() {
        try {

            int vaaal = Integer.parseInt(binding.etCmp.getText().toString().trim());
            int lastdigit = (vaaal % 10);
            int _other = 0;
            if (lastdigit == 0 || lastdigit == 1 || lastdigit == 2 || lastdigit == 8 || lastdigit == 9) {
                _other = 0;
            }
            if (lastdigit == 3 || lastdigit == 4 || lastdigit == 5 || lastdigit == 6 || lastdigit == 7) {
                _other = 5;
            }
            convertValue = String.valueOf(vaaal);
            convertValue = convertValue.replace(String.valueOf(lastdigit), String.valueOf(_other));
            if (lastdigit == 8 || lastdigit == 9) {
                int _CV = Integer.parseInt(convertValue) + 10;
                convertValue = String.valueOf(_CV);
            }
            Log.d(TAG, "getNewValue: " + lastdigit + "\t\t\t" + _other + "\t\t\t\t" + convertValue);
            _value = Integer.parseInt(convertValue);
        } catch (Exception e) {
            Log.d(TAG, "getNewValue:Exception " + e);
        }

    }

    private void getArrayValues() {
        try {
            for (int i = totalStrikeValue; i > 0; i--) {
//                _value = _value - 10;
                _value = _value - strikeGapValue;
//                integerArrayList.add(_value);
                StoreDTO storeDTO = new StoreDTO("", _value, "");
                storeDTOArrayList.add(storeDTO);

            }
//            Collections.reverse(integerArrayList);
            Collections.reverse(storeDTOArrayList);
//            integerArrayList.add(Integer.parseInt(convertValue));
            StoreDTO storeDTO = new StoreDTO("", Integer.parseInt(convertValue), "");
            storeDTOArrayList.add(storeDTO);
            _value = Integer.parseInt(convertValue);
            for (int i = 0; i < totalStrikeValue; i++) {
//                _value = _value + 10;
                _value = _value + strikeGapValue;
//                integerArrayList.add(_value);
                StoreDTO storeDTO1 = new StoreDTO("", _value, "");
                storeDTOArrayList.add(storeDTO1);

            }
          /*  Log.d(TAG, "integerArrayList: " + integerArrayList.toString());
            Log.d(TAG, "integerArrayList:size " + integerArrayList.size());*/
            Log.d(TAG, "storeDTOArrayList: " + storeDTOArrayList.toString());
            Log.d(TAG, "storeDTOArrayList:size " + storeDTOArrayList.size());
        } catch (Exception e) {
            Log.d(TAG, "getArrayValues: " + e);
        }
    }

    private void initValues() {
        mContext = this;
        stockDAO = new StockDAO(FillValuesActivity.this);
        sessionManager = new SessionManager(mContext);
        binding.linearResult.setVisibility(View.GONE);
        /*  integerArrayList = new ArrayList<>();*/
        storeDTOArrayList = new ArrayList<>();
        strikeGapValue = 10;
        totalStrikeValue = 10;
        bundle = getIntent().getExtras();
        if (bundle != null) {
            strikeGapValue = Integer.parseInt(bundle.getString("strikeGap"));
            totalStrikeValue = Integer.parseInt(bundle.getString("totalStrike"));
            binding.tvName.setText(bundle.getString("name"));
            binding.tvStrike.setText("Strike : " + bundle.getString("strikeGap"));
            binding.tvTotalStrike.setText("Total Strike : " + bundle.getString("totalStrike"));
        }
        Log.d(TAG, "initValues: " + strikeGapValue + "\t\t" + totalStrikeValue);
    }

    private void setTopAdapter() {
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        valuesAdapter = new ValuesAdapter(mContext, _value, /*integerArrayList,*/ storeDTOArrayList);
        binding.recycleTopValues.setLayoutManager(horizontalLayoutManager1);
        binding.recycleTopValues.setAdapter(valuesAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        exitActivityAnimation();
    }


    public void getArrayForFillValues() {
        storeDTOArrayList.clear();
        stockDAO.open();
        storeDTOArrayList = stockDAO.getFillValue(bundle.getString("stock_id"));
        stockDAO.close();
        Log.d(TAG, "storeDTOArrayList: " + storeDTOArrayList.size() + "\t\t\t\t" + bundle.getString("stock_id"));
        setTopAdapter();

        if (storeDTOArrayList.size() > 0) {
            binding.etCmp.setText(storeDTOArrayList.get(0).getCmp());
            binding.linearResult.setVisibility(View.VISIBLE);
        }
    }


}
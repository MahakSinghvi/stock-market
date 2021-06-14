package com.ps.sm.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ps.sm.R;
import com.ps.sm.common.StockMarketBaseActivity;
import com.ps.sm.database.StockDAO;
import com.ps.sm.databinding.ActivityAddBinding;
import com.ps.sm.dto.ItemDTO;
import com.ps.sm.session.SessionManager;

public class AddActivity extends StockMarketBaseActivity {
    private String TAG = "AddActivity";
    SessionManager sessionManager;
    Context mContext;
    ActivityAddBinding binding;
    public static AddActivity addActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add);
        initValues();
        clickEvents();
    }

    private void initValues() {
        addActivity = this;
        mContext = this;
        sessionManager = new SessionManager(mContext);
    }

    private void Validation() {
        if (binding.etName.getText().toString().trim().length() < 1) {
            customToast("Enter name", true);
        } else if (binding.etCode.getText().toString().trim().length() < 1) {
            customToast("Enter code", true);
        } else if (binding.etLotSize.getText().toString().trim().length() < 1) {
            customToast("Enter Lot Size", true);
        } else if (binding.etstrikegap.getText().toString().trim().length() < 1) {
            Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
            customToast("Enter Strike gap", true);
        } else if (binding.ettotalstrike.getText().toString().trim().length() < 1) {
            customToast("Total Strike", true);
        } else {
            StockDAO stockDAO = new StockDAO(AddActivity.this);
            stockDAO.open();
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setName(binding.etName.getText().toString().trim());
            itemDTO.setCode(binding.etCode.getText().toString().trim());
            itemDTO.setLot_size(binding.etLotSize.getText().toString().trim());
            itemDTO.setStrike_gap(binding.etstrikegap.getText().toString().trim());
            itemDTO.setTotal_strike(binding.ettotalstrike.getText().toString().trim());
            stockDAO.addToLocalDB(itemDTO);
            stockDAO.close();
            if (HomeActivity.homeActivity != null) {
                HomeActivity.homeActivity.setDataArray();
            }
            finish();
            exitActivityAnimation();
        }


    }

    private void clickEvents() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                exitActivityAnimation();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation();
//                startActivity(new Intent(mContext, AddNextActivity.class));
//                enterActivityAnimation();

//                exitActivityAnimation();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        exitActivityAnimation();
    }
}
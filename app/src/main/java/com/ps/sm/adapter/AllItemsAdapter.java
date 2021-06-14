package com.ps.sm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ps.sm.R;
import com.ps.sm.activity.FillValuesActivity;
import com.ps.sm.activity.HomeActivity;
import com.ps.sm.common.StockMarketBaseActivity;
import com.ps.sm.databinding.AdapterAllItemsBinding;
import com.ps.sm.dto.ItemDTO;

import java.util.ArrayList;


public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.ViewHolder> {
    private String LOG_TAG = "AllItemsAdapter";
    Context mContext;
    ArrayList<ItemDTO> itemDTOs;
    private AdapterAllItemsBinding binding;
    private LayoutInflater inflater;
    StockMarketBaseActivity baseActivity;
    private ItemDTO itemDTO;

    public AllItemsAdapter(Context context, ArrayList<ItemDTO> itemDTOs) {

        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.itemDTOs = itemDTOs;
        baseActivity = (StockMarketBaseActivity) mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_all_items, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        itemDTO = itemDTOs.get(position);
        holder._binding.nameTV.setText(itemDTO.getName());
        holder._binding.codeTV.setText("Code : " + itemDTO.getCode());
        holder._binding.lotsizeTV.setText("LotSize : " + itemDTO.getLot_size());
        holder._binding.strikegapTV.setText("Strike Gap : " + itemDTO.getStrike_gap());
        holder._binding.tstrikeTV.setText("Total Strike : " + itemDTO.getTotal_strike());
        holder._binding.linearDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, FillValuesActivity.class)
                        .putExtra("strikeGap", itemDTOs.get(position).getStrike_gap())
                        .putExtra("totalStrike", itemDTOs.get(position).getTotal_strike())
                        .putExtra("name", itemDTOs.get(position).getName())
                        .putExtra("lotSize", itemDTOs.get(position).getLot_size())
                        .putExtra("stock_id", itemDTOs.get(position).getId())

                );
                baseActivity.enterActivityAnimation();
            }
        });

        holder._binding.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.homeActivity != null) {
                    HomeActivity.homeActivity.deleteItem(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemDTOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterAllItemsBinding _binding;

        public ViewHolder(@NonNull AdapterAllItemsBinding binding) {
            super(binding.getRoot());
            this._binding = binding;
        }
    }
}

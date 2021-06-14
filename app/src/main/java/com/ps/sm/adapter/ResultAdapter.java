package com.ps.sm.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ps.sm.R;
import com.ps.sm.activity.StrategyActivity;
import com.ps.sm.databinding.AdapterCategoryBinding;
import com.ps.sm.databinding.AdapterResultBinding;
import com.ps.sm.dto.CategoryDTO;
import com.ps.sm.dto.ResultDTO;

import java.util.ArrayList;
import java.util.Random;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private String TAG = "ResultAdapter";
    Context mContext;
    private AdapterResultBinding binding;
    private LayoutInflater inflater;
    public static ResultAdapter resultAdapter;
    private ArrayList<ResultDTO> resultDTOs;

    public ResultAdapter(Context context, ArrayList<ResultDTO> resultDTOs) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        resultAdapter = this;
        this.resultDTOs=resultDTOs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_result, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        resultDTOs.get(position);
        holder._binding.strategyTV.setText("ONE1");
        holder._binding.stike1TV.setText(resultDTOs.get(position).getStrike1());
        holder._binding.stike2TV.setText(resultDTOs.get(position).getStrike2());

        holder._binding.price1TV.setText(resultDTOs.get(position).getPrice1());
        holder._binding.price2TV.setText(resultDTOs.get(position).getPrice2());
        holder._binding.strategyTV.setText(resultDTOs.get(position).getStrategy());
        holder._binding.riskTV.setText(resultDTOs.get(position).getRisk());
        holder._binding.rewardTV.setText(resultDTOs.get(position).getReward());
        holder._binding.ubevTV.setText(resultDTOs.get(position).getUbev());
        holder._binding.lbevTV.setText(resultDTOs.get(position).getLbev());
        holder._binding.ratioTV.setText(resultDTOs.get(position).getRatio());

       /* Random r = new Random();
        int red=r.nextInt(255 - 0 + 1)+0;
        int green=r.nextInt(255 - 0 + 1)+0;
        int blue=r.nextInt(255 - 0 + 1)+0;

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.RECTANGLE);
        draw.setColor(Color.rgb(red,green,blue));
        holder._binding.linearBg.setBackground(draw);*/

    }

    @Override
    public int getItemCount() {
        return resultDTOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterResultBinding _binding;

        public ViewHolder(@NonNull AdapterResultBinding binding) {
            super(binding.getRoot());
            this._binding = binding;
        }
    }
}

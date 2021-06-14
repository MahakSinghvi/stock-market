package com.ps.sm.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ps.sm.R;
import com.ps.sm.databinding.AdapterCategoryBinding;
import com.ps.sm.databinding.AdapterStrategyBinding;
import com.ps.sm.dto.CategoryDTO;
import com.ps.sm.dto.StrategyDTO;

import java.util.ArrayList;


public class StrategyAdapter extends RecyclerView.Adapter<StrategyAdapter.ViewHolder> {
    private String TAG = "StrategyAdapter";
    Context mContext;
    private AdapterStrategyBinding binding;
    private LayoutInflater inflater;
    ArrayList<StrategyDTO> strategyDTOArrayList;
    public int selectedPosition = -1;
    public static StrategyAdapter strategyAdapter;

    public StrategyAdapter(Context context, ArrayList<StrategyDTO> _strategyDTOArrayList) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        strategyDTOArrayList = _strategyDTOArrayList;
        strategyAdapter = this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_strategy, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder._binding.tvStrategy.setText(strategyDTOArrayList.get(position).getName());
        if (strategyDTOArrayList.get(position).isSelected()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder._binding.linearCategory.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.teal_200)));
                holder._binding.tvStrategy.setTextColor(mContext.getResources().getColor(R.color.white));

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder._binding.tvStrategy.setTextColor(mContext.getResources().getColor(R.color.white));
                holder._binding.linearCategory.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.background_color1)));

            }
        }
        holder._binding.linearCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != -1) {
                    if (selectedPosition == position) {
                        if (strategyDTOArrayList.get(selectedPosition).isSelected()) {
                            strategyDTOArrayList.get(selectedPosition).setSelected(false);
                        } else {
                            strategyDTOArrayList.get(selectedPosition).setSelected(true);
                        }
                        selectedPosition = -1;
                    } else {
                        strategyDTOArrayList.get(selectedPosition).setSelected(false);
                        selectedPosition = position;
                        strategyDTOArrayList.get(selectedPosition).setSelected(true);
                    }
                } else {
                    selectedPosition = position;
                    strategyDTOArrayList.get(selectedPosition).setSelected(true);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return strategyDTOArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterStrategyBinding _binding;

        public ViewHolder(@NonNull AdapterStrategyBinding binding) {
            super(binding.getRoot());
            this._binding = binding;
        }
    }
}

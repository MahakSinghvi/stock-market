package com.ps.sm.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
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
import com.ps.sm.dto.CategoryDTO;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private String TAG = "CategoryAdapter";
    Context mContext;
    private AdapterCategoryBinding binding;
    private LayoutInflater inflater;
    ArrayList<CategoryDTO> categoryDTOArrayList;
    public int selectedPosition = -1;
    public static CategoryAdapter categoryAdapter;

    public CategoryAdapter(Context context, ArrayList<CategoryDTO> _categoryDTOArrayList) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        categoryDTOArrayList = _categoryDTOArrayList;
        categoryAdapter = this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_category, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder._binding.tvCategory.setText(categoryDTOArrayList.get(position).getName());
        if (categoryDTOArrayList.get(position).isSelected()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder._binding.linearCategory.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.teal_200)));
                holder._binding.tvCategory.setTextColor(mContext.getResources().getColor(R.color.white));

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder._binding.tvCategory.setTextColor(mContext.getResources().getColor(R.color.white));
                holder._binding.linearCategory.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.background_color1)));

            }
        }
        holder._binding.linearCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != -1) {
                    if (selectedPosition == position) {
                        if (categoryDTOArrayList.get(selectedPosition).isSelected()) {
                            categoryDTOArrayList.get(selectedPosition).setSelected(false);
                        } else {
                            categoryDTOArrayList.get(selectedPosition).setSelected(true);
                        }
                        selectedPosition = -1;
                    } else {
                        categoryDTOArrayList.get(selectedPosition).setSelected(false);
                        selectedPosition = position;
                        categoryDTOArrayList.get(selectedPosition).setSelected(true);
                    }
                } else {
                    selectedPosition = position;
                    categoryDTOArrayList.get(selectedPosition).setSelected(true);
                }
                notifyDataSetChanged();
                Log.d(TAG, "onClick:\t\tselectedPosition\t\t " + selectedPosition);

                if (StrategyActivity.strategyActivity != null) {
                    StrategyActivity.strategyActivity.getArrayForStrategy(position, ((position + 1) * 3));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryDTOArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterCategoryBinding _binding;

        public ViewHolder(@NonNull AdapterCategoryBinding binding) {
            super(binding.getRoot());
            this._binding = binding;
        }
    }
}

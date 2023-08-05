package com.example.clapphonefinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clapphonefinder.R;
import com.example.clapphonefinder.databinding.ItemLanguageBinding;
import com.example.clapphonefinder.model.LanguageModel;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LanguageModel> languageList;
    private int lastCheckedPosition = -1;
    ClickListener clickListener;

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public LanguageAdapter(Context context, ArrayList<LanguageModel> languageList) {
        this.context = context;
        this.languageList = languageList;
    }

    @NonNull
    @Override
    public LanguageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLanguageBinding binding = ItemLanguageBinding.inflate(LayoutInflater.from(context), parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.ViewHolder holder, int position) {
        LanguageModel languageModel = languageList.get(position);
        holder.binding.imvFlag.setImageResource(languageModel.getLanguageIcon());
        holder.binding.tvNameLanguage.setText(languageModel.getLanguage());
        holder.binding.layoutParent.setSelected(position == lastCheckedPosition);
        holder.binding.layoutParent.setBackgroundResource(holder.binding.layoutParent.isSelected() ? R.drawable.bg_gray_select : R.drawable.bg_gray);
        holder.binding.imvSelect.setImageResource(holder.binding.layoutParent.isSelected() ? R.drawable.ic_radio_selected : R.drawable.ic_radio_not_select);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int copyOfLastCheckedPosition = lastCheckedPosition;
                lastCheckedPosition = holder.getAdapterPosition();
                notifyItemChanged(copyOfLastCheckedPosition);
                notifyItemChanged(lastCheckedPosition);
                clickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLanguageBinding binding;
        public ViewHolder(@NonNull ItemLanguageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ClickListener {
        void onClick(int position);
    }
}

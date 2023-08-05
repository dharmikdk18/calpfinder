package com.example.clapphonefinder.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clapphonefinder.activity.ApplySoundActivity;
import com.example.clapphonefinder.databinding.SoundItemBinding;
import com.example.clapphonefinder.model.SoundModel;

import java.util.ArrayList;
import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {

    private Context context;
    private List<SoundModel> soundList;


    public SoundAdapter(Context context, List<SoundModel> soundList) {
        this.context = context;
        this.soundList = soundList;
    }

    @NonNull
    @Override
    public SoundAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SoundItemBinding binding = SoundItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundAdapter.ViewHolder holder, int position) {
        SoundModel soundModel = soundList.get(position);
        holder.binding.ivSoundImage.setImageResource(soundModel.getImage());
        holder.binding.tvSoundName.setText(soundModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ApplySoundActivity.class);
                intent.putExtra("sound", soundModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return soundList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SoundItemBinding binding;
        public ViewHolder(@NonNull SoundItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

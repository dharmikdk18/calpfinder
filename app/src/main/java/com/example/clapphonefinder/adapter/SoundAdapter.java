package com.example.clapphonefinder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sdkads.adsType.Interstitial_Google;
import com.example.clapphonefinder.activity.ApplySoundActivity;
import com.example.clapphonefinder.databinding.SoundItemBinding;
import com.example.clapphonefinder.model.SoundModel;

import java.util.ArrayList;
import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {

    private Activity activity;
    private List<SoundModel> soundList;


    public SoundAdapter(Activity activity, List<SoundModel> soundList) {
        this.activity = activity;
        this.soundList = soundList;
    }

    @NonNull
    @Override
    public SoundAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SoundItemBinding binding = SoundItemBinding.inflate(LayoutInflater.from(activity), parent, false);
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
                Interstitial_Google.showInterstitial(activity, new Interstitial_Google.OnclickInter() {
                    @Override
                    public void clicked() {
                        Intent intent = new Intent(activity, ApplySoundActivity.class);
                        intent.putExtra("sound", soundModel);
                        activity.startActivity(intent);
                    }
                });

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

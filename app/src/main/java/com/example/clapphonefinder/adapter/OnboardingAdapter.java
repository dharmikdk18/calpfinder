package com.example.clapphonefinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.clapphonefinder.databinding.OnboardingItemBinding;
import com.example.clapphonefinder.model.OnboardingModel;

import java.util.ArrayList;

public class OnboardingAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<OnboardingModel> onboardingList;


    public OnboardingAdapter(Context context, ArrayList<OnboardingModel> onboardingList) {
        this.context = context;
        this.onboardingList = onboardingList;
    }

    @Override
    public int getCount() {
        return onboardingList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        OnboardingItemBinding binding = OnboardingItemBinding.inflate(LayoutInflater.from(context), container, false);
        container.addView(binding.getRoot());

        OnboardingModel onboardingModel = onboardingList.get(position);
        binding.ivImage.setImageResource(onboardingModel.getImage());
        binding.tvTitle.setText(onboardingModel.getTitle());

        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

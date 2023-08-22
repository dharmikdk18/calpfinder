package com.example.clapphonefinder.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clapphonefinder.R;
import com.example.clapphonefinder.adapter.OnboardingAdapter;
import com.example.clapphonefinder.databinding.ActivityIntroBinding;
import com.example.clapphonefinder.model.OnboardingModel;
import com.example.clapphonefinder.utils.PreferenceManager;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {

    OnboardingAdapter adapter;
    ActivityIntroBinding binding;


    private int currentPosition = 0;
    private Activity activity;
    private ArrayList<OnboardingModel> onboardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        onboardList = new ArrayList<>();

        onboardList.add(new OnboardingModel(R.drawable.intro1, getString(R.string.onboarding1), getString(R.string.onboarding_description_1)));
        onboardList.add(new OnboardingModel(R.drawable.intro2, getString(R.string.onboarding2), getString(R.string.onboarding_description_2)));
        onboardList.add(new OnboardingModel(R.drawable.intro3, getString(R.string.onboarding3), getString(R.string.onboarding_description_3)));

        adapter = new OnboardingAdapter(this, onboardList);
        binding.viewpager.setAdapter(adapter);
        binding.dotsIndicator.setViewPager(binding.viewpager);

        binding.btnNext.setOnClickListener(view -> {
            currentPosition++;
            if (currentPosition < onboardList.size()) {
                binding.viewpager.setCurrentItem(currentPosition);
            } else {
                PreferenceManager.setIsFirstTime(false);
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (currentPosition != 0) {
            currentPosition--;
            binding.viewpager.setCurrentItem(currentPosition);
        } else {
            finish();
        }
    }
}
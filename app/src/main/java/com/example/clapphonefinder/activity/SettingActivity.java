package com.example.clapphonefinder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.app.sdkads.adsType.Interstitial_Google;
import com.example.clapphonefinder.databinding.ActivitySettingBinding;
import com.example.clapphonefinder.utils.PreferenceManager;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[] {
                        Color.parseColor("#7295B6"), //disabled
                        Color.parseColor("#7295B6") //enabled
                }
        );
        binding.rbtDefault.setButtonTintList(colorStateList);
        binding.rbtSosMode.setButtonTintList(colorStateList);
        binding.rbtDiscoMode.setButtonTintList(colorStateList);

        boolean flash = PreferenceManager.getFlash();
        binding.switchFlash.setChecked(flash);

        boolean vibration = PreferenceManager.getVibration();
        binding.switchVibrate.setChecked(vibration);

        String flashMode = PreferenceManager.getFlashMode();
        if (flashMode.equalsIgnoreCase("default")) {
            binding.rbtDefault.setChecked(true);
            binding.rbtDiscoMode.setChecked(false);
            binding.rbtSosMode.setChecked(false);
        } else if (flashMode.equalsIgnoreCase("disco")) {
            binding.rbtDefault.setChecked(false);
            binding.rbtDiscoMode.setChecked(true);
            binding.rbtSosMode.setChecked(false);
        } else if (flashMode.equalsIgnoreCase("sos")) {
            binding.rbtDefault.setChecked(false);
            binding.rbtDiscoMode.setChecked(false);
            binding.rbtSosMode.setChecked(true);
        }

        binding.rbtDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.setFlashMode("default");
                binding.rbtDefault.setChecked(true);
                binding.rbtDiscoMode.setChecked(false);
                binding.rbtSosMode.setChecked(false);
            }
        });

        binding.rbtDiscoMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.setFlashMode("disco");
                binding.rbtDefault.setChecked(false);
                binding.rbtDiscoMode.setChecked(true);
                binding.rbtSosMode.setChecked(false);
            }
        });

        binding.rbtSosMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.setFlashMode("sos");
                binding.rbtDefault.setChecked(false);
                binding.rbtDiscoMode.setChecked(false);
                binding.rbtSosMode.setChecked(true);
            }
        });

        binding.switchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PreferenceManager.setVibration(b);
            }
        });

        binding.switchFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PreferenceManager.setFlash(b);
            }
        });

        binding.layoutLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Interstitial_Google.showInterstitial(activity, new Interstitial_Google.OnclickInter() {
                    @Override
                    public void clicked() {
                        Intent intent = new Intent(activity, LanguageActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });

        binding.layoutRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName();
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        binding.layoutPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.google.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Interstitial_Google.showBackInterstitial(SettingActivity.this, new Interstitial_Google.OnclickInter() {
            @Override
            public void clicked() {
                finish();
            }
        });
    }
}
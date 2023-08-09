package com.example.clapphonefinder.fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.clapphonefinder.R;
import com.example.clapphonefinder.activity.LanguageActivity;
import com.example.clapphonefinder.databinding.FragmentSettingBinding;
import com.example.clapphonefinder.utils.PreferenceManager;

public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(getLayoutInflater());

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
                Intent intent = new Intent(getActivity(), LanguageActivity.class);
                startActivity(intent);
            }
        });

        binding.layoutRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getActivity().getPackageName();
                try {
                    // Open the Play Store with the app's page
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // If Play Store app is not available, open in browser
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

        return binding.getRoot();
    }


}
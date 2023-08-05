package com.example.clapphonefinder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.clapphonefinder.R;
import com.example.clapphonefinder.adapter.MainPagerAdapter;
import com.example.clapphonefinder.databinding.ActivityMainBinding;
import com.example.clapphonefinder.fragment.FindFragment;
import com.example.clapphonefinder.fragment.SettingFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        mainPagerAdapter.addFragment(new FindFragment(), "Find");
        mainPagerAdapter.addFragment(new SettingFragment(), "Setting");

        binding.viewpager.setAdapter(mainPagerAdapter);
        binding.bottomTabLayout.setupWithViewPager(binding.viewpager);

    }



}
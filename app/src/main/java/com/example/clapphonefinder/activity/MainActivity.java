package com.example.clapphonefinder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import com.example.clapphonefinder.R;
import com.example.clapphonefinder.databinding.ActivityMainBinding;
import com.example.clapphonefinder.fragment.FindFragment;
import com.example.clapphonefinder.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadFragment(new FindFragment(), "Find");

        binding.layoutFind.setOnClickListener(view -> {
            loadFragment(new FindFragment(), "Find");
            binding.layoutFind.setBackgroundResource(R.drawable.bg_gradient_orange);
            binding.layoutSetting.setBackgroundResource(R.drawable.bg_white);
            binding.imvFind.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
            binding.tvFind.setTextColor(ColorStateList.valueOf(getColor(R.color.white)));
            binding.tvSetting.setTextColor(ColorStateList.valueOf(getColor(R.color.color_gray)));
            binding.imvSetting.setImageTintList(ColorStateList.valueOf(getColor(R.color.color_gray)));
        });

        binding.layoutSetting.setOnClickListener(view -> {
            loadFragment(new SettingFragment(), "Setting");
            binding.layoutSetting.setBackgroundResource(R.drawable.bg_gradient_orange);
            binding.layoutFind.setBackgroundResource(R.drawable.bg_white);
            binding.imvFind.setImageTintList(ColorStateList.valueOf(getColor(R.color.color_gray)));
            binding.imvSetting.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
            binding.tvFind.setTextColor(ColorStateList.valueOf(getColor(R.color.color_gray)));
            binding.tvSetting.setTextColor(ColorStateList.valueOf(getColor(R.color.white)));
        });

    }

    public void loadFragment(Fragment fragment, String title){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment, title)
                .commit();
    }

}
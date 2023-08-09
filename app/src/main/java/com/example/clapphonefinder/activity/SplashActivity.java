package com.example.clapphonefinder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.sdkads.Ads;
import com.app.sdkads.App;
import com.example.clapphonefinder.R;
import com.example.clapphonefinder.utils.LocaleHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        String selectedLanguage = LocaleHelper.getLanguage(this);
        LocaleHelper.setLocale(this, selectedLanguage);
        setContentView(R.layout.activity_splash);

        Ads.Init(SplashActivity.this, new Ads.InitListner() {
            @Override
            public void success() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

            @Override
            public void failed(String error) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }
}
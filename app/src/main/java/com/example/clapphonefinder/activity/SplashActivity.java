package com.example.clapphonefinder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.sdkads.Ads;
import com.app.sdkads.App;
import com.example.clapphonefinder.R;
import com.example.clapphonefinder.utils.LocaleHelper;
import com.example.clapphonefinder.utils.PermissionUtils;

public class SplashActivity extends AppCompatActivity {
    private PermissionUtils permissionUtils;
    private Context context;
    private boolean isNotificationPermission = false, isCameraPermission = false, isMicrophonePermission = false, isMusicAndAudioPermission = false, isPhotosAndVideosPermission = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        String selectedLanguage = LocaleHelper.getLanguage(this);
        LocaleHelper.setLocale(this, selectedLanguage);
        setContentView(R.layout.activity_splash);

        context = this;
        permissionUtils = new PermissionUtils(context);

        isNotificationPermission = permissionUtils.isNotificationPermission();
        isCameraPermission = permissionUtils.isCameraPermission();
        isMicrophonePermission = permissionUtils.isMicrophonePermission();
        isMusicAndAudioPermission = permissionUtils.isMusicAndAudioPermission();
        isPhotosAndVideosPermission = permissionUtils.isPhotosAndVideosPermission();

        Ads.Init(SplashActivity.this, new Ads.InitListner() {
            @Override
            public void success() {
                nextScreen();

            }

            @Override
            public void failed(String error) {
                nextScreen();
            }
        });

    }

    private void nextScreen() {
        if (isNotificationPermission && isCameraPermission && isMicrophonePermission && isMusicAndAudioPermission && isPhotosAndVideosPermission){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), PermissionActivity.class));
            finish();
        }
    }
}
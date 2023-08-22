package com.example.clapphonefinder.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;

import com.app.sdkads.Ads;
import com.app.sdkads.App;
import com.example.clapphonefinder.R;
import com.example.clapphonefinder.databinding.ActivitySplashBinding;
import com.example.clapphonefinder.databinding.PermissionDialogBinding;
import com.example.clapphonefinder.utils.LocaleHelper;
import com.example.clapphonefinder.utils.PermissionUtils;

public class SplashActivity extends AppCompatActivity {
    private PermissionUtils permissionUtils;
    private Context context;
    private boolean isNotificationPermission = false, isCameraPermission = false, isMicrophonePermission = false, isMusicAndAudioPermission = false, isPhotosAndVideosPermission = false, isOverlayPermission = false;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    ActivitySplashBinding binding;
    PermissionDialogBinding permissionDialogBinding;

    private String[] permissions = {
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        String selectedLanguage = LocaleHelper.getLanguage(this);
        LocaleHelper.setLocale(this, selectedLanguage);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = this;
        permissionUtils = new PermissionUtils(context);

        isNotificationPermission = permissionUtils.isNotificationPermission();
        isCameraPermission = permissionUtils.isCameraPermission();
        isMicrophonePermission = permissionUtils.isMicrophonePermission();
        isMusicAndAudioPermission = permissionUtils.isMusicAndAudioPermission();
        isPhotosAndVideosPermission = permissionUtils.isPhotosAndVideosPermission();
        isOverlayPermission = permissionUtils.isOverlayPermission();

        Ads.Init(SplashActivity.this, new Ads.InitListner() {
            @Override
            public void success() {
                showPermissionDialog();
            }

            @Override
            public void failed(String error) {
                showPermissionDialog();
            }
        });

    }

    private boolean checkPermission() {
        boolean status = false;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                status = false;
                break;
            } else {
                status = true;
            }
        }

        return status;
    }

    private void showPermissionDialog() {
        if (!checkPermission()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);

            permissionDialogBinding = PermissionDialogBinding.inflate(getLayoutInflater());
            builder.setView(permissionDialogBinding.getRoot());

            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            permissionDialogBinding.btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    permissionDialogBinding.btnApply.setEnabled(false);
                    dialog.dismiss();
                    requestPermission();
                }
            });
        } else {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
                startActivityForResult(intent, 200);
            } else {
                nextScreen();
            }
        }
    }

    private void requestPermission() {
        requestPermissions(permissions, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            nextScreen();
        }
    }

    private void nextScreen() {
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;

                    // Update the progress bar on the main thread
                    handler.post(new Runnable() {
                        public void run() {
                            binding.progressBar.setProgress(progressStatus);
                            binding.tvPer.setText(progressStatus + "%");
                        }
                    });

                    try {
                        // Add a delay to control the speed of the progress
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (progressStatus == 100) {
                        if (isNotificationPermission && isCameraPermission && isMicrophonePermission && isMusicAndAudioPermission && isPhotosAndVideosPermission && isOverlayPermission) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), PermissionActivity.class));
                            finish();
                        }

                    }
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                showPermissionDialog();
                permissionDialogBinding.btnApply.setEnabled(true);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }
}
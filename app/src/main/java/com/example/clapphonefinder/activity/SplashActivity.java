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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;

import com.app.sdkads.Ads;
import com.app.sdkads.App;
import com.example.clapphonefinder.R;
import com.example.clapphonefinder.databinding.ActivitySplashBinding;
import com.example.clapphonefinder.databinding.PermissionDialog2Binding;
import com.example.clapphonefinder.databinding.PermissionDialogBinding;
import com.example.clapphonefinder.utils.LocaleHelper;
import com.example.clapphonefinder.utils.PermissionUtils;
import com.example.clapphonefinder.utils.PreferenceManager;

public class SplashActivity extends AppCompatActivity {
    private PermissionUtils permissionUtils;
    private Context context;private int progressStatus = 0;
    private Handler handler = new Handler();
    ActivitySplashBinding binding;
    PermissionDialogBinding permissionDialogBinding;
    PermissionDialog2Binding permissionDialog2Binding;
    AlertDialog dialog;

    private String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private String[] permissions33 = {
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES
    };

    private String[] ps;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ps = permissions33;
        } else {
            ps = permissions;
        }

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
        for (String permission : ps) {
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
        for (String permission : ps){
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(permission)) {
                    showPermissionSecondDialog(permission);
                    return;
                }
            }
        }
        if (!checkPermission()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);

            permissionDialogBinding = PermissionDialogBinding.inflate(getLayoutInflater());
            builder.setView(permissionDialogBinding.getRoot());


            dialog = builder.create();
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
                showPermissionSecondDialog("");
            } else {
                nextScreen();
            }
        }
    }

    private void showPermissionSecondDialog(String permission){
        int icon = R.drawable.ic_notification_p;
        String title = "";
        String message = "";
        if (permission.equalsIgnoreCase(Manifest.permission.POST_NOTIFICATIONS)){
            title = "Notifications";
            message = "Enable notifications so you don’t miss another augmented reality experiences near you";
            icon = R.drawable.ic_notification_p;
        } else if (permission.equalsIgnoreCase(Manifest.permission.CAMERA)){
            title = "Camera";
            message = "Please provide us access to your camera, which is required for Augmented Reality";
            icon = R.drawable.ic_camera_p;
        } else if (permission.equalsIgnoreCase(Manifest.permission.RECORD_AUDIO)){
            title = "Microphone";
            message = "Grant microphone permission for enhanced Augmented Reality.";
            icon = R.drawable.ic_mircophone;
        } else if (permission.equalsIgnoreCase(Manifest.permission.READ_MEDIA_AUDIO)){
            title = "Music and Audio";
            message = "Enable microphone access to enhance music and audio in AR.";
            icon = R.drawable.ic_music_p;
        } else if (permission.equalsIgnoreCase(Manifest.permission.READ_MEDIA_IMAGES)){
            title = "Photo and Video";
            message = "Grant microphone access for enhanced photo and video AR.";
            icon = R.drawable.ic_photo_p ;
        } else if (!Settings.canDrawOverlays(context)){
            title = "Display over other apps";
            message = "Authorize display over apps for crucial alerts.";
            icon = R.drawable.ic_device_p;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        permissionDialog2Binding = PermissionDialog2Binding.inflate(getLayoutInflater());
        builder.setView(permissionDialog2Binding.getRoot());
        permissionDialog2Binding.tvTitle.setText(title);
        permissionDialog2Binding.tvMessage.setText(message);
        permissionDialog2Binding.ivIcon.setImageResource(icon);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        permissionDialog2Binding.btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        permissionDialog2Binding.btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (Settings.canDrawOverlays(context)) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 300);
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
                    startActivityForResult(intent, 300);
                }
            }
        });
    }

    private void requestPermission() {
        requestPermissions(ps, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            nextScreen();
        } else if (requestCode == 300){
            showPermissionDialog();
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
                        if (PreferenceManager.isFirstTime()){
                            startActivity(new Intent(getApplicationContext(), IntroActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
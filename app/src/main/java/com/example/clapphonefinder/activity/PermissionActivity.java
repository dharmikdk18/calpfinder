package com.example.clapphonefinder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.clapphonefinder.R;
import com.example.clapphonefinder.databinding.ActivityPermissionBinding;
import com.example.clapphonefinder.utils.PermissionUtils;

import java.util.List;

public class PermissionActivity extends AppCompatActivity {

    private ActivityPermissionBinding binding;
    private Context context;
    private static final int NOTIFICATION_CODE = 100, CAMERA_CODE = 200, RECORD_AUDIO_CODE = 300, READ_MEDIA_AUDIO_CODE = 400, READ_MEDIA_PHOTO_CODE = 500, REQUEST_CODE_OVERLAY_PERMISSION = 600;
    private boolean isNotificationPermission = false, isCameraPermission = false, isMicrophonePermission = false, isMusicAndAudioPermission = false, isPhotosAndVideosPermission = false, isOverlayPermission = false;
    private PermissionUtils permissionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = this;
        permissionUtils = new PermissionUtils(context);

        binding.notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkAndGrantPermission(binding.notification, b, Manifest.permission.POST_NOTIFICATIONS, NOTIFICATION_CODE);
            }
        });

        binding.camera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkAndGrantPermission(binding.camera, b, Manifest.permission.CAMERA, CAMERA_CODE);
            }
        });

        binding.microphone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkAndGrantPermission(binding.microphone, b, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_CODE);
            }
        });

        binding.musicAndAudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkAndGrantPermission(binding.musicAndAudio, b, Manifest.permission.READ_MEDIA_AUDIO, READ_MEDIA_AUDIO_CODE);
            }
        });

        binding.photosAndVideos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkAndGrantPermission(binding.musicAndAudio, b, Manifest.permission.READ_MEDIA_IMAGES, READ_MEDIA_PHOTO_CODE);
            }
        });

        binding.overlayPermission.setOnCheckedChangeListener((compoundButton, b) -> {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION);
        });

        binding.btnNext.setOnClickListener(view -> {
            binding.btnNext.setEnabled(false);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void checkAndGrantPermission(Switch switchButton, boolean b, String permission, int permissionCode) {
        if (b) {
            if (shouldShowRequestPermissionRationale(permission)) {
                showRequirePermissionDialog();
                switchButton.setChecked(false);
                return;
            }
            requestPermissions(new String[]{permission}, permissionCode);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isNotificationPermission = permissionUtils.isNotificationPermission();
        isCameraPermission = permissionUtils.isCameraPermission();
        isMicrophonePermission = permissionUtils.isMicrophonePermission();
        isMusicAndAudioPermission = permissionUtils.isMusicAndAudioPermission();
        isPhotosAndVideosPermission = permissionUtils.isPhotosAndVideosPermission();
        isOverlayPermission = permissionUtils.isOverlayPermission();

        binding.notification.setChecked(isNotificationPermission);
        binding.camera.setChecked(isCameraPermission);
        binding.microphone.setChecked(isMicrophonePermission);
        binding.musicAndAudio.setChecked(isMusicAndAudioPermission);
        binding.photosAndVideos.setChecked(isPhotosAndVideosPermission);
        binding.overlayPermission.setChecked(isOverlayPermission);

        if (isNotificationPermission && isCameraPermission && isMicrophonePermission && isMusicAndAudioPermission && isPhotosAndVideosPermission && isOverlayPermission){
            binding.btnNext.setEnabled(true);
        } else {
            binding.btnNext.setEnabled(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case NOTIFICATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    binding.notification.setChecked(true);
                } else {
                    binding.notification.setChecked(false);
                }
                break;
            case CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    binding.camera.setChecked(true);
                } else {
                    binding.camera.setChecked(false);
                }
                break;
            case RECORD_AUDIO_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    binding.microphone.setChecked(true);
                } else {
                    binding.microphone.setChecked(false);
                }
                break;
            case READ_MEDIA_AUDIO_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    binding.musicAndAudio.setChecked(true);
                } else {
                    binding.musicAndAudio.setChecked(false);
                }
            case READ_MEDIA_PHOTO_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    binding.photosAndVideos.setChecked(true);
                } else {
                    binding.photosAndVideos.setChecked(false);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    private void showRequirePermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
        builder.setTitle("Permission Required");
        builder.setMessage("Please grant the permission in the app settings.");
        builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }



}
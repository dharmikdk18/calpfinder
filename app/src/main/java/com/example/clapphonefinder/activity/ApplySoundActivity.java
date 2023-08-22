package com.example.clapphonefinder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.app.sdkads.adsType.Interstitial_Google;
import com.example.clapphonefinder.R;
import com.example.clapphonefinder.databinding.ActivityApplySoundBinding;
import com.example.clapphonefinder.model.SoundModel;
import com.example.clapphonefinder.service.MyForegroundService;
import com.example.clapphonefinder.service.PlaySoundService;
import com.example.clapphonefinder.utils.PreferenceManager;
import com.example.clapphonefinder.utils.Utils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class ApplySoundActivity extends AppCompatActivity {

    ActivityApplySoundBinding applySoundBinding;
    SoundModel soundModel;
    AudioManager audioManager;
    int sound;
    boolean enableSound;
    String time;
    private static final int REQUEST_CODE_OVERLAY_PERMISSION = 1000;

    private BroadcastReceiver volumeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                applySoundBinding.progressBar.setProgress(currentVolume);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        applySoundBinding = ActivityApplySoundBinding.inflate(getLayoutInflater());
        setContentView(applySoundBinding.getRoot());

        soundModel = getIntent().getParcelableExtra("sound");


        sound = soundModel.getSound();
        enableSound = PreferenceManager.getEnableSound();
        time = PreferenceManager.getTime();

        applySoundBinding.soundSwitch.setChecked(enableSound);

        if (time.equalsIgnoreCase("15s")) {
            applySoundBinding.chip15s.setChecked(true);
        } else if (time.equalsIgnoreCase("30s")) {
            applySoundBinding.chip30s.setChecked(true);
        } else if (time.equalsIgnoreCase("1m")) {
            applySoundBinding.chip1m.setChecked(true);
        } else if (time.equalsIgnoreCase("2m")) {
            applySoundBinding.chip2m.setChecked(true);
        } else if (time.equalsIgnoreCase("loop")) {
            applySoundBinding.chiploop.setChecked(true);
        }

        applySoundBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        applySoundBinding.toolbar.setTitle(soundModel.getName());
        initControls();
        applySoundBinding.ivPlay.setOnClickListener(view -> {
            Intent serviceIntent = new Intent(ApplySoundActivity.this, PlaySoundService.class);
            if (!Utils.isServiceRunning(ApplySoundActivity.this, PlaySoundService.class)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    serviceIntent.putExtra("action", "play");
                    serviceIntent.putExtra("sound", soundModel.getSound());
                    startForegroundService(serviceIntent);
                } else {
                    startService(serviceIntent);
                }
            }  else {
                stopService(serviceIntent);
            }
        });

        applySoundBinding.chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = findViewById(checkedId);
                if (chip != null) {
                    if (chip.isChecked()) {
                        time = chip.getText().toString().trim();
                        chip.setChecked(true);
                    } else {
                        chip.setChecked(false);
                    }
                }
            }
        });

        applySoundBinding.soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                enableSound = b;
            }
        });

        applySoundBinding.btnApply.setOnClickListener(view -> {
            PreferenceManager.setSound(sound);
            PreferenceManager.setEnableSound(enableSound);
            PreferenceManager.setTime(time);
            Toast.makeText(ApplySoundActivity.this, soundModel.getName() + " apply successfully", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(volumeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(volumeReceiver);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            applySoundBinding.progressBar.setProgress(currentVolume);
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            applySoundBinding.progressBar.setProgress(currentVolume);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initControls() {
        try {
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            applySoundBinding.progressBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            applySoundBinding.progressBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

            applySoundBinding.progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isOverlayPermissionAvailable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Interstitial_Google.showBackInterstitial(this, new Interstitial_Google.OnclickInter() {
            @Override
            public void clicked() {
                finish();
            }
        });
    }
}
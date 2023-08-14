package com.example.clapphonefinder.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.clapphonefinder.R;
import com.example.clapphonefinder.activity.MainActivity;
import com.example.clapphonefinder.utils.PreferenceManager;
import com.example.clapphonefinder.utils.Recorder;

public class PlaySoundService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "MyForegroundServiceChannel";
    private static final String TAG = "PlaySoundService";
    private MediaPlayer mediaPlayer;
    Vibrator vibrator;
    private static final long[] VIBRATE_PATTERN = {1000, 500};
    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashOn = false;
    private Handler handler = new Handler();
    private long delayMillis = 100;

    private long shortDelayMillis = 200; // Duration of short flash (adjust as needed)
    private long longDelayMillis = 600;  // Duration of long flash (adjust as needed)
    private long pauseDelayMillis = 1000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null) {
            String action = intent.getStringExtra("action");

            int sound = intent.getIntExtra("sound", 0);
            if (action != null && sound != 0) {
                if (action.equals("play")) {
                    mediaPlayer = MediaPlayer.create(PlaySoundService.this, R.raw.clap_sound);
                    playStart();
                } else {

                }
            }
        }
        startForegroundService();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (vibrator != null) {
            vibrator.cancel();
        }

        if (cameraManager != null && cameraId != null) {
            try {
                cameraManager.setTorchMode(cameraId, false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        if (PreferenceManager.getFlashMode().equalsIgnoreCase("disco")) {
            handler.removeCallbacks(discoRunnable);
            turnOffFlash();
        } if (PreferenceManager.getFlashMode().equalsIgnoreCase("sos")) {
            handler.removeCallbacks(sosRunnable);
            turnOffFlash();
        }

        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    private void stopForegroundService() {
        stopForeground(true);
        stopSelf();
    }

    private void startSound(boolean play) {
        if (mediaPlayer != null) {
            if (play) {
                mediaPlayer.start();
            } else {
                stopForegroundService();
            }
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (play) {
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
            }
        });
    }

    private Runnable discoRunnable = new Runnable() {
        @Override
        public void run() {
            if (isFlashOn) {
                turnOffFlash();
            } else {
                turnOnFlash();
            }
            handler.postDelayed(this, delayMillis);
        }
    };

    private Runnable sosRunnable = new Runnable() {
        @Override
        public void run() {
            if (isFlashOn) {
                turnOffFlash();
                handler.postDelayed(this, shortDelayMillis);
            } else {
                turnOnFlash();
                handler.postDelayed(this, longDelayMillis);
            }
        }
    };

    private void turnOnFlash() {
        try {
            cameraManager.setTorchMode(cameraId, true);
            isFlashOn = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOffFlash() {
        try {
            cameraManager.setTorchMode(cameraId, false);
            isFlashOn = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void playStart() {
        startSound(true);

        if (PreferenceManager.getVibration()) {
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(VIBRATE_PATTERN, 0);
            }
        }

        if (PreferenceManager.getFlash()) {
            if (PreferenceManager.getFlashMode().equalsIgnoreCase("default")) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                    try {
                        cameraId = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId, true);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            } else if (PreferenceManager.getFlashMode().equalsIgnoreCase("disco")) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                    try {
                        cameraId = cameraManager.getCameraIdList()[0];
                        handler.post(discoRunnable);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            } else if (PreferenceManager.getFlashMode().equalsIgnoreCase("sos")) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                    try {
                        cameraId = cameraManager.getCameraIdList()[0];
                        handler.post(sosRunnable);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        String time = PreferenceManager.getTime();
        int duration = 0;
        if (time.equalsIgnoreCase("15s")) {
            duration = 15 * 1000;
        } else if (time.equalsIgnoreCase("30s")) {
            duration = 30 * 1000;
        } else if (time.equalsIgnoreCase("1m")) {
            duration = 60 * 1000;
        } else if (time.equalsIgnoreCase("2m")) {
            duration = 120 * 1000;
        } else if (time.equalsIgnoreCase("loop")) {
            duration = -1;
        }

        if (duration != -1) {
            new CountDownTimer(duration, 1000) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    startSound(false);
                }
            }.start();
        }

    }

    private void startForegroundService() {
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams paramsIn = new WindowManager.LayoutParams(-1, -1, Build.VERSION.SDK_INT >= 26 ? 2038 : 2010, 19399552 | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, -3);
        paramsIn.screenBrightness = 100.0f;

        try {
            Settings.System.putInt(getContentResolver(), "screen_brightness_mode", 0);
            ContentResolver cResolver = getContentResolver();
            Settings.System.putInt(cResolver, "screen_brightness", 255);
        } catch (Exception e) {
            e.toString();
        }

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            View inComingCallView = layoutInflater.inflate(R.layout.notification_small, (ViewGroup) null);
            Button btnGotIt = inComingCallView.findViewById(R.id.btn_got_it);
            btnGotIt.setOnClickListener(view -> {
                windowManager.removeView(inComingCallView);
                stopForegroundService();
            });
            windowManager.addView(inComingCallView, paramsIn);
        }
    }

    private Notification createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("My Foreground Service")
                .setContentText("Running...")
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        return builder.build();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    CHANNEL_ID,
                    "My Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}

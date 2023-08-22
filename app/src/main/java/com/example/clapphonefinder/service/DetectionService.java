package com.example.clapphonefinder.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.clapphonefinder.R;
import com.example.clapphonefinder.utils.Recorder;

public class DetectionService extends Service implements DetectorThread.OnClapListener {
    private static final String TAG = "DetectClap";
    private DetectorThread detectorThread;
    LocalBroadcastManager localBroadcastManager;
    private Recorder recorder;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "MyForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        this.localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            try {
                if (intent.getExtras() != null) {
                    if (intent.getExtras().containsKey("action")) {
                        if (intent.getStringExtra("action").equals("start")) {
                            startClapDetection();
                        }
                        if (intent.getStringExtra("action").equals("stop")) {
                            stopClapDetection();
                            stopSelf();
                        }
                    }
                    return super.onStartCommand(intent, flags, startId);
                }
            } catch (Exception e) {
            }
        }
        startClapDetection();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
        startForeground(NOTIFICATION_ID, createNotification());
        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "My Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private Notification createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("My Foreground Service")
                .setContentText("Running in the background")
                .setSmallIcon(R.drawable.ic_notification_icon);

        return builder.build();
    }

    private void startClapDetection() {
        try {
            stopClapDetection();
        } catch (Exception e) {
        }
        this.recorder = new Recorder(this);
        this.recorder.startRecording();
        this.detectorThread = new DetectorThread(this.recorder);
        this.detectorThread.setOnClapListener(this);
        this.detectorThread.start();
    }


    private void stopClapDetection() {
        if (this.detectorThread != null) {
            this.detectorThread.stopDetection();
            this.detectorThread.setOnClapListener(null);
            this.detectorThread = null;
        }
        if (this.recorder != null) {
            this.recorder.stopRecording();
            this.recorder = null;
        }
    }

    @Override
    public void onDestroy() {
        stopClapDetection();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onClap() {
        stopClapDetection();
        this.localBroadcastManager.sendBroadcast(new Intent("Clap"));
    }
}

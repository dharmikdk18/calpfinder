package com.example.clapphonefinder.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.clapphonefinder.R;
import com.example.clapphonefinder.activity.ApplySoundActivity;
import com.example.clapphonefinder.activity.MainActivity;
import com.example.clapphonefinder.utils.PreferenceManager;
import com.example.clapphonefinder.utils.Recorder;

public class MyForegroundService extends Service implements DetectorThread.OnClapListener {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "MyForegroundServiceChannel";
    private DetectorThread detectorThread;
    private Recorder recorder;
    private static final String TAG = "DetectClap";
    private ScreenLockReceiver screenLockReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForegroundService();
        if (intent != null && intent.getExtras() != null) {
            String action = intent.getStringExtra("action");
            if (action != null) {
                if (action.equals("start")) {
                    IntentFilter intentFilter = new IntentFilter("clapDetected");
                    registerReceiver(clapReceiver, intentFilter);
                    startClapDetection();
                    Intent intent1 = new Intent("clap");
                    intent1.putExtra("start", true);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
                }
            }
        }
        return START_STICKY;
    }

    private void startForegroundService() {
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(clapReceiver);
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    CHANNEL_ID,
                    "My Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

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

        if (screenLockReceiver != null) {
            unregisterReceiver(screenLockReceiver);
            screenLockReceiver = null;
        }
        Intent intent1 = new Intent("clap");
        intent1.putExtra("start", false);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);

        stopForeground(true);
        stopSelf();
    }

    private BroadcastReceiver clapReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onClap: ");
            Intent mainActivityIntent = new Intent(context, MainActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);
        }
    };
    private MediaPlayer mediaPlayer;
    @Override
    public void onClap() {
        stopClapDetection();
        Intent serviceIntent = new Intent(this, PlaySoundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            serviceIntent.putExtra("action", "play");
            serviceIntent.putExtra("sound", PreferenceManager.getSound());
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    private class ScreenLockReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                showPopupDialog();
                Log.d(TAG, "onReceive: Screen OFF");
            } else {
                Log.d(TAG, "onReceive: Screen On");
            }
        }
    }

    private void showPopupDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification with a custom channel to show the popup dialog
            NotificationChannel channel = new NotificationChannel(
                    "popup_channel_id",
                    "Popup Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "popup_channel_id")
                    .setContentTitle("Popup Title")
                    .setContentText("Popup Message")
                    .setSmallIcon(R.drawable.ic_notification_icon)
                    .setAutoCancel(true);

            // Create a pending intent to handle the notification click
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            // Show the notification
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            notificationManagerCompat.notify(1, builder.build());
        }
    }
}

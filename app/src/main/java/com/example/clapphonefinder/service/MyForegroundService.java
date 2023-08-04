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
import com.example.clapphonefinder.activity.MainActivity;
import com.example.clapphonefinder.utils.Recorder;

public class MyForegroundService extends Service implements DetectorThread.OnClapListener {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "MyForegroundServiceChannel";
    LocalBroadcastManager localBroadcastManager;
    private DetectorThread detectorThread;
    private Recorder recorder;
    private static final String TAG = "DetectClap";
    private ScreenLockReceiver screenLockReceiver;


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
        // Check the action passed through the intent and handle accordingly
        if (intent != null && intent.getExtras() != null) {
            String action = intent.getStringExtra("action");
            if (action != null) {
                if (action.equals("start")) {
                    startForegroundService();
                    IntentFilter intentFilter = new IntentFilter("clapDetected");
                    registerReceiver(clapReceiver, intentFilter);
                    startClapDetection();
                } else if (action.equals("stop")) {
                    stopClapDetection();
                    stopForegroundService();
                }
            }
        }
        return START_STICKY;
    }

    private void startForegroundService() {
        // Build the notification for the foreground service
        Notification notification = createNotification();

        // Start the service as a foreground service
        startForeground(NOTIFICATION_ID, notification);
    }

    private void stopForegroundService() {
        // Stop the foreground service and remove the notification
        stopForeground(true);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        Intent intent1 = new Intent("clap");
        intent1.putExtra("start", false);
        localBroadcastManager.sendBroadcast(intent1);
        unregisterReceiver(clapReceiver);
//        unregisterReceiver(screenLockReceiver);
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
        // Build and return the notification to be shown as part of the foreground service
        // Set up the notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("My Foreground Service")
                .setContentText("Running...")
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Customize the notification as needed
        // ...

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

        // No need to call startForeground() here, it should be called in onStartCommand()

        // Initialize and register the ScreenLockReceiver
//        if (screenLockReceiver == null) {
//            screenLockReceiver = new ScreenLockReceiver();
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
//            registerReceiver(screenLockReceiver, intentFilter);
//        }
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

        // Unregister the ScreenLockReceiver if it was registered
        if (screenLockReceiver != null) {
            unregisterReceiver(screenLockReceiver);
            screenLockReceiver = null;
        }

        // Stop the foreground service
        stopForeground(true);
        stopSelf();
    }

    private BroadcastReceiver clapReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onClap: ");
            // Start the MainActivity when the clap is detected
            Intent mainActivityIntent = new Intent(context, MainActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);
        }
    };
    private MediaPlayer mediaPlayer;
    @Override
    public void onClap() {

//        Toast.makeText(this, "Clap", Toast.LENGTH_SHORT).show();
//        showPopupDialog();
//        Intent intent = new Intent("clapDetected");
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        mediaPlayer = MediaPlayer.create(this, R.raw.clap_sound);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        Log.d(TAG, "onClap: ");
        stopClapDetection();
    }

    private class ScreenLockReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Show the popup dialog when the device is locked
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
        } else {
            // If the device is running on a lower API, you can't show a popup dialog directly.
            // Instead, you can show a notification with a pending intent to launch an activity when clicked.
        }
    }
}

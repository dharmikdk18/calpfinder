package com.example.clapphonefinder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.app.sdkads.adsType.Interstitial_Google;
import com.example.clapphonefinder.R;
import com.example.clapphonefinder.adapter.SoundAdapter;
import com.example.clapphonefinder.databinding.ActivityMainBinding;
import com.example.clapphonefinder.model.SoundModel;
import com.example.clapphonefinder.service.MyForegroundService;
import com.example.clapphonefinder.utils.ShadowProperty;
import com.example.clapphonefinder.utils.ShadowViewDrawable;
import com.example.clapphonefinder.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private Activity activity;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean status = intent.getBooleanExtra("start", false);
            binding.switchTapToActive.setChecked(status);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;

        Utils.applyShadow(this, binding.llTapToActive);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_setting) {
                    Intent intent = new Intent(activity, SettingActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        binding.llTapToActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(activity, MyForegroundService.class);
                if (!Utils.isServiceRunning(activity, MyForegroundService.class)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        serviceIntent.putExtra("action", "start");
                        activity.startForegroundService(serviceIntent);
                    } else {
                        activity.startService(serviceIntent);
                    }
                } else {
                    activity.stopService(serviceIntent);
                }
            }
        });

        List<SoundModel> soundList = new ArrayList<>();
        soundList.add(new SoundModel(getString(R.string.whistle), R.raw.whistle, R.drawable.ic_whistle));
        soundList.add(new SoundModel(getString(R.string.hello), R.raw.hello, R.drawable.ic_hello));
        soundList.add(new SoundModel(getString(R.string.car_honk), R.raw.car_honk, R.drawable.ic_car_honk));
        soundList.add(new SoundModel(getString(R.string.door_bell), R.raw.door_bell, R.drawable.ic_door_bell));
        soundList.add(new SoundModel(getString(R.string.party_horn), R.raw.party_horn, R.drawable.ic_party_horn));
        soundList.add(new SoundModel(getString(R.string.police_whistle), R.raw.police_whistle, R.drawable.ic_police_whistle));
        soundList.add(new SoundModel(getString(R.string.cavalry), R.raw.cavalry, R.drawable.ic_cavalry));

        SoundAdapter adapter = new SoundAdapter(activity, soundList);
        binding.rcvSound.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("clap");
        LocalBroadcastManager.getInstance(activity).registerReceiver(mMessageReceiver, filter);
        binding.switchTapToActive.setChecked(Utils.isServiceRunning(activity, MyForegroundService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(mMessageReceiver);
    }


}
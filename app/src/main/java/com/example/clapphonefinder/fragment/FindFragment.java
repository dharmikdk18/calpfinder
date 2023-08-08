package com.example.clapphonefinder.fragment;

import android.Manifest;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;

import androidx.core.content.*;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.*;

import com.example.clapphonefinder.*;
import com.example.clapphonefinder.adapter.SoundAdapter;
import com.example.clapphonefinder.databinding.*;
import com.example.clapphonefinder.model.*;
import com.example.clapphonefinder.service.*;
import com.example.clapphonefinder.utils.Utils;

import java.util.*;

public class FindFragment extends Fragment {

    private static final String TAG = "FindFragment";
    private FragmentFindBinding binding;
    private Activity activity;
    private BroadcastReceiver localBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Handle the broadcast here
//            String message = intent.getStringExtra("message");
            // Do something with the message
            boolean start = intent.getBooleanExtra("start", false);
            if (!start){
                binding.imvSound.setBackgroundResource(R.drawable.ic_off_service);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("clap");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(localBroadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(localBroadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFindBinding.inflate(getLayoutInflater());

        activity = getActivity();

        if (Utils.isServiceRunning(activity, MyForegroundService.class)) {
            binding.imvSound.setBackgroundResource(R.drawable.ic_on_service);
        } else {
            binding.imvSound.setBackgroundResource(R.drawable.ic_off_service);
        }

        binding.imvSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    Intent serviceIntent = new Intent(activity, MyForegroundService.class);
                    if (!Utils.isServiceRunning(activity, MyForegroundService.class)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            serviceIntent.putExtra("action", "start");
                            activity.startForegroundService(serviceIntent);

                        } else {
                            activity.startService(serviceIntent);
                        }
                        binding.imvSound.setBackgroundResource(R.drawable.ic_on_service);
                    } else {
                        binding.imvSound.setBackgroundResource(R.drawable.ic_off_service);
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
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

        return binding.getRoot();
    }
}
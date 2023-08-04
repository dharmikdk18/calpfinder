package com.example.clapphonefinder.fragment;

import android.Manifest;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;

import androidx.core.content.*;
import androidx.fragment.app.Fragment;

import android.view.*;

import com.example.clapphonefinder.*;
import com.example.clapphonefinder.adapter.SoundAdapter;
import com.example.clapphonefinder.databinding.*;
import com.example.clapphonefinder.model.*;
import com.example.clapphonefinder.service.*;

import java.util.*;

public class FindFragment extends Fragment {

    private static final String TAG = "FindFragment";
    private FragmentFindBinding binding;
    private Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFindBinding.inflate(getLayoutInflater());

        activity = getActivity();

        if (isServiceRunning(activity, MyForegroundService.class)) {
            binding.layoutOnOff.setBackgroundResource(R.drawable.ic_on_service);
        } else {
            binding.layoutOnOff.setBackgroundResource(R.drawable.ic_off_service);
        }

        binding.layoutOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    Intent serviceIntent = new Intent(activity, MyForegroundService.class);

                    if (!isServiceRunning(activity, MyForegroundService.class)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            serviceIntent.putExtra("action", "start");
                            activity.startForegroundService(serviceIntent);
                        } else {
                            activity.startService(serviceIntent);
                        }
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
                }
            }
        });

        List<SoundModel> soundList = new ArrayList<>();
        soundList.add(new SoundModel("Whistle", R.raw.whistle, R.drawable.ic_whistle));
        soundList.add(new SoundModel("Hello", R.raw.hello, R.drawable.ic_hello));
        soundList.add(new SoundModel("Car honk", R.raw.car_honk, R.drawable.ic_car_honk));
        soundList.add(new SoundModel("Door bell", R.raw.door_bell, R.drawable.ic_door_bell));
        soundList.add(new SoundModel("Party horn", R.raw.party_horn, R.drawable.ic_party_horn));
        soundList.add(new SoundModel("Police whistle", R.raw.police_whistle, R.drawable.ic_police_whistle));
        soundList.add(new SoundModel("Cavalry", R.raw.cavalry, R.drawable.ic_cavalry));

        SoundAdapter adapter = new SoundAdapter(activity, soundList);
        binding.rcvSound.setAdapter(adapter);

        return binding.getRoot();
    }

    public boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
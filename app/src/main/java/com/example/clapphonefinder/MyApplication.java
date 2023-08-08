package com.example.clapphonefinder;

import android.app.Application;

import com.example.clapphonefinder.utils.LocaleHelper;
import com.example.clapphonefinder.utils.PreferenceManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(this);
    }
}

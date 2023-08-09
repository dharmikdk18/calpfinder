package com.example.clapphonefinder;

import android.app.Application;

import com.app.sdkads.App;
import com.example.clapphonefinder.utils.LocaleHelper;
import com.example.clapphonefinder.utils.PreferenceManager;

public class MyApplication extends Application {

    App app;

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(this);
        app = new App(this);
        app.onCreate();
    }
}

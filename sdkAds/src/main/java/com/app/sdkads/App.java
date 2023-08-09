package com.app.sdkads;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;

public class App  {

    private static Context context;
    private static AppOpenManager appOpenManager;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    Application application;

    public App(Application application){
        this.application = application;
    }


    public void onCreate() {

        MobileAds.initialize(application);
        AudienceNetworkAds.initialize(application);
        AdSettings.addTestDevice("351a82a2-2643-4e57-8def-f4ac3d2c6f96");
        setAppOpenManager();

        sharedPreferences = application.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setAppOpenManager() {
        appOpenManager = new AppOpenManager(application);
    }


    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void setString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }
}

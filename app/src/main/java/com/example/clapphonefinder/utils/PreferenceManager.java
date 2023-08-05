package com.example.clapphonefinder.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.clapphonefinder.R;

public class PreferenceManager {

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    public static void init(Application application){
        preferences = application.getSharedPreferences("sound", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static void setFirstTime(){
        editor.putBoolean("first_time",false);
        editor.apply();
    }

    public static void setSound(int value){
        editor.putInt("sound",value);
        editor.apply();
    }

    public static void setEnableSound(boolean value){
        editor.putBoolean("enable_sound",value);
        editor.apply();
    }

    public static void setTime(String value){
        editor.putString("time",value);
        editor.apply();
    }

    public static void setVibration(boolean value){
        editor.putBoolean("vibrate",value);
        editor.apply();
    }

    public static void setFlash(boolean value){
        editor.putBoolean("flash",value);
        editor.apply();
    }

    public static void setFlashMode(String value){
        editor.putString("flash_mode",value);
        editor.apply();
    }

    public static boolean getFirstTime(){
        return preferences.getBoolean("first_time", true);
    }

    public static int getSound(){
        return preferences.getInt("sound", R.raw.whistle);
    }

    public static boolean getEnableSound(){
        return preferences.getBoolean("enable_sound", true);
    }

    public static String getTime(){
        return preferences.getString("time", "15s");
    }

    public static boolean getVibration(){
        return preferences.getBoolean("vibrate", false);
    }

    public static boolean getFlash(){
        return preferences.getBoolean("flash", false);
    }

    public static String getFlashMode(){
        return preferences.getString("flash_mode", "default");
    }

    public static String getLanguageCode(){
        return preferences.getString("language_code", "en");
    }

}

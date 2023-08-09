package com.app.sdkads.utils;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import androidx.browser.customtabs.CustomTabsIntent;

import com.app.sdkads.App;
import com.app.sdkads.R;

public class utils {

    public static void setNullKey(){
        App.setString(Const.carrier_id, "");
        App.setString(Const.vpn_url, "");
        App.setString(Const.country, "");
        App.setString(Const.ad_status, "");
        App.setString(Const.app_status, "");
        App.setString(Const.rating, "");
        App.setString(Const.custom_ad, "");
        App.setString(Const.vpn_status, "");
        App.setString(Const.extra_ad_status, "");
        App.setString(Const.g_banner, "");
        App.setString(Const.g_native_banner, "");
        App.setString(Const.g_app_open, "");
        App.setString(Const.g_interstitial, "");
        App.setString(Const.g_rewarded, "");
        App.setString(Const.g_rewarded_interstitial, "");
        App.setString(Const.fb_banner, "");
        App.setString(Const.fb_native_ad, "");
        App.setString(Const.fb_native_banner, "");
        App.setString(Const.fb_interstitial, "");
        App.setString(Const.fb_rewarded_video, "");
        App.setString(Const.adx_banner, "");
        App.setString(Const.adx_native_banner, "");
        App.setString(Const.adx_app_open, "");
        App.setString(Const.adx_interstitial, "");
        App.setString(Const.adx_rewarded, "");
        App.setString(Const.adx_rewarded_interstitial, "");
        App.setString(Const.qu_link, "");

    }

    public static void qurekaClick(Activity activity, String Qureka_link) {

        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            Bundle bundle = new Bundle();
            bundle.putBinder(CustomTabsIntent.EXTRA_SESSION, (IBinder) null);
            intent.putExtras(bundle);
            intent.putExtra(CustomTabsIntent.EXTRA_TOOLBAR_COLOR, R.color.colorPrimary);
            intent.putExtra(CustomTabsIntent.EXTRA_ENABLE_INSTANT_APPS, true);
            intent.setPackage("com.android.chrome");
            intent.setData(Uri.parse(Qureka_link));
            activity.startActivity(intent, bundle);
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent("android.intent.action.VIEW");
            Bundle bundle = new Bundle();
            bundle.putBinder(CustomTabsIntent.EXTRA_SESSION, (IBinder) null);
            intent.putExtras(bundle);
            intent.putExtra(CustomTabsIntent.EXTRA_TOOLBAR_COLOR, R.color.colorPrimary);
            intent.putExtra(CustomTabsIntent.EXTRA_ENABLE_INSTANT_APPS, true);
            intent.setData(Uri.parse(Qureka_link));
            activity.startActivity(intent, bundle);
        }


    }
}

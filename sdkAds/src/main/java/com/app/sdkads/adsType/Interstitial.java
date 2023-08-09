package com.app.sdkads.adsType;


import android.app.Activity;
import android.util.Log;

import com.app.sdkads.App;
import com.app.sdkads.utils.Const;


public class Interstitial {

    public interface Onclick {
        void clicked();
    }

    public static void Interstitial_load(Activity activity) {
//        if (App.getString(Const.Ads_priority).equals("2")) {
            Interstitial_Google.Interstitial_load(activity);
//        }
    }

    public static void Interstitial_show(Activity activity, Onclick onclick) {
        Log.e("visible_mdg",""+ Const.ad_status);

        if (App.getString(Const.ad_status).equalsIgnoreCase("0")) {
            onclick.clicked();
        } else if (App.getString(Const.ad_status).equalsIgnoreCase("1")) {
            Interstitial_Google.showInterstitial(activity, new Interstitial_Google.OnclickInter() {
                @Override
                public void clicked() {
                    onclick.clicked();
                }
            });
        } else if (App.getString(Const.ad_status).equalsIgnoreCase("2")) {
            Interstitial_Google.showInterstitial(activity, new Interstitial_Google.OnclickInter() {
                @Override
                public void clicked() {
                    onclick.clicked();
                }
            });
        } else {
            onclick.clicked();
        }
    }

    public static void Interstitial_show_back(Activity activity, Onclick onclick) {
        Log.e("visible_mdg",""+ Const.ad_status);

        if (App.getString(Const.ad_status).equalsIgnoreCase("0") || App.getString(Const.back_press_ads).equalsIgnoreCase("0")) {
            onclick.clicked();
        } else if (App.getString(Const.ad_status).equalsIgnoreCase("1")) {
            Interstitial_Google.showInterstitial(activity, new Interstitial_Google.OnclickInter() {
                @Override
                public void clicked() {
                    onclick.clicked();
                }
            });
        } else if (App.getString(Const.ad_status).equalsIgnoreCase("2")) {
            Interstitial_Google.showInterstitial(activity, new Interstitial_Google.OnclickInter() {
                @Override
                public void clicked() {
                    onclick.clicked();
                }
            });
        } else {
            onclick.clicked();
        }
    }


}

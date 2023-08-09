package com.app.sdkads.adsType;


import android.app.Activity;
import android.widget.RelativeLayout;

import com.app.sdkads.App;
import com.app.sdkads.utils.Const;
import com.facebook.ads.NativeAdLayout;


public class NativeBanner {

    public static void show(Activity activity, int id, int fbId) {
        if (App.getString(Const.ad_status).equalsIgnoreCase("0")) {
//                    new NativeBanner_Google(activity, id);
        } else if (App.getString(Const.ad_status).equalsIgnoreCase("1")) {
            new NativeBanner_Google(activity, id, fbId);
        }
    }

    public static void show(Activity activity, RelativeLayout id, NativeAdLayout fbId) {
        if (App.getString(Const.ad_status).equalsIgnoreCase("0")) {
//                    new NativeBanner_Google(activity, id);
        } else if (App.getString(Const.ad_status).equalsIgnoreCase("1")) {
            new NativeBanner_Google(activity, id, fbId);
        }
    }
}

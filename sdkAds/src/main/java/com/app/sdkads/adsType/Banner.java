package com.app.sdkads.adsType;



import android.app.Activity;
import android.widget.RelativeLayout;

import com.app.sdkads.App;
import com.app.sdkads.utils.Const;


public class Banner {

    public static void show(Activity activity, int id) {

        if (App.getString(Const.ad_status).equalsIgnoreCase("0")) {

        } else if (App.getString(Const.ad_status).equalsIgnoreCase("1")) {
            new Banner_Google(activity, id);
        }
    }

    public static void show(Activity activity, RelativeLayout id) {

        if (App.getString(Const.ad_status).equalsIgnoreCase("0")) {

        } else if (App.getString(Const.ad_status).equalsIgnoreCase("1")) {
            new Banner_Google(activity, id);
        }
    }
}

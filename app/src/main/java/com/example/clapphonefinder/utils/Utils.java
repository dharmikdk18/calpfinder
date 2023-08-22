package com.example.clapphonefinder.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.core.view.ViewCompat;

public class Utils {
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
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

    public static void applyShadow(Context context, View view){
        ShadowProperty sp = new ShadowProperty()
                .setShadowColor(0x1A0081F8)
                .setShadowRadius(dip2px(context, 10.0f))
                .setShadowDy(dip2px(context, 0.4f))
                .setShadowRadius(dip2px(context, 4))
                .setShadowSide(ShadowProperty.ALL);

        ShadowViewDrawable sd = new ShadowViewDrawable(context, sp, Color.WHITE, dip2px(context, 10.0f), dip2px(context, 10.0f));
        ViewCompat.setBackground(view, sd);
        ViewCompat.setLayerType(view, View.LAYER_TYPE_SOFTWARE, null);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

package com.app.sdkads;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.app.sdkads.utils.Const;
import com.app.sdkads.utils.utils;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

public class AppOpenManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private long loadTime = 0;
    private static Activity currentActivity;
    private static final String LOG_TAG = "AppOpenManager";
    private AppOpenAd appOpenAd = null;
    private static boolean isShowingAd = false;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private final Application myApplication;
    public static Dialog dialog;
    public static boolean isDisable = false;

    public static void disableAppOpenAd(){
        isDisable = true;
    }

    public static void enableAppOpenAd(){
        isDisable = false;
    }

    public AppOpenManager(Application myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        if (!App.getString(Const.ad_status).equalsIgnoreCase("0")) {
            if (!isDisable){
                showAdIfAvailable();
            }
        }

    }

    public void showAdIfAvailable() {
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            AppOpenManager.this.appOpenAd = null;
                            isShowingAd = false;
                            fetchAd();
                            dialog.dismiss();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);

        } else {
            fetchAd();
            Log.d(LOG_TAG, "Can not show ad.");
            if (!Const.isFirstTimeAppOpen){
                Const.isFirstTimeAppOpen = true;
                qureka_openAD();
            } else {
                Const.isFirstTimeAppOpen = false;
            }



        }
    }

    public void fetchAd() {
        if (isAdAvailable()) {
            return;
        }

        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        AppOpenManager.this.appOpenAd = ad;
                        AppOpenManager.this.loadTime = (new Date()).getTime();

                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                    }

                };
        AdRequest request = getAdRequest();
        AppOpenAd.load(myApplication,
                App.getString(Const.g_app_open), request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }


    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);

    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

//    public static InitListner minitListner;
//    public interface InitListner {
//        public void success();
//
//        public void failed(String error);
//    }
    public static void qureka_openAD() {
        Dialog qurekaDialog = new Dialog(currentActivity, android.R.style.Theme_DeviceDefault);
        qurekaDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        qurekaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        qurekaDialog.setContentView(R.layout.qureka_app_open_ad);
        qurekaDialog.setCancelable(false);
        qurekaDialog.show();

        RelativeLayout btnSkip = qurekaDialog.findViewById(R.id.btnSkip);
        LinearLayout btnQureka = qurekaDialog.findViewById(R.id.btnQureka);
        LinearLayout btnQureka2 = qurekaDialog.findViewById(R.id.btnQureka2);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qurekaDialog.dismiss();
//                minitListner.success();
            }
        });

        btnQureka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qurekaDialog.dismiss();
                utils.qurekaClick(currentActivity, App.getString(Const.Qureka_link));
            }
        });


        btnQureka2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qurekaDialog.dismiss();
                utils.qurekaClick(currentActivity, App.getString(Const.Qureka_link));
            }
        });
    }
}

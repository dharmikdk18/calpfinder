package com.app.sdkads.adsType;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.sdkads.App;
import com.app.sdkads.utils.Const;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;

public class Banner_Google {

    Activity activity;
    ViewGroup view;

    public Banner_Google(Activity activity, int id) {
        this.activity = activity;
        view = activity.findViewById(id);
        googleBanner();
    }

    public Banner_Google(Activity activity, RelativeLayout id) {
        this.activity = activity;
        view = id;
        googleBanner();
    }

    public void googleBanner() {
        com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(activity);
        adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        adView.setAdUnitId(App.getString(Const.g_banner));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                fbBanner();
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdClosed() {
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.addView(adView, params);

    }

    public void fbBanner() {
        AdView adView = new AdView(activity, App.getString(Const.fb_banner), AdSize.BANNER_HEIGHT_50);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.addView(adView, params);
        adView.loadAd();

        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };


    }

}

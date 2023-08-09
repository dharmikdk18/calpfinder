package com.app.sdkads.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.sdkads.App;
import com.app.sdkads.R;
import com.app.sdkads.utils.Const;
import com.app.sdkads.utils.utils;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.NativeAdLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;

import java.util.Random;

public class RisingBannerView extends RelativeLayout {

    private View currentView;

    public RisingBannerView(@NonNull Context context) {
        super(context);
        init();
    }

    public RisingBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RisingBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Load the initial XML layout
        if (!App.getString(Const.ad_status).equalsIgnoreCase("0")) {
            setCurrentView(R.layout.loading_banner_ads);
            googleBanner((Activity) currentView.getContext());
        }
    }

    private void setCurrentView(@LayoutRes int layoutRes) {
        if (currentView != null) {
            removeView(currentView);
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        currentView = inflater.inflate(layoutRes, this, false);
        addView(currentView);
    }


    public void googleBanner(Activity context) {
        com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(context);
        adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        adView.setAdUnitId(App.getString(Const.g_banner));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d("TAG", "onAdLoaded: ");
                if (currentView != null) {
                    removeView(currentView);
                }
                currentView = adView;
                addView(currentView);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.d("TAG", "onAdFailedToLoad: ");
                fbBanner(context);
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
    }

    public void fbBanner(Activity context) {
        AdView adView = new AdView(context, App.getString(Const.fb_banner), AdSize.BANNER_HEIGHT_50);
//        adView.loadAd();
        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                qurekaBanner(context);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (currentView != null) {
                    removeView(currentView);
                }
                currentView = adView;
                addView(currentView);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // Position and layout child views within MyCustomView
        if (currentView != null) {
            currentView.layout(0, 0, getWidth(), getHeight());
        }
    }

    public void qurekaBanner(Activity activity) {
//        if (isNetworkAvailable()) {

        RelativeLayout adView = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.qureka_banner, null, false);
        ImageView imageview_qureka_banner = adView.findViewById(R.id.imageview_qureka_banner);

        int i = getRandomNumber(1, 5);
        switch (i) {
            case 1:
                imageview_qureka_banner.setImageResource(R.drawable.qureka_banner_1);
                break;
            case 2:
                imageview_qureka_banner.setImageResource(R.drawable.qureka_banner_2);
                break;
            case 3:
                imageview_qureka_banner.setImageResource(R.drawable.qureka_banner_3);
                break;
            case 4:
                imageview_qureka_banner.setImageResource(R.drawable.qureka_banner_4);
                break;
            case 5:
                imageview_qureka_banner.setImageResource(R.drawable.qureka_banner_5);
                break;
        }


        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.qurekaClick(activity, App.getString(Const.Qureka_link));
            }
        });

        if (currentView != null) {
            removeView(currentView);
        }
        currentView = adView;
        addView(currentView);
//        }
    }


    public static int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }
}

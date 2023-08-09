package com.app.sdkads.adsType;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.app.sdkads.App;
import com.app.sdkads.R;
import com.app.sdkads.utils.Const;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Interstitial_Google {

    private static InterstitialAd mInterstitialAd = null;
    public static Activity activity;
    public static String TAG = "Interstitial_Google";
    public static com.facebook.ads.InterstitialAd interstitialAd = null;
    public static Dialog dialog;


    public static void Interstitial_load(Activity activity) {


        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity, App.getString(Const.g_interstitial),
                adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                        FbInterstitial_load(activity);
//                        Interstitial_load(activity);
                    }
                });
    }

    public static void FbInterstitial_load(Activity activity) {

        interstitialAd = new com.facebook.ads.InterstitialAd(activity, App.getString(Const.fb_interstitial));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
                if (onclickInter != null) {
                    interstitialAd = null;
                    FbInterstitial_load(activity);
                    onclickInter.clicked();
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
//                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    public static OnclickInter onclickInter;
    public static int interAdCount = 0;

    public static void showInterstitial(Activity activity, OnclickInter onclickInter) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                interAdCount++;
                if (App.getString(Const.extra_ad_status).equalsIgnoreCase("0")) {
                    if (interAdCount % 3 == 0) {
                        if (App.getString(Const.ad_status).equalsIgnoreCase("1")) {
                            dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            dialog.setContentView(R.layout.loading_ads);
                            dialog.setCancelable(false);
                            dialog.show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Interstitial_Google.onclickInter = onclickInter;
                                    if (mInterstitialAd != null) {
                                        mInterstitialAd.show(activity);
                                    } else if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                                        interstitialAd.show();
                                    } else {
//                    onclickInter.clicked();
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                        Interstitial_load(activity);
                                        Interstitial_Qureka.showInterstitial(activity, new Interstitial_Qureka.OnclickInter() {
                                            @Override
                                            public void clicked() {
                                                onclickInter.clicked();
                                            }
                                        });
                                    }

                                    if (mInterstitialAd != null) {
                                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                            @Override
                                            public void onAdDismissedFullScreenContent() {
                                                mInterstitialAd = null;
                                                Interstitial_load(activity);
                                                onclickInter.clicked();
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                Log.e(TAG, "onAdFailedToShowFullScreenContent --> " + adError);
                                            }

                                            @Override
                                            public void onAdShowedFullScreenContent() {
                                                Log.e(TAG, "onAdFailedToShowFullScreenContent -->");
                                            }
                                        });
                                    }
                                }
                            }, 1000);
                        } else {
                            onclickInter.clicked();
                        }
                    } else {
                        onclickInter.clicked();
                    }
                } else {
                    dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.setContentView(R.layout.loading_ads);
                    dialog.setCancelable(false);
                    dialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Interstitial_Google.onclickInter = onclickInter;
                            if (mInterstitialAd != null) {
                                mInterstitialAd.show(activity);
                            } else if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                                interstitialAd.show();
                            } else {
//                    onclickInter.clicked();
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                Interstitial_load(activity);
                                Interstitial_Qureka.showInterstitial(activity, new Interstitial_Qureka.OnclickInter() {
                                    @Override
                                    public void clicked() {
                                        onclickInter.clicked();
                                    }
                                });
                            }

                            if (mInterstitialAd != null) {
                                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        mInterstitialAd = null;
                                        Interstitial_load(activity);
                                        onclickInter.clicked();
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        Log.e(TAG, "onAdFailedToShowFullScreenContent --> " + adError);
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        Log.e(TAG, "onAdFailedToShowFullScreenContent -->");
                                    }
                                });
                            }
                        }
                    }, 1000);
                }
            }
        });

    }

    public static void showBackInterstitial(Activity activity, OnclickInter onclickInter) {
        if (App.getString(Const.back_press_ads).equalsIgnoreCase("0")){
            onclickInter.clicked();
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (App.getString(Const.ad_status).equalsIgnoreCase("1")) {
                    dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.setContentView(R.layout.loading_ads);
                    dialog.setCancelable(false);
                    dialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Interstitial_Google.onclickInter = onclickInter;
                            if (mInterstitialAd != null) {
                                mInterstitialAd.show(activity);
                            } else if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                                interstitialAd.show();
                            } else {
//                    onclickInter.clicked();
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                Interstitial_load(activity);
                                Interstitial_Qureka.showInterstitial(activity, new Interstitial_Qureka.OnclickInter() {
                                    @Override
                                    public void clicked() {
                                        onclickInter.clicked();
                                    }
                                });
                            }

                            if (mInterstitialAd != null) {
                                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        mInterstitialAd = null;
                                        Interstitial_load(activity);
                                        onclickInter.clicked();
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        Log.e(TAG, "onAdFailedToShowFullScreenContent --> " + adError);
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        Log.e(TAG, "onAdFailedToShowFullScreenContent -->");
                                    }
                                });
                            }
                        }
                    }, 1000);
                } else {
                    onclickInter.clicked();
                }
            }
        });

    }


    public interface OnclickInter {
        void clicked();
    }


}

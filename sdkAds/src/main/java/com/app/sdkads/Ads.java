package com.app.sdkads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.sdkads.Api.AllApi;
import com.app.sdkads.Api.Global;
import com.app.sdkads.Model.MyAds;
import com.app.sdkads.adsType.Interstitial;
import com.app.sdkads.utils.Const;
import com.app.sdkads.utils.utils;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ads {

    public static String TAG = "Ads";
    public static Activity mactivity;
    public static InitListner minitListner;
    public static Dialog dialog;

    private static AppOpenAd.AppOpenAdLoadCallback loadCallback;

    public interface InitListner {
        public void success();

        public void failed(String error);
    }


    public static void Init(Activity activity, InitListner initListner) {
        mactivity = activity;
        minitListner = initListner;
        doMyAds_URL_Data();
        dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.loading_ads);
    }

    private static String INSTALL_PREF = "install_pref_infius";

    private static void doMyAds_URL_Data() {

        String download = "0";



        if (App.getString(Const.isFirstTime).equalsIgnoreCase("")){
            download = "1";
            App.setString(Const.isFirstTime,"0");
        } else {
            download = "0";
        }

        AllApi restApi = Global.initRetrofit();

        Call<MyAds> loginCall = restApi.doMyAds_URL_Data(mactivity.getPackageName(),download);

        loginCall.enqueue(new Callback<MyAds>() {
            @Override
            public void onResponse(Call<MyAds> call, Response<MyAds> response) {
                Log.d(TAG, "OnResponse--> doMyAds_URL_Data : " + response.body());

                if (response.isSuccessful()){
                    MyAds.AdsData adsDataa = response.body().getData();
                    App.setString(Const.carrier_id, adsDataa.getCarrier_id());
                    App.setString(Const.vpn_url, adsDataa.getVpn_url());
                    App.setString(Const.country, adsDataa.getCountry());
                    App.setString(Const.ad_status, adsDataa.getAd_status());
                    App.setString(Const.app_status, adsDataa.getApp_status());
                    App.setString(Const.rating, adsDataa.getRating());
                    App.setString(Const.custom_ad, adsDataa.getCustom_ad());
                    App.setString(Const.vpn_status, adsDataa.getVpn_status());
                    App.setString(Const.extra_ad_status, adsDataa.getExtra_ad_status());
                    App.setString(Const.g_banner, adsDataa.getG_banner());
                    App.setString(Const.g_native_banner, adsDataa.getG_native_banner());
                    App.setString(Const.g_app_open, adsDataa.getG_app_open());
                    App.setString(Const.g_interstitial, adsDataa.getG_interstitial());
                    App.setString(Const.g_rewarded, adsDataa.getG_rewarded());
                    App.setString(Const.g_rewarded_interstitial, adsDataa.getG_rewarded_interstitial());
                    App.setString(Const.fb_banner, adsDataa.getFb_banner());
                    App.setString(Const.fb_native_ad, adsDataa.getFb_native_ad());
                    App.setString(Const.fb_native_banner, adsDataa.getFb_native_banner());
                    App.setString(Const.fb_interstitial, adsDataa.getFb_interstitial());
                    App.setString(Const.fb_rewarded_video, adsDataa.getFb_rewarded_video());
                    App.setString(Const.adx_banner, adsDataa.getAdx_banner());
                    App.setString(Const.adx_native_banner, adsDataa.getAdx_native_banner());
                    App.setString(Const.adx_app_open, adsDataa.getAdx_app_open());
                    App.setString(Const.adx_interstitial, adsDataa.getAdx_interstitial());
                    App.setString(Const.adx_rewarded, adsDataa.getAdx_rewarded());
                    App.setString(Const.adx_rewarded_interstitial, adsDataa.getAdx_rewarded_interstitial());
                    App.setString(Const.qu_link, adsDataa.getQu_link());
                    App.setString(Const.back_press_ads, adsDataa.getBack_press_ads());
                    App.setString(Const.Qureka_link, adsDataa.getQu_link());

                    if (App.getString(Const.ad_status).equalsIgnoreCase("0")) {
                        minitListner.success();
                    } else {
                        Google_AppOpenAds();
                    }

                    Interstitial.Interstitial_load(mactivity);

                } else{
                    try {
                        minitListner.failed(response.errorBody().string());
                    } catch (IOException e) {
                        minitListner.failed(e.toString());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyAds> call, Throwable t) {
                utils.setNullKey();
                minitListner.failed(t.getLocalizedMessage());
                Log.e(TAG, "Failure--> doMyAds_URL_Data : " + t);
            }
        });
    }

    public static void qureka_openAD() {
        Dialog qurekaDialog = new Dialog(mactivity, android.R.style.Theme_DeviceDefault);
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
                minitListner.success();
            }
        });

        btnQureka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qurekaDialog.dismiss();
                utils.qurekaClick(mactivity, App.getString(Const.Qureka_link));
            }
        });


        btnQureka2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qurekaDialog.dismiss();
                utils.qurekaClick(mactivity, App.getString(Const.Qureka_link));
            }
        });
    }

    private static void Google_AppOpenAds() {
        MobileAds.initialize(mactivity);
        dialog.show();
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            public void onAdLoaded(AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);
                dialog.dismiss();
                appOpenAd.show(mactivity);
                appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);

                        dialog.dismiss();
                        qureka_openAD();
                    }

                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        minitListner.success();
                    }
                });
            }

            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                dialog.dismiss();
                qureka_openAD();
            }

        };
        AppOpenAd.load((Context) mactivity, App.getString(Const.g_app_open), new AdRequest.Builder().build(), 1, loadCallback);
    }



}

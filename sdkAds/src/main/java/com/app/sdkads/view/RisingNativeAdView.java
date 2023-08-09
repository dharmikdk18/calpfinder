package com.app.sdkads.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.app.sdkads.App;
import com.app.sdkads.R;
import com.app.sdkads.utils.Const;
import com.app.sdkads.utils.utils;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RisingNativeAdView extends NativeAdLayout {
    private View currentView;

    public RisingNativeAdView(@NonNull Context context) {
        super(context);
        init();
    }

    public RisingNativeAdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RisingNativeAdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Load the initial XML layout

        if (!App.getString(Const.ad_status).equalsIgnoreCase("0")) {
            setCurrentView(R.layout.loading_native_ads);
            nativeAd((Activity) currentView.getContext());
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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // Position and layout child views within MyCustomView
        if (currentView != null) {
            currentView.layout(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    public NativeAd nativeAd;
    public void nativeAd(Activity activity) {
        AdLoader.Builder builder = new AdLoader.Builder(activity, App.getString(Const.g_native_banner));
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                NativeAdView adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_helper, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
//                customNativeAdView.setView(activity, R.layout.native_ad_layout_1);
                if (currentView != null) {
                    removeView(currentView);
                }
                currentView = adView;
                addView(currentView);
            }
        }).build();


        NativeAdOptions adOptions = new NativeAdOptions.Builder().build();
        builder.withNativeAdOptions(adOptions);


        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                loadFbNativeAd(activity);
            }

        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());

    }

    public void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));

        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        Objects.requireNonNull(adView.getMediaView()).setMediaContent(Objects.requireNonNull(nativeAd.getMediaContent()));


        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);

        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getStarRating() == null) {
            Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView())).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);

    }

    public void loadFbNativeAd(Activity activity) {
        com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(activity, App.getString(Const.fb_native_ad));

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e("TAG", "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e("TAG", "Native ad failed to load: " + adError.getErrorMessage());
                qureka_Native(activity);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d("TAG", "Native ad is loaded and ready to be displayed!");
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd, activity);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d("TAG", "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d("TAG", "Native ad impression logged!");
            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    public void inflateAd(com.facebook.ads.NativeAd nativeAd, Activity activity) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
//        NativeAdLayout nativeAdLayout = (NativeAdLayout) fbview;
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout_1, this, false);
        if (currentView != null) {
            removeView(currentView);
        }
        currentView = adView;
        addView(currentView);
//        customNativeAdView.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = this.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd, this);
//        View adView2 = com.facebook.ads.NativeAdView.render(activity, nativeAd, com.facebook.ads.NativeAdView.Type.HEIGHT_300);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        com.facebook.ads.MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        com.facebook.ads.MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    private void qureka_Native(Activity activity) {
//        if (isNetworkAvailable()) {

            RelativeLayout adView = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.qureka_native_layout, null, false);

            adView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

}

package com.app.sdkads.Model;


import com.google.gson.annotations.SerializedName;

public class MyAds {
    @SerializedName("code")
    public int code;
    @SerializedName("status")
    public boolean status;
    @SerializedName("message")
    public String message;
    @SerializedName("downloads")
    public int downloads;
    @SerializedName("data")
    public AdsData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public AdsData getData() {
        return data;
    }

    public void setData(AdsData data) {
        this.data = data;
    }

    public class AdsData {
        @SerializedName("carrier_id")
        public String carrier_id;
        @SerializedName("vpn_url")
        public String vpn_url;
        @SerializedName("country")
        public String country;
        @SerializedName("ad_status")
        public String ad_status;
        @SerializedName("app_status")
        public String app_status;
        @SerializedName("rating")
        public String rating;
        @SerializedName("custom_ad")
        public String custom_ad;
        @SerializedName("vpn_status")
        public String vpn_status;
        @SerializedName("extra_ad_status")
        public String extra_ad_status;

        @SerializedName("back_press_ads")
        public String back_press_ads;
        @SerializedName("g_banner")
        public String g_banner;
        @SerializedName("g_native_banner")
        public String g_native_banner;
        @SerializedName("g_app_open")
        public String g_app_open;
        @SerializedName("g_interstitial")
        public String g_interstitial;
        @SerializedName("g_rewarded")
        public String g_rewarded;
        @SerializedName("g_rewarded_interstitial")
        public String g_rewarded_interstitial;
        @SerializedName("fb_banner")
        public String fb_banner;
        @SerializedName("fb_native_ad")
        public String fb_native_ad;
        @SerializedName("fb_native_banner")
        public String fb_native_banner;
        @SerializedName("fb_interstitial")
        public String fb_interstitial;
        @SerializedName("fb_rewarded_video")
        public String fb_rewarded_video;
        @SerializedName("adx_banner")
        public String adx_banner;
        @SerializedName("adx_native_banner")
        public String adx_native_banner;
        @SerializedName("adx_app_open")
        public String adx_app_open;
        @SerializedName("adx_interstitial")
        public String adx_interstitial;
        @SerializedName("adx_rewarded")
        public String adx_rewarded;
        @SerializedName("adx_rewarded_interstitial")
        public String adx_rewarded_interstitial;
        @SerializedName("qu_link")
        public String qu_link;

        public String getBack_press_ads() {
            return back_press_ads;
        }

        public void setBack_press_ads(String back_press_ads) {
            this.back_press_ads = back_press_ads;
        }

        public String getCarrier_id() {
            return carrier_id;
        }

        public void setCarrier_id(String carrier_id) {
            this.carrier_id = carrier_id;
        }

        public String getVpn_url() {
            return vpn_url;
        }

        public void setVpn_url(String vpn_url) {
            this.vpn_url = vpn_url;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAd_status() {
            return ad_status;
        }

        public void setAd_status(String ad_status) {
            this.ad_status = ad_status;
        }

        public String getApp_status() {
            return app_status;
        }

        public void setApp_status(String app_status) {
            this.app_status = app_status;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getCustom_ad() {
            return custom_ad;
        }

        public void setCustom_ad(String custom_ad) {
            this.custom_ad = custom_ad;
        }

        public String getVpn_status() {
            return vpn_status;
        }

        public void setVpn_status(String vpn_status) {
            this.vpn_status = vpn_status;
        }

        public String getExtra_ad_status() {
            return extra_ad_status;
        }

        public void setExtra_ad_status(String extra_ad_status) {
            this.extra_ad_status = extra_ad_status;
        }

        public String getG_banner() {
            return g_banner;
        }

        public void setG_banner(String g_banner) {
            this.g_banner = g_banner;
        }

        public String getG_native_banner() {
            return g_native_banner;
        }

        public void setG_native_banner(String g_native_banner) {
            this.g_native_banner = g_native_banner;
        }

        public String getG_app_open() {
            return g_app_open;
        }

        public void setG_app_open(String g_app_open) {
            this.g_app_open = g_app_open;
        }

        public String getG_interstitial() {
            return g_interstitial;
        }

        public void setG_interstitial(String g_interstitial) {
            this.g_interstitial = g_interstitial;
        }

        public String getG_rewarded() {
            return g_rewarded;
        }

        public void setG_rewarded(String g_rewarded) {
            this.g_rewarded = g_rewarded;
        }

        public String getG_rewarded_interstitial() {
            return g_rewarded_interstitial;
        }

        public void setG_rewarded_interstitial(String g_rewarded_interstitial) {
            this.g_rewarded_interstitial = g_rewarded_interstitial;
        }

        public String getFb_banner() {
            return fb_banner;
        }

        public void setFb_banner(String fb_banner) {
            this.fb_banner = fb_banner;
        }

        public String getFb_native_ad() {
            return fb_native_ad;
        }

        public void setFb_native_ad(String fb_native_ad) {
            this.fb_native_ad = fb_native_ad;
        }

        public String getFb_native_banner() {
            return fb_native_banner;
        }

        public void setFb_native_banner(String fb_native_banner) {
            this.fb_native_banner = fb_native_banner;
        }

        public String getFb_interstitial() {
            return fb_interstitial;
        }

        public void setFb_interstitial(String fb_interstitial) {
            this.fb_interstitial = fb_interstitial;
        }

        public String getFb_rewarded_video() {
            return fb_rewarded_video;
        }

        public void setFb_rewarded_video(String fb_rewarded_video) {
            this.fb_rewarded_video = fb_rewarded_video;
        }

        public String getAdx_banner() {
            return adx_banner;
        }

        public void setAdx_banner(String adx_banner) {
            this.adx_banner = adx_banner;
        }

        public String getAdx_native_banner() {
            return adx_native_banner;
        }

        public void setAdx_native_banner(String adx_native_banner) {
            this.adx_native_banner = adx_native_banner;
        }

        public String getAdx_app_open() {
            return adx_app_open;
        }

        public void setAdx_app_open(String adx_app_open) {
            this.adx_app_open = adx_app_open;
        }

        public String getAdx_interstitial() {
            return adx_interstitial;
        }

        public void setAdx_interstitial(String adx_interstitial) {
            this.adx_interstitial = adx_interstitial;
        }

        public String getAdx_rewarded() {
            return adx_rewarded;
        }

        public void setAdx_rewarded(String adx_rewarded) {
            this.adx_rewarded = adx_rewarded;
        }

        public String getAdx_rewarded_interstitial() {
            return adx_rewarded_interstitial;
        }

        public void setAdx_rewarded_interstitial(String adx_rewarded_interstitial) {
            this.adx_rewarded_interstitial = adx_rewarded_interstitial;
        }

        public String getQu_link() {
            return qu_link;
        }

        public void setQu_link(String qu_link) {
            this.qu_link = qu_link;
        }
    }
}
			
			
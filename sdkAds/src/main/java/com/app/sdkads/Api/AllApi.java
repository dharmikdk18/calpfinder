package com.app.sdkads.Api;


import com.app.sdkads.Model.MyAds;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AllApi {

    String MyAds_URL = "pixel/add";

    @FormUrlEncoded
    @Headers({"token:cGl4ZWxhcHBAMjAyMw=="})
    @POST(MyAds_URL)
    Call<MyAds> doMyAds_URL_Data(@Field("package_name") String package_name,
                                 @Field("download") String download);


}

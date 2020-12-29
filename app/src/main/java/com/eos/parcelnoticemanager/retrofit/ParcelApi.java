package com.eos.parcelnoticemanager.retrofit;

import android.content.SharedPreferences;

import com.eos.parcelnoticemanager.tools.LoginActivity;
import com.eos.parcelnoticemanager.tools.ParcelRegisterActivity;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ParcelApi {

    @POST("/sagam/parcel/add/")
    Call add_parcel(@Header("token") String token, @Body JsonObject jsonObject);

}

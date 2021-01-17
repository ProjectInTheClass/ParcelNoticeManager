package com.eos.parcelnoticemanager.retrofit;

import com.eos.parcelnoticemanager.data.ResponseData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ParcelApi {
    @POST("sagam/parcel/add")
    Call<ResponseData> add_parcel(@Header("token") String token, @Body JsonObject jsonObject);

}

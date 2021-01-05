package com.eos.parcelnoticemanager.retrofit;

import com.eos.parcelnoticemanager.data.DormitoryData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DormitoryApi {
    @GET("sagam/dormitory")
    Call<DormitoryData> get_dormitory(@Header("token") String token);
    @POST("sagam/dormitory/add")
    Call add_dormitory(@Header("token") String token, @Body JsonObject jsonObject);
    @POST("sagam/dormitory/join")
    Call join_dormitory(@Header("token") String token, @Body JsonObject jsonObject);
}

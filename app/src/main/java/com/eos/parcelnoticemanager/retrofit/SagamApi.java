package com.eos.parcelnoticemanager.retrofit;

import com.eos.parcelnoticemanager.data.ResponseData;
import com.eos.parcelnoticemanager.data.UserData;
import com.eos.parcelnoticemanager.data.WasherData;
import com.eos.parcelnoticemanager.data.SagamData;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SagamApi {
    @GET("sagam")
    Call<SagamData> getSagamInfo(@Header("token") String token);

    @GET("sagam/user/userInfo")
    Call <List<UserData>> getUsers_byId(@Header("token") String token, @Query("user") int userId);

    @GET("sagam/user/list")
    Call <List<UserData>> getUsers(@Header("token") String token);
}

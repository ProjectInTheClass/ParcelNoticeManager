package com.eos.parcelnoticemanager.retrofit;

import com.eos.parcelnoticemanager.data.TokenVO;
import com.eos.parcelnoticemanager.data.SagamData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApi {
    @GET("sagam/")
    Call<SagamData> get_users();
    @GET("sagam/{id}")
    Call<SagamData> get_user(@Path("id") String userId);
    @POST("auth/join")
    Call<Object> join(@Body JsonObject jsonObject);
    @POST("auth/login")
    Call<TokenVO> login(@Body JsonObject jsonObject);
}

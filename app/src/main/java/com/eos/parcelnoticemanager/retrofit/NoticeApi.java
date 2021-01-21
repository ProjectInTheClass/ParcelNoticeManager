package com.eos.parcelnoticemanager.retrofit;

import com.eos.parcelnoticemanager.data.NoticeData;
import com.eos.parcelnoticemanager.data.ResponseData;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NoticeApi {
    @GET("sagam/notice")
    Call<List<NoticeData>> get_notice(@Header("token") String token);

    @POST("sagam/notice/add")
    Call<ResponseData> add_notice(@Header("token") String token, @Body JsonObject jsonObject);

    @DELETE("sagan/notice/delete")
    Call<ResponseData> delete_notice(@Header("token") String token, @Query("notice") int notice);

}

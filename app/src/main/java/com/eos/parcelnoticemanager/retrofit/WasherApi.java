package com.eos.parcelnoticemanager.retrofit;

import com.eos.parcelnoticemanager.data.ResponseData;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.UserData;
import com.eos.parcelnoticemanager.data.WasherData;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WasherApi {
    @POST("sagam/washer/add")
    Call <ResponseData> add_washer(@Header("token") String token, @Body JsonObject jsonObject);

    @DELETE("sagam/washer/delete")
    Call <ResponseData> delete_washer(@Header("token") String token, @Query("washer") int washer);

    @GET("user/washer/floor")
    Call<List<WasherData>> getWashers_byFloor(@Header("token") String token, @Query("floor") int floor);

    @GET("user/room/users")
    Call <List<WasherData>> get_users(@Header("token") String token, @Query("roomId") int roomId);
}

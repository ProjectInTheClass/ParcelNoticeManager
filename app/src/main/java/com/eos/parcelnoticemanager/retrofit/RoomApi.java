package com.eos.parcelnoticemanager.retrofit;

import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.UserData;
import com.eos.parcelnoticemanager.tools.LoginActivity;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RoomApi {
    @GET("/sagam/room/")
    Call<List<RoomData>> get_rooms(@Header("token") String token);

    @POST("/sagam/room/add")
    Call add_room(@Header("token") String token, @Body JsonObject jsonObject);

    @GET("/sagam/room/users")
    Call<List<UserData>> get_users(@Header("token") String token);
}

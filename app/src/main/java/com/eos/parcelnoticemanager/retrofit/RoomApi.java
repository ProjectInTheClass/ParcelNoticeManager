package com.eos.parcelnoticemanager.retrofit;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.FloorData;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RoomApi {

    //방 추가
    @POST("sagam/room/add")
    Call<FloorData> plus_room();
    //기숙사 호실 정보 가져오기
    @GET("sagam/room/")
    Call<List<FloorData>> get_room();
}

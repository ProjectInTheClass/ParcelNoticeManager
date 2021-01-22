package com.eos.parcelnoticemanager.retrofit;

import com.eos.parcelnoticemanager.data.ResponseData;
import com.eos.parcelnoticemanager.data.SagamData;
import com.eos.parcelnoticemanager.data.UserData;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface PointApi {
    @POST("sagam/point/add")
    Call<ResponseData> add_point(@Header("token") String token, @Body JsonObject jsonObject);

}

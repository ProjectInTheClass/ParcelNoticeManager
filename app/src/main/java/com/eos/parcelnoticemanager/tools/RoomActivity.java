package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.FloorAdapter;
import com.eos.parcelnoticemanager.adapter.ParcelFloorAdapter;
import com.eos.parcelnoticemanager.data.DormitoryData;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.data.ResponseData;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.TokenVO;
import com.eos.parcelnoticemanager.data.WasherData;
import com.eos.parcelnoticemanager.data.WasherFloorData;
import com.eos.parcelnoticemanager.retrofit.AuthApi;
import com.eos.parcelnoticemanager.retrofit.DormitoryApi;
import com.eos.parcelnoticemanager.retrofit.RoomApi;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RoomActivity extends AppCompatActivity {

    private static RecyclerView rvFloor;
    private static FloorAdapter floorAdapter;
    public static ArrayList<FloorData> globalfloors;
    int totalFloor;
    private static RoomApi roomApi;
    private DormitoryData dormitoryData;
    private DormitoryApi dormitoryApi;
    static SharedPreferences pref;
    public static Context context;
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        context = RoomActivity.this;

        pref = getSharedPreferences("token",0);
        init();

        rvFloor = findViewById(R.id.rvFloors);
    }

    private void init(){
        roomApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RoomApi.class);

        dormitoryApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DormitoryApi.class);

        globalfloors = new ArrayList<>();

        Call<DormitoryData> callGetDormitory = dormitoryApi.get_dormitory(getToken());
        Callback<DormitoryData> callback = new Callback<DormitoryData>() {
            @Override
            public void onResponse(Call<DormitoryData> call, Response<DormitoryData> response) {
                dormitoryData = response.body();
                totalFloor = dormitoryData.getStory();

                for(int i=0; i< totalFloor; i++){
                    FloorData temp = new FloorData();
                    temp.setFloorNum(i+1);
                    globalfloors.add(temp);
                }

                for(int i = 0; i<totalFloor; i++){
                    //해당 층에 있는 방들을 불러옵니다. 없을 수도 있어요.
                    Call<List<RoomData>> callRoomListByFloor = roomApi.getRooms_byFloor(getToken(), i+1);
                    if (callRoomListByFloor == null)
                        continue;
                    final FloorData floor = globalfloors.get(i);

                    //층에 있는 방들 정보를 불러옵니다.
                    Callback<List<RoomData>> callback2 = new Callback<List<RoomData>>() {
                        @Override
                        public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
                            floor.setRooms((ArrayList<RoomData>) response.body());
                            floorAdapter = new FloorAdapter(globalfloors, RoomActivity.this);
                            LinearLayoutManager manager = new LinearLayoutManager(RoomActivity.this);
                            rvFloor.setLayoutManager(manager);
                            rvFloor.setAdapter(floorAdapter);


                        }
                        @Override
                        public void onFailure(Call<List<RoomData>> call, Throwable t) {
                            Toast.makeText(RoomActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    };
                    callRoomListByFloor.enqueue(callback2);
                }

            }
            @Override
            public void onFailure(Call<DormitoryData> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        callGetDormitory.enqueue(callback);



    }
    public static String getToken(){
        return pref.getString("token","");
    }

    public static void PlusRoom(View v, final int position) {
        final FloorData floor = globalfloors.get(position);
        RoomData newRoom = new RoomData();
        floor.finalRoomNum++;
        newRoom.setRoomNum(floor.finalRoomNum);
        floor.rooms.add(newRoom);

        JsonObject json = new JsonObject();
        json.addProperty("floor", position+1);
        json.addProperty("roomNum", newRoom.getRoomNum());

        Call<ResponseData> call = roomApi.add_room(getToken(), json);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()){
                    rvFloor.setAdapter(floorAdapter);
                    UpdateRooms(floor.floorNum);


                }else{}
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e( "onFailure: ",t.getMessage() );
            }
        });
    }
    public static void MinusRoom(View v, int position){
        final FloorData floor = globalfloors.get(position);
        if(floor.rooms.size()!= 0) {
            int id = floor.rooms.get(floor.rooms.size()-1).getId();
            Call <ResponseData> call = roomApi.delete_room(getToken(), id);
            Log.e("삭제하는거 몇번? ", Integer.toString(id));
            call.enqueue(new Callback<ResponseData>(){
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    floor.rooms.remove(floor.rooms.size() - 1);
                    rvFloor.setAdapter(floorAdapter);
                    floor.finalRoomNum--;


                }
                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Log.e("onFailure: ", t.getMessage());
                }
            });
        }

    }
    public static void UpdateRooms(int whichFloor) {
        Log.e("여기 들어오나?", Integer.toString(whichFloor));

        Call<List<RoomData>> callRoomListByFloor = roomApi.getRooms_byFloor(getToken(), whichFloor);
        final FloorData floor = globalfloors.get(whichFloor - 1);

        Callback<List<RoomData>> callback2 = new Callback<List<RoomData>>() {
            @Override
            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
                floor.setRooms((ArrayList<RoomData>) response.body());

            }

            @Override
            public void onFailure(Call<List<RoomData>> call, Throwable t) {
                Log.e("실패?", "실패");
            }
        };
        callRoomListByFloor.enqueue(callback2);

    }
    public static void switchContext(){
        Intent intent = new Intent(context, PlusStudentActivity.class);
        context.startActivity(intent);
    }


}



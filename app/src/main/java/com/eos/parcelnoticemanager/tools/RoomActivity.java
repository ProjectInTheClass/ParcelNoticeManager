package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.FloorAdapter;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.UserData;
import com.eos.parcelnoticemanager.retrofit.RoomApi;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public class RoomActivity extends AppCompatActivity {

    private RecyclerView rvFloor;
    private FloorAdapter floorAdapter;
    public ArrayList<FloorData> globalfloors;
    int totalFloor;
    ArrayList<Integer> roomNum = new ArrayList<>();
    private RoomApi roomApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);


        globalfloors = prepareData();

        rvFloor = findViewById(R.id.rvFloors);

        floorAdapter = new FloorAdapter(globalfloors, RoomActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(RoomActivity.this);
        rvFloor.setLayoutManager(manager);
        rvFloor.setAdapter(floorAdapter);


        floorAdapter.setOnItemClickListener(new FloorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                FloorData floor = globalfloors.get(position);
                RoomData newRoom = new RoomData();
                floor.finalRoomNum++;
                newRoom.setRoomNum(floor.finalRoomNum);
                floor.rooms.add(newRoom);

                rvFloor.setAdapter(floorAdapter);
            }
        });

    }

    private ArrayList<FloorData> prepareData() {
        ArrayList<FloorData> floors = new ArrayList<FloorData>();

        //첫번째 subject 추가
        FloorData floor1 = new FloorData();
        floor1.setFloorNum(1);

        RoomData room101 = new RoomData();
        room101.setRoomNum(101);
        room101.setfloor(1);

        RoomData room102 = new RoomData();
        room102.setRoomNum(102);
        room102.setfloor(1);

        floor1.finalRoomNum = 102;
        floor1.rooms.add(room101);
        floor1.rooms.add(room102);
        floors.add(floor1);

        FloorData floor2 = new FloorData();
        floor2.setFloorNum(2);
        floor2.finalRoomNum = 200;

        floors.add(floor2);
        return floors;
    }

    private void init(){
        totalFloor = 5;
        ArrayList<FloorData> floors = new ArrayList<FloorData>();


        roomApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RoomApi.class);

        for(int i = 0; i<totalFloor; i++) {
            FloorData temp = new FloorData();
            temp.setFloorNum(i + 1);
            floors.add(temp);
        }

        for(int i = 0; i<totalFloor; i++){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("floor",i+1);
            Call <ArrayList<RoomData>> callRoomListByFloor = roomApi.getRooms_byFloor(ParcelRegisterActivity.getToken(), jsonObject);
            Callback<ArrayList<RoomData>> callback = new Callback<ArrayList<RoomData>>() {
                @Override
                public void onResponse(Call<ArrayList<RoomData>> call, Response<ArrayList<RoomData>> response) {}

                @Override
                public void onFailure(Call<ArrayList<RoomData>> call, Throwable t) {
                    Toast.makeText(RoomActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
            };
            callRoomListByFloor.enqueue(callback);

            for(int i = 0; i< cal)

            @POST("/sagam/room/add")
            Call add_room(@Header("token") String token, @Body JsonObject jsonObject);

            @PUT("/sagam/room/updateUser")
            Call update_user(@Header("token") String token, @Body JsonObject jsonObject);

            @GET("/sagam/room/getRoomsByFloor")
            Call <List<RoomData>>getRooms_byFloor(@Header("token") String toke, @Body JsonObject jsonObject);

            @GET("/sagam/room/users")
            Call <List<UserData>> get_users(@Header("token") String token, @Body JsonObject jsonObject);

            Call<ArrayList<LaundryData>> callLaundryList = laundryApi.get_laundry_list(jsonObject);
            Callback<ArrayList<LaundryData>> callback = new Callback<ArrayList<LaundryData>>() {
                @Override
                public void onResponse(Call<ArrayList<LaundryData>> call, Response<ArrayList<LaundryData>> response) {
                    laundryAdapters.add(new LaundryAdapter(response.body()));
                }

                @Override
                public void onFailure(Call<ArrayList<LaundryData>> call, Throwable t) {
                    Toast.makeText(LaundryConfirmActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
            };
            callLaundryList.enqueue(callback);
        }
    }
}



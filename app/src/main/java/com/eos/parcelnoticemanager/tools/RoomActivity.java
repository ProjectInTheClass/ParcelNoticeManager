package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.FloorAdapter;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.TokenVO;
import com.eos.parcelnoticemanager.retrofit.AuthApi;
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

    private RecyclerView rvFloor;
    private FloorAdapter floorAdapter;
    public ArrayList<FloorData> globalfloors = new ArrayList<>();
    int totalFloor;
    ArrayList<Integer> roomNum = new ArrayList<>();
    private RoomApi roomApi;
    int i;
    static SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        pref = getSharedPreferences("token",0);
        init();

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

    private void init(){
        totalFloor = 3;

        roomApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RoomApi.class);

        for(int i = 0; i<totalFloor; i++) {
            FloorData temp = new FloorData();
            temp.setFloorNum(i + 1);
            globalfloors.add(temp);
        }

        for(i = 0; i<totalFloor; i++){
            /*JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("floor",i+1);*/

            Call<List<RoomData>> callRoomListByFloor = roomApi.getRooms_byFloor(getToken(), i+1);
            Callback<List<RoomData>> callback = new Callback<List<RoomData>>() {
                @Override
                public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
                    globalfloors.get(i).setRooms((ArrayList<RoomData>) response.body());
                    for(int j = 0; j < globalfloors.size(); j++){
                        System.out.println(globalfloors.get(i).getRooms().get(j));
                    }
                    globalfloors.get(i).finalRoomNum = globalfloors.get(i).rooms.size()+globalfloors.get(i).floorNum*100;
                }
                @Override
                public void onFailure(Call<List<RoomData>> call, Throwable t) {
                    Toast.makeText(RoomActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
            };
            callRoomListByFloor.enqueue(callback);
        }
    }
    public static String getToken(){
        return pref.getString("token","");
    }
}



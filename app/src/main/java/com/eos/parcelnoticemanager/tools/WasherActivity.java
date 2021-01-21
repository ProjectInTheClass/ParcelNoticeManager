package com.eos.parcelnoticemanager.tools;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.FloorAdapter;
import com.eos.parcelnoticemanager.adapter.WasherFloorAdapter;
import com.eos.parcelnoticemanager.data.DormitoryData;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.data.ResponseData;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.WasherFloorData;
import com.eos.parcelnoticemanager.data.WasherData;
import com.eos.parcelnoticemanager.retrofit.DormitoryApi;
import com.eos.parcelnoticemanager.retrofit.RoomApi;
import com.eos.parcelnoticemanager.retrofit.WasherApi;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WasherActivity extends AppCompatActivity {

    private static RecyclerView rvWasherFloor;
    private static WasherFloorAdapter washerFloorAdapter;
    public static ArrayList<WasherFloorData> globalfloors = new ArrayList<>();
    private static WasherApi washerApi;
    private static DormitoryApi dormitoryApi;
    static SharedPreferences pref;
    private DormitoryData dormitoryData;
    private static int totalFloor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_washer);
        pref = getSharedPreferences("token",0);

        init();

        rvWasherFloor = findViewById(R.id.rvWasherFloor);
    }


    public void init(){

        washerApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WasherApi.class);

        dormitoryApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DormitoryApi.class);
        Call<DormitoryData> callGetDormitory = dormitoryApi.get_dormitory(getToken());
        Callback<DormitoryData> callback = new Callback<DormitoryData>() {
            @Override
            public void onResponse(Call<DormitoryData> call, Response<DormitoryData> response) {
                dormitoryData = response.body();
                totalFloor = dormitoryData.getStory();

                for(int i=0; i< totalFloor; i++){
                    WasherFloorData temp = new WasherFloorData();
                    temp.setFloorNum(i+1);
                    temp.isWasher = true;
                    globalfloors.add(temp);
                }
                Log.d("몇층???", Integer.toString(totalFloor));

                for(int i = 0; i<totalFloor; i++){
                    //해당 층에 있는 세탁기 건조기들을 불러옵니다. 없을 수도 있어요.
                    Call<List<WasherData>> callRoomListByFloor = washerApi.getWashers_byFloor(getToken(), i+1);
                    if (callRoomListByFloor == null)
                        continue;
                    final WasherFloorData floor = globalfloors.get(i);

                    //층에 있는 방들 정보를 불러옵니다.
                    Callback<List<WasherData>> callback2 = new Callback<List<WasherData>>() {
                        @Override
                        public void onResponse(Call<List<WasherData>> call, Response<List<WasherData>> response) {
                            floor.setWashers((ArrayList<WasherData>) response.body());
                            for(int i = 0; i<floor.washers.size(); i++){
                                floor.washers.get(i).washerNum = i+1;
                            }

                            floor.finalWasherNum = floor.washers.size();
                            washerFloorAdapter = new WasherFloorAdapter(globalfloors, WasherActivity.this);
                            LinearLayoutManager manager = new LinearLayoutManager(WasherActivity.this);
                            rvWasherFloor.setLayoutManager(manager);
                            rvWasherFloor.setAdapter(washerFloorAdapter);

                        }
                        @Override
                        public void onFailure(Call<List<WasherData>> call, Throwable t) {
                            Toast.makeText(WasherActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
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

    public static void PlusWasher(View v, final int position) {
        JsonObject json = new JsonObject();

        json.addProperty("floor", position + 1);

        Call<ResponseData> call = washerApi.add_washer(getToken(), json);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    final WasherFloorData floor = globalfloors.get(position);
                    Log.e("몇층에 추가? ", Integer.toString(floor.floorNum));
                    Log.e("몇호??", Integer.toString(floor.finalWasherNum));
                    WasherData newWasher = new WasherData();
                    floor.finalWasherNum++;
                    newWasher.washerNum = floor.finalWasherNum;
                    floor.washers.add(newWasher);
                    rvWasherFloor.setAdapter(washerFloorAdapter);
                    //바뀐 부분이 있는 층을 업데이트하기
                    UpdateWasher(floor.floorNum);

                } else {}
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }
        });
    }
    public static void MinusWasher(View v, int position){
        final WasherFloorData floor = globalfloors.get(position);
        if(floor.washers.size()!= 0) {
            int id = floor.washers.get(floor.washers.size()-1).getId();
            Call <ResponseData> call = washerApi.delete_washer(getToken(), id);
            Log.e("삭제하는거 몇번? ", Integer.toString(id));
            call.enqueue(new Callback<ResponseData>(){
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    floor.washers.remove(floor.washers.size() - 1);
                    rvWasherFloor.setAdapter(washerFloorAdapter);
                    floor.finalWasherNum--;


                }
                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Log.e("onFailure: ", t.getMessage());
                }
            });
        }

    }
    public static void UpdateWasher(int whichFloor){
        Log.e("여기 들어오나?", Integer.toString(whichFloor));

        //해당 층에 있는 세탁기 건조기들을 불러옵니다. 없을 수도 있어요.
        Call<List<WasherData>> callRoomListByFloor = washerApi.getWashers_byFloor(getToken(), whichFloor);
        final WasherFloorData floor = globalfloors.get(whichFloor-1);

        //층에 있는 세탁기들 정보를 불러옵니다.
        Callback<List<WasherData>> callback2 = new Callback<List<WasherData>>() {
            @Override
            public void onResponse(Call<List<WasherData>> call, Response<List<WasherData>> response) {
                floor.setWashers((ArrayList<WasherData>) response.body());
                for(int i = 0; i<floor.washers.size(); i++){
                    floor.washers.get(i).washerNum = i+1;
                }
                Log.e("마지막 애 id", Integer.toString(floor.washers.get(floor.washers.size()-1).getId()));
                floor.finalWasherNum = floor.washers.size();

            }
            @Override
            public void onFailure(Call<List<WasherData>> call, Throwable t) {
                Log.e("실패?", "실패");
            }
        };
        callRoomListByFloor.enqueue(callback2);

    }


}
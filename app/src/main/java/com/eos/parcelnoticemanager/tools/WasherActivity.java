package com.eos.parcelnoticemanager.tools;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private RecyclerView rvWasherFloor;
    private WasherFloorAdapter washerFloorAdapter;
    public ArrayList<WasherFloorData> globalfloors = new ArrayList<>();
    private WasherApi washerApi;
    private DormitoryApi dormitoryApi;
    static SharedPreferences pref;
    private DormitoryData dormitoryData;
    private int totalFloor;



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

                            //+버튼 눌렀을때 세탁기 추가하기
                            washerFloorAdapter.setOnItemClickListener(new WasherFloorAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    WasherFloorData floor = globalfloors.get(position);
                                    WasherData newWasher = new WasherData();
                                    floor.finalWasherNum++;
                                    newWasher.washerNum = floor.finalWasherNum;
                                    floor.washers.add(newWasher);
                                    rvWasherFloor.setAdapter(washerFloorAdapter);
                                    JsonObject json = new JsonObject();

                                    json.addProperty("floor", position+1);

                                    Call<ResponseData> call = washerApi.add_washer(getToken(), json);
                                    call.enqueue(new Callback<ResponseData>() {
                                        @Override
                                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                            if(response.isSuccessful()){
                                                Toast.makeText(WasherActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                                                if(response.body() == null){
                                                    Toast.makeText(WasherActivity.this,"알 수 없는 에러입니다. 개발자에게 문의하세요",Toast.LENGTH_LONG).show();
                                                    return;
                                                }

                                            }else{
                                                Toast.makeText(WasherActivity.this,response.message().toString(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call call, Throwable t) {
                                            Log.e( "onFailure: ",t.getMessage() );
                                        }
                                    });

                                }
                            });

                            //-버튼을 눌렀을 때(마지막 방 삭제하기)
                            washerFloorAdapter.setOnItemClickListener(new WasherFloorAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    WasherFloorData floor = globalfloors.get(position);
                                    //floor.finalWasherNum--;
                                    if(floor.washers.size()!= 0) {
                                        floor.washers.remove(floor.washers.size() - 1);
                                        rvWasherFloor.setAdapter(washerFloorAdapter);
                                    }

                                    /*
                                    JsonObject json = new JsonObject();

                                    json.addProperty("floor", position+1);

                                    Call<ResponseData> call = washerApi.add_washer(getToken(), json);
                                    call.enqueue(new Callback<ResponseData>() {
                                        @Override
                                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                            if(response.isSuccessful()){
                                                Toast.makeText(WasherActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                                                if(response.body() == null){
                                                    Toast.makeText(WasherActivity.this,"알 수 없는 에러입니다. 개발자에게 문의하세요",Toast.LENGTH_LONG).show();
                                                    return;
                                                }

                                            }else{
                                                Toast.makeText(WasherActivity.this,response.message().toString(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call call, Throwable t) {
                                            Log.e( "onFailure: ",t.getMessage() );
                                        }
                                    });*/



                                }

                            });
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
}
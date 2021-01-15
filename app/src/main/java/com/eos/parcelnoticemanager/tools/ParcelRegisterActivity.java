package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.ParcelFloorAdapter;
import com.eos.parcelnoticemanager.custom_dialog.ParcelDetailDialog;
import com.eos.parcelnoticemanager.custom_dialog.ParcelStudentListDialog;
import com.eos.parcelnoticemanager.data.DormitoryData;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.retrofit.DormitoryApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParcelRegisterActivity extends AppCompatActivity {

    RecyclerView rvFloor;
    ParcelFloorAdapter floorAdapter;
    static SharedPreferences pref;
    static String baseUrl;
    private DormitoryData dormitoryData;
    private DormitoryApi dormitoryApi;
    static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_parcel_register);
        baseUrl = getString(R.string.base_url);
        pref = getSharedPreferences("token",0);
        context = ParcelRegisterActivity.this;

        init();

    }

    private void init() {

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

                 rvFloor = findViewById(R.id.parcel_recyclerView_floor);
                 ArrayList<Integer> floors = new ArrayList<>();
                 for(int i=0; i<dormitoryData.getStory(); i++){
                     floors.add(i+1);
                 }
                 floorAdapter = new ParcelFloorAdapter(ParcelRegisterActivity.this,floors);
                 LinearLayoutManager manager = new LinearLayoutManager(ParcelRegisterActivity.this);
                 rvFloor.setLayoutManager(manager);
                 rvFloor.setAdapter(floorAdapter);
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
    public static String getBaseUrl(){return baseUrl;}
    public static void showDialog(RoomData room){
        ParcelStudentListDialog parcelStudentListDialog = new ParcelStudentListDialog(context,room);
        parcelStudentListDialog.setCanceledOnTouchOutside(true);
        parcelStudentListDialog.setCancelable(true);
        parcelStudentListDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        parcelStudentListDialog.show();
    }
    public static void showDetailDialog(){
        ParcelDetailDialog parcelDetailDialog = new ParcelDetailDialog(context);
        parcelDetailDialog.setCanceledOnTouchOutside(true);
        parcelDetailDialog.setCancelable(true);
        parcelDetailDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        parcelDetailDialog.show();
    }
}
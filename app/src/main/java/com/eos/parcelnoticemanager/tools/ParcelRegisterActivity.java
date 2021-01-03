package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.ParcelFloorAdapter;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.UserData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class ParcelRegisterActivity extends AppCompatActivity {

    RecyclerView rvFloor;
    ParcelFloorAdapter floorAdapter;
    static SharedPreferences pref;
    static String baseUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_parcel_register);
        baseUrl = getString(R.string.base_url);
        pref = getSharedPreferences("token",0);

        rvFloor = findViewById(R.id.parcel_recyclerView_floor);

        floorAdapter = new ParcelFloorAdapter(getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(ParcelRegisterActivity.this);
        rvFloor.setLayoutManager(manager);
        rvFloor.setAdapter(floorAdapter);
    }

    public static String getToken(){
        return pref.getString("token","");
    }
    public static String getBaseUrl(){return baseUrl;}
}
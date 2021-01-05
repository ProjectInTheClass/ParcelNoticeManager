package com.eos.parcelnoticemanager.tools;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.WasherFloorAdapter;
import com.eos.parcelnoticemanager.data.WasherFloorData;
import com.eos.parcelnoticemanager.data.WasherData;

import java.util.ArrayList;


public class WasherActivity extends AppCompatActivity {

    private RecyclerView rvWasherFloor;
    private WasherFloorAdapter washerFloorAdapter;
    public ArrayList<WasherFloorData> globalfloors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_washer);


        globalfloors = prepareData();

        rvWasherFloor = findViewById(R.id.rvWasherFloor);

        washerFloorAdapter = new WasherFloorAdapter(globalfloors, WasherActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(WasherActivity.this);
        rvWasherFloor.setLayoutManager(manager);
        rvWasherFloor.setAdapter(washerFloorAdapter);


        washerFloorAdapter.setOnItemClickListener(new WasherFloorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                WasherFloorData floor = globalfloors.get(position);
                WasherData newWasher = new WasherData();
                floor.finalWasherNum++;
                newWasher.washerNum = floor.finalWasherNum;
                floor.washers.add(newWasher);

                rvWasherFloor.setAdapter(washerFloorAdapter);
            }
        });

    }

    private ArrayList<WasherFloorData> prepareData() {
        ArrayList<WasherFloorData> floors = new ArrayList<WasherFloorData>();

        //첫번째 subject 추가
        WasherFloorData floor1 = new WasherFloorData();
        floor1.floorNum = 1;
        floor1.isWasher = true;

        WasherData washer1 = new WasherData();
        washer1.washerNum = 1;

        WasherData washer2= new WasherData();
        washer2.washerNum = 2;

        WasherData washer3 = new WasherData();
        washer3.washerNum = 3;

        floor1.finalWasherNum = 3;
        floor1.washers.add(washer1);
        floor1.washers.add(washer2);
        floor1.washers.add(washer3);
        floors.add(floor1);

        WasherFloorData floor1_dry = new WasherFloorData();
        floor1_dry.floorNum = 1;
        floor1_dry.finalWasherNum = 0;

        floors.add(floor1_dry);
        return floors;
    }
}
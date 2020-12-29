package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.FloorAdapter;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.data.RoomData;

import java.util.ArrayList;


public class RoomActivity extends AppCompatActivity {

    private RecyclerView rvFloor;
    private FloorAdapter floorAdapter;
    public ArrayList<FloorData> globalfloors;


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
                newRoom.roomNum = floor.finalRoomNum;
                floor.rooms.add(newRoom);

                rvFloor.setAdapter(floorAdapter);
            }
        });




    }

    private ArrayList<FloorData> prepareData() {
        ArrayList<FloorData> floors = new ArrayList<FloorData>();

        //첫번째 subject 추가
        FloorData floor1 = new FloorData();
        floor1.floorNum = 1;

        RoomData room101 = new RoomData();
        room101.roomNum = 101;

        RoomData room102 = new RoomData();
        room102.roomNum = 102;

        RoomData room103 = new RoomData();
        room103.roomNum = 103;

        floor1.finalRoomNum = 103;
        floor1.rooms.add(room101);
        floor1.rooms.add(room102);
        floor1.rooms.add(room103);
        floors.add(floor1);

        FloorData floor2 = new FloorData();
        floor2.floorNum = 2;
        floor2.finalRoomNum = 200;

        floors.add(floor2);
        return floors;
    }
}



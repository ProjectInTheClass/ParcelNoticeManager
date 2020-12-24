package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.data.RoomData;

import java.util.ArrayList;

public class ParcelRegisterActivity extends AppCompatActivity {

    RecyclerView rvFloor;
    ParcelFloorAdapter floorAdapter;
    public ArrayList<FloorData> globalfloors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_parcel_register);

        rvFloor = findViewById(R.id.parcel_recyclerView_floor);
        globalfloors = prepareData();

        floorAdapter = new ParcelFloorAdapter(globalfloors,this);
        LinearLayoutManager manager = new LinearLayoutManager(ParcelRegisterActivity.this);
        rvFloor.setLayoutManager(manager);
        rvFloor.setAdapter(floorAdapter);
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

        RoomData room201 = new RoomData();
        room201.roomNum = 201;

        RoomData room202 = new RoomData();
        room202.roomNum = 202;

        RoomData room203 = new RoomData();
        room203.roomNum = 203;

        RoomData room204 = new RoomData();
        room204.roomNum = 204;

        floor2.finalRoomNum = 204;
        floor2.rooms.add(room201);
        floor2.rooms.add(room202);
        floor2.rooms.add(room203);
        floor2.rooms.add(room204);
        floors.add(floor2);

        FloorData floor3 = new FloorData();
        floor3.floorNum = 3;

        RoomData room301 = new RoomData();
        room301.roomNum = 301;

        RoomData room302 = new RoomData();
        room302.roomNum = 302;

        RoomData room303 = new RoomData();
        room303.roomNum = 303;

        RoomData room304 = new RoomData();
        room304.roomNum = 304;

        RoomData room305 = new RoomData();
        room305.roomNum = 305;

        floor3.finalRoomNum = 305;
        floor3.rooms.add(room301);
        floor3.rooms.add(room302);
        floor3.rooms.add(room303);
        floor3.rooms.add(room304);
        floor3.rooms.add(room305);
        floors.add(floor3);

        FloorData floor4 = new FloorData();
        floor4.floorNum = 4;

        RoomData room401 = new RoomData();
        room401.roomNum = 401;

        RoomData room402 = new RoomData();
        room402.roomNum = 402;

        RoomData room403 = new RoomData();
        room403.roomNum = 403;

        RoomData room404 = new RoomData();
        room404.roomNum = 404;

        RoomData room405 = new RoomData();
        room405.roomNum = 405;

        RoomData room406 = new RoomData();
        room406.roomNum = 406;

        floor4.finalRoomNum = 406;
        floor4.rooms.add(room401);
        floor4.rooms.add(room402);
        floor4.rooms.add(room403);
        floor4.rooms.add(room404);
        floor4.rooms.add(room405);
        floor4.rooms.add(room406);
        floors.add(floor4);

        return floors;
    }
}
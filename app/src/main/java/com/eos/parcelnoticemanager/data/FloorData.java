package com.eos.parcelnoticemanager.data;

import java.util.ArrayList;

public class FloorData {
    public int id;
    public int floorNum;
    public int finalRoomNum = floorNum*100;
    public ArrayList<RoomData> rooms = new ArrayList<RoomData>();
}

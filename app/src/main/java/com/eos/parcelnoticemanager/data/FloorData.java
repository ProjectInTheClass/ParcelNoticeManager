package com.eos.parcelnoticemanager.data;

import java.util.ArrayList;

public class FloorData {
    public int id;
    public int floorNum;
    public int finalRoomNum = floorNum*100;
    public ArrayList<RoomData> rooms = new ArrayList<RoomData>();

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public ArrayList<RoomData> getRooms() {
        return rooms;
    }

    public int getFinalRoomNum() {
        return finalRoomNum;
    }

    public void setRooms(ArrayList<RoomData> rooms) {
        this.rooms = rooms;
    }
}

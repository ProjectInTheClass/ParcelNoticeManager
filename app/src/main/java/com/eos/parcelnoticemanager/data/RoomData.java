package com.eos.parcelnoticemanager.data;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RoomData {
    public Serializable students;
    int id;
    int roomNum;
    int floor;
    int dormitory;

    public int getRoomNum() {
        return roomNum;

    }

    public void setRoomNum(int roomNum){
        this.roomNum = roomNum;
    }

    public void setfloor(int roomNum){
        this.floor = floor;
    }
    public void setDormitory(int dormitory){
        this.dormitory = dormitory;
    }
}

package com.eos.parcelnoticemanager.data;

import java.util.ArrayList;

public class WasherFloorData {
    public int id;
    public int floorNum;
    public int finalWasherNum = floorNum*100;
    //true인 경우 세탁기에 대한 정보, false인 경우 건주기에 대한 정보
    public boolean isWasher;
    public ArrayList<WasherData> washers = new ArrayList<WasherData>();

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public void setWashers(ArrayList<WasherData> washers){
        this.washers = washers;
    }
}

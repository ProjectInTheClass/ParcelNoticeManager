package com.eos.parcelnoticemanager.data;

public class WasherData {
    private int id;
    private int floor;
    private int domitory;
    public int washerNum;

    public void setDomitory(int domitory) {
        this.domitory = domitory;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDomitory() {
        return domitory;
    }

    public int getFloor() {
        return floor;
    }

    public int getId() {
        return id;
    }
}

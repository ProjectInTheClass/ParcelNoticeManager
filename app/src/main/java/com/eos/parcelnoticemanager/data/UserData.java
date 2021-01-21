package com.eos.parcelnoticemanager.data;

public class UserData {
    int id;
    String email;
    String password;
    String name;
    String phoneNum;
    String provider;
    String snsId;
    boolean isAuthed;
    int penalty;
    int advantage;
    int dormitory;
    int room;

    public UserData(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){return id;}

    public String getPhoneNum(){
        return phoneNum;
    }
}

package com.eos.parcelnoticemanager.data;

public class StudentData {
    int id;
    String email;
    String password;
    String name;
    int phoneNum;
    String provider;
    String snsId;
    boolean isAuthed;
    int penalty;
    int advantage;
    int room;
    int dormitory;

    public StudentData(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

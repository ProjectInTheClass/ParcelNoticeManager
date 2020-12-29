package com.eos.parcelnoticemanager.data;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RoomData {
    int id;
    public int roomNum;
    public ArrayList<StudnetInRoomData> students;

    public RoomData(){
        students = new ArrayList<>();
    }
    public void AddStudent(String name, String number){
        StudnetInRoomData student = new StudnetInRoomData(name, number);
        students.add(student);
    }

    public void DeleteStudent(String name, String number){
        students.remove(new StudnetInRoomData(name, number));
    }
}

package com.eos.parcelnoticemanager.data;

import java.util.ArrayList;

public class StudnetInRoomData {
    public int roomNum;
    public ArrayList<String> studentNameList;
    public ArrayList<String> studentNumberList;

    public StudnetInRoomData(int roomNum){
        this.roomNum = roomNum;
        studentNameList = new ArrayList<>();
        studentNumberList = new ArrayList<>();
    }

    public void addStudent(String name, String studentNumber){
        studentNameList.add(name);
        studentNumberList.add(studentNumber);
    }
    public void deleteStudent(String name, String studentNumber){
        studentNameList.remove(name);
        studentNumberList.remove(studentNumber);
    }
}

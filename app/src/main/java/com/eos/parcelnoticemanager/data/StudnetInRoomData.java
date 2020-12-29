package com.eos.parcelnoticemanager.data;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudnetInRoomData implements Serializable {
    String studentNum;
    String studentName;

    StudnetInRoomData(String name, String num){
        studentName = name;
        studentNum = num;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentNum() {
        return studentNum;
    }
}


package com.eos.parcelnoticemanager.data;

import android.view.View;
import android.widget.EditText;

public class NoticeData {
    private int id;
    private String contents;
    private int sagam;

    public int getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}

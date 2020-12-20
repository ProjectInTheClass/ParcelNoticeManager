package com.eos.parcelnoticemanager.data;

import android.view.View;
import android.widget.EditText;

public class NoticeData {
    String noticeData = "공지";

    public void saveNoticeData(String noticeData){
        this.noticeData = noticeData;
    }

    public String getNoticeData(){
        return noticeData;
    }


}

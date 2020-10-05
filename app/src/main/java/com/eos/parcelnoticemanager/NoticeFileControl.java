package com.eos.parcelnoticemanager;

import android.view.View;
import android.widget.EditText;

class NoticeFileControl {
    String noticeData = "공지";

    public void saveNoticeData(String noticeData){
        this.noticeData = noticeData;
    }

    public String getNoticeData(){
        return noticeData;
    }


}

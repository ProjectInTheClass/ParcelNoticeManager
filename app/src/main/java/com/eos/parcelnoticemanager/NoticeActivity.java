package com.eos.parcelnoticemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NoticeActivity extends AppCompatActivity {

    NoticeFileControl cotent = new NoticeFileControl();
    private Button btn_save;
    private EditText et_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        et_notice.setText(new String(cotent.getNoticeData()));
        setContentView(R.layout.activity_notice);

        et_notice = findViewById(R.id.editText_notice);
        btn_save = findViewById(R.id.button_save);

        btn_save.setOnClickListener(btnSaveListner);

    }

    View.OnClickListener btnSaveListner = new View.OnClickListener(){
        public void onClick(View v){
            cotent.saveNoticeData(et_notice.getText().toString());
        }
    };
}
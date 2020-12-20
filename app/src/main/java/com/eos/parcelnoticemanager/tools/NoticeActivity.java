package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.NoticeData;

public class NoticeActivity extends AppCompatActivity {

    NoticeData cotent = new NoticeData();
    private Button btn_save;
    private EditText et_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notice);

        et_notice = (EditText)findViewById(R.id.editText_notice);
        et_notice.setText(cotent.getNoticeData());

        et_notice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_notice.setText(cotent.getNoticeData());
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                et_notice.setText(editable.toString());

            }
        });

        btn_save = findViewById(R.id.button_save);

        btn_save.setOnClickListener(btnSaveListner);
    }

    View.OnClickListener btnSaveListner = new View.OnClickListener(){
        public void onClick(View v){
            cotent.saveNoticeData(et_notice.getText().toString());
        }
    };
}
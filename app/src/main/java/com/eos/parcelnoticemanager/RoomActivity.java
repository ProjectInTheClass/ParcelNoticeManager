package com.eos.parcelnoticemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RoomActivity extends AppCompatActivity {
    EditText ed_rn;
    EditText ed_name;
    EditText ed_sn;
    Button btn_save;
    RoomFileControl cotent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        ed_rn = findViewById(R.id.editText_roomNumber);
        ed_name = findViewById(R.id.editText_studentName_room);
        ed_sn = findViewById(R.id.editText_studentNumber);
        btn_save = findViewById(R.id.button_save_room);

        btn_save.setOnClickListener(btnSaveListner);
    }

    View.OnClickListener btnSaveListner = new View.OnClickListener(){
        public void onClick(View v){
            cotent = new RoomFileControl(Integer.parseInt(ed_rn.getText().toString()), ed_name.getText().toString(),
                    Integer.parseInt(ed_rn.getText().toString()));
        }
    };
}
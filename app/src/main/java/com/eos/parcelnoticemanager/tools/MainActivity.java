package com.eos.parcelnoticemanager.tools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.custom_dialog.ParcelStudentListDialog;

public class MainActivity extends AppCompatActivity {
    private Button btnParcelRegister, btnRoomRegister, btnRewardRegister, btnNoticeRegister, btnWasherRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnParcelRegister = findViewById(R.id.button_main_parcel);
        btnRoomRegister = findViewById(R.id.button_main_room);
        btnRewardRegister = findViewById(R.id.button_main_reward);
        btnNoticeRegister = findViewById(R.id.button_main_notice);
        btnWasherRegister = findViewById(R.id.button_main_washer);


        btnParcelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ParcelRegisterActivity.class);
                startActivity(intent);
            }
        });

        btnRoomRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                startActivity(intent);
            }
        });

        btnWasherRegister.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WasherActivity.class);
                startActivity(intent);
            }
        });

        btnRewardRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RewardActivity.class);
                startActivity(intent);
            }
        });


        btnNoticeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
    }
}
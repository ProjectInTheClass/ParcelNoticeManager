package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;

public class MainActivity extends DefaultActivity {
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
package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.RewardData;

public class RewardActivity extends AppCompatActivity {

    private EditText ed_name;
    private EditText ed_score;
    private EditText ed_reason;
    private Button btn_save_reward;
    private Button btn_plus;
    private Button btn_minus;
    boolean isPlus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        ed_name = (EditText)findViewById(R.id.editText_studentName);
        ed_score = (EditText)findViewById(R.id.editText_score);
        ed_reason = (EditText)findViewById(R.id.editText_reason);
        btn_plus = (Button)findViewById(R.id.button_plusPoint);
        btn_plus.setOnClickListener(btnPlusListener);

        btn_minus = (Button)findViewById(R.id.button_minusPoint);
        btn_minus.setOnClickListener(btnMinusListener);

        btn_save_reward = (Button)findViewById(R.id.button_save_reward);
        btn_save_reward.setOnClickListener(btnSaveListener);
    }

    View.OnClickListener btnPlusListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isPlus = true;
        }
    };

    View.OnClickListener btnMinusListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isPlus = false;
        }
    };


    View.OnClickListener btnSaveListener = new View.OnClickListener(){
        public void onClick(View v){
            RewardData cotent = new RewardData(ed_name.getText().toString());
            if(isPlus == true){
                cotent.plusScore(Integer.parseInt(ed_score.getText().toString()), ed_reason.getText().toString());
            }
            else{
                cotent.minusScore(Integer.parseInt(ed_score.getText().toString()), ed_reason.getText().toString());
            }
        }
    };
}
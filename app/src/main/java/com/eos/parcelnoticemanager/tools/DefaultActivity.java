package com.eos.parcelnoticemanager.tools;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
// 뒤로가기를 눌렀을 경우, 두번 눌러야 꺼지는 액티비티 입니다.
// 로그인, 메인 화면이 해당됩니다.
public class DefaultActivity extends AppCompatActivity {
    private Toast toast;
    private long backKeyPressedTime = 0;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()> backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            finish();
            toast.cancel();
        }
    }
}

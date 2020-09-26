package com.eos.parcelnoticemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister;
    private EditText editTextSchool, editTextPassword;
    private CheckBox checkBoxAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.button_login_login);
        btnRegister = findViewById(R.id.button_login_register);
        editTextSchool = findViewById(R.id.editText_login_school);
        editTextPassword = findViewById(R.id.editText_login_password);
        checkBoxAutoLogin = findViewById(R.id.checkbox_login_autoLogin);

        if(checkBoxAutoLogin.isChecked() == true){
            //자동로그인
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextSchool.getText().toString();
                String password = editTextPassword.getText().toString();

                //일치하는 회원이 있는지 확인

                //로그인 성공시
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                //로그인 실패시
                /*Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                return;*/
            }
        });

/*        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });*/
    }
}
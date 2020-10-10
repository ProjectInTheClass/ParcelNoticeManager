package com.eos.parcelnoticemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;


public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister, btnKakaoLogin;
    private EditText editTextID, editTextPassword;
    private CheckBox checkBoxAutoLogin;
    private SessionCallBack sessionCallBack;
    private Boolean loginCheck = false;
    private SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.button_login_login);
        btnRegister = findViewById(R.id.button_login_register);
        btnKakaoLogin = findViewById(R.id.button_login_kakao);
        editTextID = findViewById(R.id.editText_login_id);
        editTextPassword = findViewById(R.id.editText_login_password);
        checkBoxAutoLogin = findViewById(R.id.checkbox_login_autoLogin);
        sessionCallBack = new SessionCallBack();
        pref = getSharedPreferences("setting",0);
        editor = pref.edit();

        Session.getCurrentSession().addCallback(sessionCallBack);
        Session.getCurrentSession().checkAndImplicitOpen();


        //자동로그인
        if(pref.getBoolean("autoLogin", false)){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userID", pref.getString("id",""));
            startActivity(intent);
        }

        btnKakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextID.getText().toString();
                String password = editTextPassword.getText().toString();

                //일치하는 회원이 있는지 확인
                Boolean validation = loginValidation(id,password);

                //로그인 성공시
                if(validation) {
                    if(loginCheck){
                        editor.putString("id",id);
                        editor.putString("password",password);
                        editor.putBoolean("autoLogin",true);
                        editor.commit();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userID", id);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        checkBoxAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loginCheck = true;
                }
                else{
                    loginCheck = false;
                    editor.clear();
                    editor.commit();
                }
            }
        });

/*        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)) {
           super.onActivityResult(requestCode, resultCode, data);
           return;
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallBack);
    }

    private boolean loginValidation(String id, String password){
        //구현하기
        return true;
    }

    private class SessionCallBack implements ISessionCallback{
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE){
                        Toast.makeText(getApplicationContext(),"네트워크 연결이 불안정 합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    //로그인 도중 세션이 비정상적인 이유로 닫혔을 때 작동, 거의 없음
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    // 로그인 성공

                    //처음 로그인 하는 계정인 경우 ->회원가입 (서버에 같은 id가 있는지 찾아보기)

                    //처음 로그인 하는 게 아닌 경우
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("userID", result.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+ exception.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
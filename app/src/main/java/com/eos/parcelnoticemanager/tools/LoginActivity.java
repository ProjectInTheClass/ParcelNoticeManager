package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.custom_dialog.JoinDialog;
import com.eos.parcelnoticemanager.data.TokenVO;
import com.eos.parcelnoticemanager.retrofit.AuthApi;
import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import com.google.gson.JsonObject;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister, btnKakaoLogin;
    private EditText editTextID, editTextPassword, etEmail;

    private CheckBox checkBoxAutoLogin;
    private SessionCallBack sessionCallBack;
    private Boolean loginCheck = false;
    private Retrofit retrofit;
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

        etEmail = findViewById(R.id.editText_login_id);
        editTextPassword = findViewById(R.id.editText_login_password);
        checkBoxAutoLogin = findViewById(R.id.checkbox_login_autoLogin);
        sessionCallBack = new SessionCallBack();
        pref = getSharedPreferences("setting",0);
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Session.getCurrentSession().addCallback(sessionCallBack);
        Session.getCurrentSession().checkAndImplicitOpen();

        pref = this.getSharedPreferences("token", this.MODE_PRIVATE);
        editor = pref.edit();

        //자동로그인
        if(pref.getBoolean("autoLogin", false)){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userID", pref.getString("id",""));
            startActivity(intent);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextID.getText().toString();
                String email = etEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                JsonObject json = new JsonObject();
                json.addProperty("email", email);
                json.addProperty("password", password);

                Call<TokenVO> call = retrofit.create(AuthApi.class).login(json);
                call.enqueue(new Callback<TokenVO>() {

                    @Override
                    public void onResponse(Call<TokenVO> call, Response<TokenVO> response) {
                        if(response.isSuccessful()){
                            Log.d("onResponse: ", response.body().getToken());
                            Toast.makeText(LoginActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                            editor.putString("token", response.body().getToken());
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,response.message().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.e( "onFailure: ",t.getMessage() );
                        Toast.makeText(LoginActivity.this,"알 수 없는 에러입니다. 개발자에게 문의하세요",Toast.LENGTH_LONG).show();
                    }
                });
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinDialog joinDialog = new JoinDialog(LoginActivity.this);
                joinDialog.setCanceledOnTouchOutside(true);
                joinDialog.setCancelable(true);
                joinDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                joinDialog.show();
            }
        });
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

    private class SessionCallBack implements ISessionCallback {
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

                    //처음 로그인하는 계정인 경우 ->회원가입


                    //처음 로그인하는 게 아닌 경우
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


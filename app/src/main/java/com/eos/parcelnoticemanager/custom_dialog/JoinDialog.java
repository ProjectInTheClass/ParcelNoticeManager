package com.eos.parcelnoticemanager.custom_dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.retrofit.AuthApi;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinDialog extends Dialog {
    private EditText etEmail, etPassword,etName;
    private Button btnConfirm, btnCancel;
    private Context context;
    private CustomDialogClickListener clickListener;
    private Retrofit retrofit;

    public EditText getEtEmail() {
        return etEmail;
    }

    public EditText getEtPassword() {
        return etPassword;
    }

    public EditText getEtName() {
        return etName;
    }

    public Button getBtnConfirm() {
        return btnConfirm;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }


    public JoinDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_join);
        etEmail =  findViewById(R.id.et_join_email);
        etPassword = findViewById(R.id.et_join_password);
        etName = findViewById(R.id.et_join_name);
        btnConfirm = findViewById(R.id.btn_join_confrim);
        btnCancel = findViewById(R.id.btn_join_cancel);
        retrofit =new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etEmail = findViewById(R.id.et_join_email);
                EditText etPassword = findViewById(R.id.et_join_password);
                EditText etName = findViewById(R.id.et_join_name);
                JsonObject json = new JsonObject();
                json.addProperty("email", etEmail.getText().toString());
                json.addProperty("password", etPassword.getText().toString());
                json.addProperty("name", etName.getText().toString());
                retrofit.create(AuthApi.class).join(json).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context,response.message().toString(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"알 수 없는 에러입니다. 개발자에게 문의하세요.",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context,"회원가입 실패",Toast.LENGTH_SHORT).show();
                        Log.d("JOIN FAILURE", "onFailure: "+t.getMessage());
                    }
                });

                dismiss();
            }
        }) ;
        btnCancel.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}

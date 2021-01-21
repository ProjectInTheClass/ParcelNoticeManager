package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.NoticeData;
import com.eos.parcelnoticemanager.data.ResponseData;
import com.eos.parcelnoticemanager.retrofit.DormitoryApi;
import com.eos.parcelnoticemanager.retrofit.NoticeApi;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoticeActivity extends AppCompatActivity {

    private Button btn_save;
    private EditText et_notice;
    private NoticeApi noticeApi;
    private SharedPreferences pref;
    private NoticeData notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        init();

/*        et_notice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_notice.setText(notice.getContents());
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                et_notice.setText(editable.toString());
            }
        });*/


    }

    private void saveNotice() {
        if(notice!=null) {
            Call<ResponseData> call = noticeApi.delete_notice(pref.getString("token", ""), notice.getId());
            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {}
                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {}
            });
        }
        JsonObject json = new JsonObject();
        json.addProperty("contents",et_notice.getText().toString());
        Call<ResponseData> callAdd = noticeApi.add_notice(pref.getString("token",""),json);
        callAdd.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Toast.makeText(NoticeActivity.this,"저장되었습니다.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {  }
        });
    }

    private void init() {
        et_notice = (EditText)findViewById(R.id.editText_notice);
        btn_save = findViewById(R.id.button_save);
        pref = getSharedPreferences("token",0);

        noticeApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NoticeApi.class);

        Call<List<NoticeData>> callNotice = noticeApi.get_notice(pref.getString("token",""));
        callNotice.enqueue(new Callback<List<NoticeData>>() {
            @Override
            public void onResponse(Call<List<NoticeData>> call, Response<List<NoticeData>> response) {
                if(response.body().size()!=0) {
                    notice = response.body().get(0);
                    et_notice.setText(notice.getContents());
                }
            }
            @Override
            public void onFailure(Call<List<NoticeData>> call, Throwable t) {  }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotice();
            }
        });
    }
}
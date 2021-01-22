package com.eos.parcelnoticemanager.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.RoomAdapter;
import com.eos.parcelnoticemanager.data.ResponseData;
import com.eos.parcelnoticemanager.data.RewardData;
import com.eos.parcelnoticemanager.data.UserData;
import com.eos.parcelnoticemanager.retrofit.PointApi;
import com.eos.parcelnoticemanager.retrofit.RoomApi;
import com.eos.parcelnoticemanager.retrofit.SagamApi;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eos.parcelnoticemanager.tools.RoomActivity.getToken;

public class RewardActivity extends AppCompatActivity {

    private EditText ed_name;
    private EditText ed_score;
    private EditText ed_reason;
    private TextView tv_score;
    private TextView tv_reason;
    private Button btn_save_reward;
    private Button btn_plus, btn_minus;
    private Button btn_nosave_reward;
    boolean isPlus = true;
    SagamApi sagamApi;
    PointApi pointApi;
    ArrayList<String> list = new ArrayList<>();
    public static String type;
    static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        ed_score = (EditText)findViewById(R.id.editText_score);
        ed_reason = (EditText)findViewById(R.id.editText_reason);

        tv_score = findViewById(R.id.textView_score);
        tv_reason = findViewById(R.id.textView_reason);

        btn_plus = (Button)findViewById(R.id.button_plusPoint);
        btn_minus = (Button)findViewById(R.id.button_minusPoint);

        btn_save_reward = (Button)findViewById(R.id.button_save_reward);
        btn_nosave_reward = (Button)findViewById(R.id.button_nosave_reward);
        pref = getSharedPreferences("token",0);
        list = new ArrayList<String>();
        Context context = RewardActivity.this;
        settingList(context);
    }



    private void settingList(final Context context) {
        sagamApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SagamApi.class);

        pointApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PointApi.class);

        Call<List<UserData>> callGetUser = sagamApi.getUsers(getToken());
        Callback<List<UserData>> callback = new Callback<List<UserData>>() {
            @Override
            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                final ArrayList<UserData> userList = new ArrayList<>(response.body());

                for (int i = 0; i < userList.size(); i++) {
                    list.add(userList.get(i).getName() + " (" + userList.get(i).getPhoneNum() + ")");
                }
                final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_reward);
                autoCompleteTextView.setAdapter(new ArrayAdapter<String>(context,
                        android.R.layout.simple_dropdown_item_1line, list));

                //상점 버튼 눌렀으면 상점 type으로 바꿔주기
                btn_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        type = "상점";
                        makeVisible();
                    }
                });
                //벌점 버튼 눌렀으면 벌점 type으로 바꿔주기
                btn_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        type = "벌점";
                        makeVisible();
                    }
                });

                btn_save_reward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String who = autoCompleteTextView.getText().toString();
                        int index;
                        for (index = 0; index < list.size(); index++) {
                            if (list.get(index).equals(who)) break;
                        }
                        int user = userList.get(index).getId();
                        int amount = Integer.parseInt(ed_score.getText().toString());
                        String reason = ed_reason.getText().toString();

                        JsonObject json = new JsonObject();
                        json.addProperty("user", user);
                        json.addProperty("amount", amount);
                        json.addProperty("type", type);
                        json.addProperty("reason", reason);

                        Button btn_plus = (Button) findViewById(R.id.button_plusPoint);
                        Button btn_minus = (Button) findViewById(R.id.button_minusPoint);

                        Call<ResponseData> callAddReward = pointApi.add_point(getToken(), json);
                        Callback<ResponseData> callback = new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                Toast.makeText(RewardActivity.this, "상벌점 등록이 완료 되었습니다.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                Toast.makeText(RewardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        };
                        callAddReward.enqueue(callback);

                    }
                });

                btn_nosave_reward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RewardActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                Toast.makeText(RewardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        callGetUser.enqueue(callback);
    }
    private String getToken(){
        return pref.getString("token","");
    }

    private void makeVisible(){
        tv_reason.setVisibility(View.VISIBLE);
        tv_score.setVisibility(View.VISIBLE);
        btn_nosave_reward.setVisibility(View.VISIBLE);
        btn_save_reward.setVisibility(View.VISIBLE);
        ed_score.setVisibility(View.VISIBLE);
        ed_reason.setVisibility(View.VISIBLE);
    }
}
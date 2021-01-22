package com.eos.parcelnoticemanager.tools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.RoomAdapter;
import com.eos.parcelnoticemanager.data.DormitoryData;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.data.ResponseData;
import com.eos.parcelnoticemanager.data.StudnetInRoomData;
import com.eos.parcelnoticemanager.data.UserData;
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

public class PlusStudentActivity extends AppCompatActivity {
    private ArrayList<String> list; //자동완성될 데이터
    private SagamApi sagamApi;
    private RoomApi roomApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        list = new ArrayList<String>();
        Context context = PlusStudentActivity.this;
        settingList(context);
    }

    private void settingList(final Context context) {
        sagamApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SagamApi.class);

        roomApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RoomApi.class);

        Call<List<UserData>> callGetUser = sagamApi.getUsers(getToken());
        Callback<List<UserData>> callback = new Callback<List<UserData>>() {
            @Override
            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                final ArrayList<UserData> userList = new ArrayList<>(response.body());

                for (int i = 0; i < userList.size(); i++) {
                    list.add(userList.get(i).getName() + " (" + userList.get(i).getPhoneNum() + ")");
                }
                final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
                autoCompleteTextView.setAdapter(new ArrayAdapter<String>(context,
                        android.R.layout.simple_dropdown_item_1line, list));


                Button btn_yes = (Button)findViewById(R.id.button_register_yes);
                Button btn_no = (Button)findViewById(R.id.button_register_no);

                btn_yes.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        String who = autoCompleteTextView.getText().toString();
                        int index;
                        for(index = 0; index < list.size(); index++){
                            if(list.get(0).equals(who)) break;
                        }
                        int id = userList.get(index).getId();
                        JsonObject json = new JsonObject();
                        json.addProperty("roomId", RoomAdapter.whichId);
                        json.addProperty("userId", id);

                        //호실 update 정보를 서버에 반영합니다.
                        Call<ResponseData> callUpdateRoom = roomApi.update_user(getToken(),json);
                        Callback<ResponseData> callback = new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                Toast.makeText(context,"등록이 완료되었습니다.",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        };
                        callUpdateRoom.enqueue(callback);

                    }
                });
                btn_no.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, RoomActivity.class);
                        startActivity(intent);
                    }
                });





            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                Toast.makeText(PlusStudentActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        callGetUser.enqueue(callback);
    }
}

package com.eos.parcelnoticemanager.tools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        list = new ArrayList<String>();
        settingList();

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list));

        setContentView(R.layout.activity_register_student);
        Log.d("roomId", Integer.toString(RoomAdapter.whichId));
    }

    private void settingList() {
        sagamApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SagamApi.class);

        Call<List<UserData>> callGetUser = sagamApi.getUsers(getToken());
        Callback<List<UserData>> callback = new Callback<List<UserData>>() {
            @Override
            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                ArrayList<UserData> userList = new ArrayList<>(response.body());

                for (int i = 0; i < userList.size(); i++) {
                    list.add(userList.get(i).getName() + " (" + userList.get(i).getPhoneNum() + ")");
                }

            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                Toast.makeText(PlusStudentActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
}

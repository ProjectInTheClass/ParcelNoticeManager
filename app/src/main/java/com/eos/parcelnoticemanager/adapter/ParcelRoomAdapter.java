package com.eos.parcelnoticemanager.adapter;

import android.bluetooth.BluetoothHidDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.custom_dialog.ParcelStudentListDialog;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.StudnetInRoomData;
import com.eos.parcelnoticemanager.retrofit.RoomApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParcelRoomAdapter extends RecyclerView.Adapter<ParcelRoomAdapter.CustomViewHolder> {
    private Context context;
    private List<RoomData> rooms;
    private LayoutInflater inflater;
    private Callback<List<RoomData>> retrofitCallback;
    private Call<List<RoomData>> callGetRooms;
    private RoomApi roomApi;


    public ParcelRoomAdapter(Context context) {
        initCallback();
        initRetrofit();
        callGetRooms.enqueue(retrofitCallback);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.parcel_room, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        RoomData room = rooms.get(position);
        holder.tvRoom.setText(String.valueOf(room.roomNum));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRoom;


        public CustomViewHolder(View itemView) {
            super(itemView);
            tvRoom = (TextView) itemView.findViewById(R.id.parcel_textView_room);

            tvRoom.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION) {
                        ParcelStudentListDialog parcelStudentListDialog = new ParcelStudentListDialog(context,rooms.get(pos));
                        parcelStudentListDialog.setCanceledOnTouchOutside(true);
                        parcelStudentListDialog.setCancelable(true);
                        parcelStudentListDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                        parcelStudentListDialog.show();
                    }
                }
            });

        }
    }
    void initRetrofit(){
        roomApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RoomApi.class);

        callGetRooms = roomApi.get_rooms();
    }

    void initCallback(){
        retrofitCallback = new Callback<List<RoomData>>() {
            @Override
            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
                rooms = response.body();
            }

            @Override
            public void onFailure(Call<List<RoomData>> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        };

    }

    private String getString(int base_url) {
        return getString(base_url);

    }
}

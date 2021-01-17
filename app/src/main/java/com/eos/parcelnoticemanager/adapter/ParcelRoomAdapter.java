package com.eos.parcelnoticemanager.adapter;

import android.bluetooth.BluetoothHidDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.custom_dialog.ParcelDetailDialog;
import com.eos.parcelnoticemanager.custom_dialog.ParcelStudentListDialog;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.StudnetInRoomData;
import com.eos.parcelnoticemanager.data.UserData;
import com.eos.parcelnoticemanager.retrofit.RoomApi;
import com.eos.parcelnoticemanager.tools.OnFloorItemClickListener;
import com.eos.parcelnoticemanager.tools.ParcelRegisterActivity;
import com.google.gson.JsonObject;
import com.kakao.usermgmt.response.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParcelRoomAdapter extends RecyclerView.Adapter<ParcelRoomAdapter.CustomViewHolder>{
    private Context context;
    private List<RoomData> rooms;
    private LayoutInflater inflater;
    private int floor;



    public ParcelRoomAdapter(Context context, int floor, List<RoomData> rooms) {
        this.floor = floor;
        this.context = context;
        this.rooms = rooms;
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
        holder.tvRoom.setText(String.valueOf(room.getRoomNum()));
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRoom;

        public CustomViewHolder(final View itemView) {
            super(itemView);
            tvRoom = (TextView) itemView.findViewById(R.id.parcel_textView_room);

            tvRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                    {
                        ParcelDetailDialog.setDormitory(rooms.get(position).getDormitory());
                        ParcelDetailDialog.setRoomId(rooms.get(position).getId());
                        ParcelRegisterActivity.showDialog(rooms.get(position));
                    }
                }
            });
        }
    }
}

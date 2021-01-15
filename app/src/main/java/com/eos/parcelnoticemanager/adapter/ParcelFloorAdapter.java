package com.eos.parcelnoticemanager.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.UserData;
import com.eos.parcelnoticemanager.retrofit.RoomApi;
import com.eos.parcelnoticemanager.tools.OnFloorItemClickListener;
import com.eos.parcelnoticemanager.tools.ParcelRegisterActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParcelFloorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static public ArrayList<Integer> floors;
    static private Context context;
    private LayoutInflater layoutInflater;

    public ParcelFloorAdapter(Context context, ArrayList<Integer> floors){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.floors = floors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.parcel_floor, parent, false);
        return new ParcelFloorAdapter.GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Call<List<RoomData>> callGetRooms =new Retrofit.Builder()
                .baseUrl(ParcelRegisterActivity.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RoomApi.class)
                .getRooms_byFloor(ParcelRegisterActivity.getToken(),floors.get(position));

        callGetRooms.enqueue(new Callback<List<RoomData>>() {
            @Override
            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
                ParcelRoomAdapter adapter = new ParcelRoomAdapter(context,floors.get(position),response.body());
                ((GridViewHolder)holder).rvRoom.setAdapter(adapter);
                ((GridViewHolder)holder).rvRoom.setLayoutManager(new GridLayoutManager(context, 5));
                ((GridViewHolder)holder).rvRoom.setHasFixedSize(true);
                ((ParcelFloorAdapter.GridViewHolder)holder).tvFloorNum.setText(String.valueOf(floors.get(position))+"층");
            }

            @Override
            public void onFailure(Call<List<RoomData>> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return floors.size();
    }


    public class GridViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvRoom;
        TextView tvFloorNum;

        public GridViewHolder(View itemView) {
            super(itemView);
            rvRoom = itemView.findViewById(R.id.parcel_recyclerView_room);
            tvFloorNum = itemView.findViewById(R.id.parcel_textView_floor);
        }
    }



}

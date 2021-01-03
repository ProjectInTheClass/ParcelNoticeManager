package com.eos.parcelnoticemanager.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.DormitoryData;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.retrofit.DormitoryApi;
import com.eos.parcelnoticemanager.retrofit.ParcelApi;
import com.eos.parcelnoticemanager.tools.OnFloorItemClickListener;
import com.eos.parcelnoticemanager.tools.ParcelRegisterActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParcelFloorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnFloorItemClickListener {

    static public ArrayList<Integer> floors;
    private Context context;
    private LayoutInflater layoutInflater;
    private DormitoryApi dormitoryApi;
    private DormitoryData dormitoryData;
    private Callback<DormitoryData> retrofitCallback;
    private Call<DormitoryData> callGetDormitory;

    public ParcelFloorAdapter(Context context){
        initRetrofit();
        initCallback();
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        floors = new ArrayList<>();
        for(int i=0; i<dormitoryData.getStory(); i++){
            floors.add(i+1);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.parcel_floor, parent, false);
        return new ParcelFloorAdapter.GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((GridViewHolder)holder).rvRoom.setAdapter(new ParcelRoomAdapter(context,floors.get(position)));
        ((GridViewHolder)holder).rvRoom.setLayoutManager(new GridLayoutManager(context, 5));
        ((GridViewHolder)holder).rvRoom.setHasFixedSize(true);
        ((ParcelFloorAdapter.GridViewHolder)holder).tvFloorNum.setText(String.valueOf(floors.get(position))+"층");
    }

    @Override
    public int getItemCount() {
        return floors.size();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
    }

    private void initRetrofit() {
        dormitoryApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DormitoryApi.class);
        callGetDormitory = dormitoryApi.get_dormitory(ParcelRegisterActivity.getToken());
    }

    private String getString(int base_url) {
        return getString(base_url);
    }

    void initCallback(){
        retrofitCallback = new Callback<DormitoryData>() {
            @Override
            public void onResponse(Call<DormitoryData> call, Response<DormitoryData> response) {
                dormitoryData = response.body();
            }

            @Override
            public void onFailure(Call<DormitoryData> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        };

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

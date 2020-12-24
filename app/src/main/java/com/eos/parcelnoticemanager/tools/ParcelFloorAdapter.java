package com.eos.parcelnoticemanager.tools;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.FloorData;

import java.util.ArrayList;

public class ParcelFloorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnFloorItemClickListener  {

    static public ArrayList<FloorData> floors;
    private Context context;
    private LayoutInflater layoutInflater;

    ParcelFloorAdapter(ArrayList<FloorData> floors, Context context){
        this.floors = floors;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.parcel_floor, parent, false);
        return new ParcelFloorAdapter.GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((GridViewHolder)holder).rvRoom.setAdapter(new ParcelRoomAdapter(context, floors.get(position).rooms));
        ((GridViewHolder)holder).rvRoom.setLayoutManager(new GridLayoutManager(context, 5));
        ((GridViewHolder)holder).rvRoom.setHasFixedSize(true);
        ((ParcelFloorAdapter.GridViewHolder)holder).tvFloorNum.setText(String.valueOf(floors.get(position).floorNum)+"층");
    }

    @Override
    public int getItemCount() {
        return floors.size();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
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

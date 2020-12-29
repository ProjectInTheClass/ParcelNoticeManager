package com.eos.parcelnoticemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.tools.OnFloorItemClickListener;

import java.util.ArrayList;


public class FloorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnFloorItemClickListener {

    OnFloorItemClickListener listener;
    static public ArrayList<FloorData> floors;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener = null;

    public FloorAdapter(ArrayList<FloorData> floors, Context context) {
        this.floors = floors;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.signle_floor, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GridViewHolder)holder).recyclerView.setAdapter(new RoomAdapter(context, floors.get(position).rooms));
        ((GridViewHolder)holder).recyclerView.setLayoutManager(new GridLayoutManager(context, 5));
        ((GridViewHolder)holder).recyclerView.setHasFixedSize(true);
        ((GridViewHolder)holder).tvFloorNum.setText(String.valueOf(floors.get(position).floorNum));
    }

    @Override
    public int getItemCount() {
        return floors.size();
    }

    @Override public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return floors.get(position).id;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView tvFloorNum;
        Button btnPlusRoom;

        public GridViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rvRooms);
            tvFloorNum = itemView.findViewById(R.id.tvFloorNum);
            btnPlusRoom = (Button)itemView.findViewById(R.id.btnPlusRoom);
            btnPlusRoom.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        if(mListener != null){
                            mListener.onItemClick(v, pos);
                        }

                    }
                }

            });
        }
    }
}
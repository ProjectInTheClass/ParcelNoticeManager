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
import com.eos.parcelnoticemanager.data.WasherFloorData;
import com.eos.parcelnoticemanager.tools.OnWasherFloorItemClickListener;

import java.util.ArrayList;


public class WasherFloorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnWasherFloorItemClickListener {

    OnWasherFloorItemClickListener listener;
    static public ArrayList<WasherFloorData> washerFloors;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener = null;

    public WasherFloorAdapter(ArrayList<WasherFloorData> washerFloors, Context context) {
        this.washerFloors = washerFloors;
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
        View view = layoutInflater.inflate(R.layout.single_washer_floor, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GridViewHolder)holder).recyclerView.setAdapter(new WasherAdpater(context,washerFloors.get(position).washers));
        ((GridViewHolder)holder).recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        ((GridViewHolder)holder).recyclerView.setHasFixedSize(true);
        if(washerFloors.get(position).isWasher == true) {
            ((GridViewHolder) holder).tvWasherFloorNum.setText(String.valueOf(washerFloors.get(position).floorNum)+"층 세탁기");
        }
        else{
            ((GridViewHolder) holder).tvWasherFloorNum.setText(String.valueOf(washerFloors.get(position).floorNum)+"층 건조기");
        }
    }

    @Override
    public int getItemCount() {
        return washerFloors.size();
    }

    @Override public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return washerFloors.get(position).id;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView tvWasherFloorNum;
        Button btnPlusWasher;

        public GridViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rvWasher);
            tvWasherFloorNum = itemView.findViewById(R.id.tvWasherFloorNum);
            btnPlusWasher = (Button)itemView.findViewById(R.id.btnPlusWahser);
            btnPlusWasher.setOnClickListener(new View.OnClickListener(){
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
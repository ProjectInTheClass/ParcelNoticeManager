package com.eos.parcelnoticemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.FloorData;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.retrofit.RoomApi;
import com.eos.parcelnoticemanager.tools.OnFloorItemClickListener;
import com.eos.parcelnoticemanager.tools.ParcelRegisterActivity;
import com.eos.parcelnoticemanager.tools.RoomActivity;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FloorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnFloorItemClickListener {

    OnFloorItemClickListener listener;
    static public ArrayList<FloorData> floors;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener = null;
    RoomApi roomApi;

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
                    roomApi = new Retrofit.Builder()
                            .baseUrl(String.valueOf(R.string.base_url))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(RoomApi.class);

                    JsonObject jsonObject = new JsonObject();

                    Call




                    for(i = 0; i<totalFloor; i++){
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("floor",i+1);
                        Call<ArrayList<RoomData>> callRoomListByFloor = roomApi.getRooms_byFloor(ParcelRegisterActivity.getToken(), jsonObject);
                        Callback<ArrayList<RoomData>> callback = new Callback<ArrayList<RoomData>>() {
                            @Override
                            public void onResponse(Call<ArrayList<RoomData>> call, Response<ArrayList<RoomData>> response) {
                                globalfloors.get(i).setRooms(response.body());
                            }
                            @Override
                            public void onFailure(Call<ArrayList<RoomData>> call, Throwable t) {
                                Toast.makeText(RoomActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        };
                        callRoomListByFloor.enqueue(callback);
                    }
                }

            });
        }
    }
}
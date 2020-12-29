package com.eos.parcelnoticemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.custom_dialog.RoomDialog;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.StudnetInRoomData;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.CustomViewHolder> {
    private Context context;
    private ArrayList<RoomData> rooms;
    public ArrayList<StudnetInRoomData> students;
    private LayoutInflater inflater;

    public RoomAdapter(Context context, ArrayList<RoomData> rooms) {
        this.context = context;
        this.rooms = rooms;
        this.inflater = LayoutInflater.from(context);
        students = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.single_room, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        RoomData room = rooms.get(position);
        holder.tvRoomNum.setText(String.valueOf(room.roomNum));
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRoomNum;
        public TextView tvRoomTitle;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tvRoomNum = (TextView) itemView.findViewById(R.id.tvRoomNumber);
            tvRoomTitle = (TextView) itemView.findViewById(R.id.tv_room_title);

            tvRoomNum.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    RoomDialog roomDialog = new RoomDialog(context);
                    roomDialog.setCanceledOnTouchOutside(true);
                    roomDialog.setCancelable(true);
                    roomDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    roomDialog.show();
                }
            });

        }
    }
}
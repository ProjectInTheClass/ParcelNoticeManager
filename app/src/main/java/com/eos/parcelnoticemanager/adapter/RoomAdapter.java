package com.eos.parcelnoticemanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.StudnetInRoomData;
import com.eos.parcelnoticemanager.tools.PlusStudentActivity;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.CustomViewHolder> {
    private Context context;
    private ArrayList<RoomData> rooms;
    public ArrayList<StudnetInRoomData> students = new ArrayList<>();
    private LayoutInflater inflater;
    private RoomData room;
    View view;

    public RoomAdapter(Context context, ArrayList<RoomData> rooms) {
        this.context = context;
        this.rooms = rooms;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.single_room, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        room = rooms.get(position);
        holder.tvRoomNum.setText(String.valueOf(room.roomNum));
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRoomNum;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tvRoomNum = itemView.findViewById(R.id.tvRoomNumber);

            tvRoomNum.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), PlusStudentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("studentList", room.students);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });

        }
    }
}
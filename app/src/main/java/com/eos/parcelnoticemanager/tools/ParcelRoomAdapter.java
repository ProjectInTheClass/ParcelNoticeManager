package com.eos.parcelnoticemanager.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.custom_dialog.ParcelStudentListDialog;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.StudnetInRoomData;

import java.util.ArrayList;

public class ParcelRoomAdapter extends RecyclerView.Adapter<ParcelRoomAdapter.CustomViewHolder> {
    private Context context;
    private ArrayList<RoomData> rooms;
    public ArrayList<StudnetInRoomData> students;
    private LayoutInflater inflater;

    public ParcelRoomAdapter(Context context, ArrayList<RoomData> rooms) {
        this.context = context;
        this.rooms = rooms;
        this.inflater = LayoutInflater.from(context);
        students = new ArrayList<>();
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
}

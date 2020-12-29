package com.eos.parcelnoticemanager.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.adapter.ParcelRoomAdapter;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.UserData;

import java.util.ArrayList;
import java.util.List;


public class ParcelStudentListDialog extends Dialog {

    private Context context;
    private RoomData room;
    private RecyclerView rvStudent;
    private List<UserData> users;

    public ParcelStudentListDialog(@NonNull Context context, RoomData room) {
        super(context);
        this.context = context;
        this.room = room;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcel_receiver_dialog);

        users = ParcelRoomAdapter.getUsers(room.getRoomNum());

        rvStudent = findViewById(R.id.parcel_recyclerView_students);
        StudentAdapter adapter = new StudentAdapter(users);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rvStudent.setLayoutManager(manager);
        rvStudent.setAdapter(adapter);

    }

    public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.CustomViewHolder> {

        List<UserData> students;
        private LayoutInflater inflater;

        StudentAdapter(List<UserData> students){
            this.students = students;
            this.inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = inflater.inflate(R.layout.item_parcel_dialog_student, parent, false);
            return new StudentAdapter.CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            UserData student = students.get(position);
            if(student.getName().equals("기타(직접입력)")){
                holder.tvStudentName.setTextSize(16);
            }
            holder.tvStudentName.setText(student.getName());
        }

        @Override
        public int getItemCount() {
            return students.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            public TextView tvStudentName;

            public CustomViewHolder(final View itemView) {
                super(itemView);
                tvStudentName = (TextView)itemView.findViewById(R.id.parcel_textView_studentName);

                tvStudentName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tvStudentName.getText().toString().equals("기타(직접입력)")){
                            ParcelDetailDialog.setReceiver(-1,null);
                        }
                        else ParcelDetailDialog.setReceiver(students.get(getAdapterPosition()).getId(),tvStudentName.getText().toString());

                        ParcelDetailDialog parcelDetailDialog = new ParcelDetailDialog(context);
                        parcelDetailDialog.setCanceledOnTouchOutside(true);
                        parcelDetailDialog.setCancelable(true);
                        parcelDetailDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                        parcelDetailDialog.show();
                        dismiss();
                    }
                });
            }
        }
    }
}

package com.eos.parcelnoticemanager.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.RoomData;
import com.eos.parcelnoticemanager.data.UserData;
import com.eos.parcelnoticemanager.retrofit.RoomApi;
import com.eos.parcelnoticemanager.tools.ParcelRegisterActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ParcelStudentListDialog extends Dialog {

    private Context context;
    private RoomData room;
    private RecyclerView rvStudent;

    public ParcelStudentListDialog(@NonNull Context context, RoomData room) {
        super(context);
        this.context = context;
        this.room = room;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcel_receiver_dialog);

        rvStudent = findViewById(R.id.parcel_recyclerView_students);
        init();
    }
    public void init(){
        Call<List<UserData>> callGetUsers = new Retrofit.Builder()
                .baseUrl(ParcelRegisterActivity.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RoomApi.class)
                .get_users(ParcelRegisterActivity.getToken(),room.getId());

        callGetUsers.enqueue(new Callback<List<UserData>>() {
            @Override
            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                /*List<UserData> users = response.body();
                users.add(new UserData("기타(직접입력)"));
                UserAdapter adapter = new UserAdapter(users,context);*/
                UserAdapter adapter = new UserAdapter(response.body(),context);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                rvStudent.setLayoutManager(manager);
                rvStudent.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> {

        List<UserData> users;
        private LayoutInflater inflater;

        public UserAdapter(List<UserData> users, Context context){
            this.users = users;
            this.inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = inflater.inflate(R.layout.item_parcel_dialog_student, parent, false);
            return new UserAdapter.CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            UserData student = users.get(position);
            /*if(student.getName().equals("기타(직접입력)")){
                holder.tvStudentName.setTextSize(16);
            }*/
            holder.tvStudentName.setText(student.getName());
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            public TextView tvStudentName;

            public CustomViewHolder(final View itemView) {
                super(itemView);
                tvStudentName = (TextView)itemView.findViewById(R.id.parcel_textView_studentName);

                tvStudentName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*if(tvStudentName.getText().toString().equals("기타(직접입력)")){
                            ParcelDetailDialog.setReceiver(-1,null);
                        }*/
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            ParcelDetailDialog.setReceiver(users.get(position).getId(), tvStudentName.getText().toString());
                            ParcelRegisterActivity.showDetailDialog();
                            dismiss();
                        }
                    }
                });
            }
        }
    }
}

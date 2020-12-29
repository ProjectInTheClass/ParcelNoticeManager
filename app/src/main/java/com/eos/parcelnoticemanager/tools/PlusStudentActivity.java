package com.eos.parcelnoticemanager.tools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.data.StudnetInRoomData;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlusStudentActivity extends AppCompatActivity {
    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();
    ArrayList<StudnetInRoomData> students = (ArrayList< StudnetInRoomData>)bundle.getSerializable("studentList");

    public class PlusStudentAdapter extends RecyclerView.Adapter<PlusStudentAdapter.ViewHolder> {
        ArrayList<StudnetInRoomData> students = new ArrayList<>();

        @NonNull
        @Override
        public PlusStudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_student, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PlusStudentAdapter.ViewHolder viewHolder, int position) {

            StudnetInRoomData item = students.get(position);
            viewHolder.tvStuName.setText(item.getStudentName());
            viewHolder.tvStuNumber.setText(item.getStudentNum());
        }

        @Override
        public int getItemCount() {
            return students.size();
        }

        public void setItems(ArrayList<StudnetInRoomData> students) {
            this.students = students;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvStuName;
            TextView tvStuNumber;

            ViewHolder(View itemView) {
                super(itemView);

                tvStuName = itemView.findViewById(R.id.et_singleRoom_student);
                tvStuNumber = itemView.findViewById(R.id.et_room_studentNumber);
            }
        }
    }

    private PlusStudentAdapter adapter = new PlusStudentAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recycleView 초기화
        RecyclerView recyclerView = findViewById(R.id.rv_exist_student);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //아이템 로드
        adapter.setItems(students);

    }

}

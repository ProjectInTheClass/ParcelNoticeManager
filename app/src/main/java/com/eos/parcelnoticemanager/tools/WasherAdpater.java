package com.eos.parcelnoticemanager.tools;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnoticemanager.R;

import com.eos.parcelnoticemanager.data.WasherData;

import java.util.ArrayList;

public class WasherAdpater extends RecyclerView.Adapter<WasherAdpater.CustomViewHolder> {
    private Context context;
    private ArrayList<WasherData> washers;
    private LayoutInflater inflater;

    public WasherAdpater(Context context, ArrayList<WasherData> washers) {
        this.context = context;
        this.washers = washers;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.single_washer, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        WasherData washer = washers.get(position);
        holder.tvWasherNum.setText(String.valueOf(washer.washerNum)+"번");
    }


    @Override
    public int getItemCount() {
        return washers.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWasherNum;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tvWasherNum = (TextView) itemView.findViewById(R.id.tvWasherNumber);


        }
    }
}

package com.eos.parcelnoticemanager.tools;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface OnWasherFloorItemClickListener {
    public void onItemClick(RecyclerView.ViewHolder holder, View view, int position);
}
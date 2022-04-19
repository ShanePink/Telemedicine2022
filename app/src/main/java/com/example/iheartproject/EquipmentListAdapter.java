package com.example.iheartproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EquipmentListAdapter extends RecyclerView.Adapter<EquipmentListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<EquipmentItem> equipmentList = new ArrayList<>();

    public EquipmentListAdapter() {

    }

    public EquipmentListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Build a recycler view of search result in table format
        View view = LayoutInflater.from(context).inflate(R.layout.activity_view_inventory, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Put result into recycler view table
        viewHolder.modelNumResultTv.setText(equipmentList.get(i).getModel());
        viewHolder.qtyResultTv.setText(String.valueOf(equipmentList.get(i).getQty()));
    }

    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    public void setEquipment(ArrayList<EquipmentItem> equipmentList) {
        // Insert filteredEquipmentArray as recycler view input
        this.equipmentList = equipmentList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView modelNumResultTv;
        private TextView qtyResultTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelNumResultTv = itemView.findViewById(R.id.modelNumResult);
            qtyResultTv = itemView.findViewById(R.id.qtyResult);
        }
    }
}
package com.example.iotagro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.MyViewHolder>{

    Context context;
    List<Plant> plantArrayList;

    public PlantAdapter(Context context, List<Plant> plantArrayList) {
        this.context = context;
        this.plantArrayList = plantArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.info_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (plantArrayList!=null && plantArrayList.size()>0) {
            Plant plant = plantArrayList.get(position);
            holder.plant_name.setText(plant.getName());
            holder.Min_T.setText(plant.getTempMin());
            holder.Max_T.setText(plant.getTempMax());
            holder.Min_H.setText(plant.getHumMin());
            holder.Max_H.setText(plant.getHumMax());
            holder.Min_M.setText(plant.getMoistMin());
            holder.Max_M.setText(plant.getMoistMax());
        }
        else {return;}

    }

    @Override
    public int getItemCount() {
        return plantArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView plant_name,Min_T,Max_T,Min_H,Max_H,Min_M,Max_M;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            plant_name=itemView.findViewById(R.id.plant_name);
            Min_T=itemView.findViewById(R.id.Min_T);
            Max_T=itemView.findViewById(R.id.Max_T);
            Min_H=itemView.findViewById(R.id.Min_H);
            Max_H=itemView.findViewById(R.id.Max_H);
            Min_M=itemView.findViewById(R.id.Min_M);
            Max_M=itemView.findViewById(R.id.Max_M);
        }
    }

}
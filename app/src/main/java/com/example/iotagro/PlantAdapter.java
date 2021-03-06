package com.example.iotagro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


public class PlantAdapter extends FirebaseRecyclerAdapter<Plant, PlantAdapter.ViewHolder> {
    FirebaseDatabase mDatabase;
    DatabaseReference ref;
    int t1,t2,h1,h2,m1,m2;
    Context context;
    private int lastPosition = -1;
    int row_index = -1;

    public PlantAdapter(@NonNull FirebaseRecyclerOptions<Plant> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int i, @NonNull Plant plant) {
        holder.plant_name.setText(plant.getPlant());
        holder.Min_T.setText(plant.getMin_temp());
        holder.Max_T.setText(plant.getMax_temp());
        holder.Min_H.setText(plant.getMin_hum());
        holder.Max_H.setText(plant.getMax_hum());
        holder.Min_M.setText(plant.getMin_moist());
        holder.Max_M.setText(plant.getMax_moist());


        holder.edit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogplus = DialogPlus.newDialog(context)
                        .setCancelable(true)
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialogupdate))
                        .create();

                View updateView=dialogplus.getHolderView();

                final EditText plant_name=updateView.findViewById(R.id.uedit_plantName);
                final NumberPicker Min_T=updateView.findViewById(R.id.unpTempMin);
                final NumberPicker Max_T=updateView.findViewById(R.id.unpTempMax);
                final NumberPicker Min_H=updateView.findViewById(R.id.unpHumMin);
                final NumberPicker Max_H=updateView.findViewById(R.id.unpHumMax);
                final NumberPicker Min_M=updateView.findViewById(R.id.unpMoistMin);
                final NumberPicker Max_M=updateView.findViewById(R.id.unpMoistMax);
                Button update=updateView.findViewById(R.id.updateBtn);

                Min_T.setMinValue(0);
                Min_T.setMaxValue(100);
                Max_T.setMinValue(0);
                Max_T.setMaxValue(100);
                Min_H.setMinValue(0);
                Min_H.setMaxValue(100);
                Max_H.setMinValue(0);
                Max_H.setMaxValue(100);
                Min_M.setMinValue(0);
                Min_M.setMaxValue(100);
                Max_M.setMinValue(0);
                Max_M.setMaxValue(100);

                int t1=Integer.parseInt(plant.getMin_temp());
                int t2=Integer.parseInt(plant.getMax_temp());
                int h1=Integer.parseInt(plant.getMin_hum());
                int h2=Integer.parseInt(plant.getMax_hum());
                int m1=Integer.parseInt(plant.getMin_moist());
                int m2=Integer.parseInt(plant.getMax_moist());

                plant_name.setText(plant.getPlant());
                Min_T.setValue(t1);
                Max_T.setValue(t2);
                Min_H.setValue(h1);
                Max_H.setValue(h2);
                Min_M.setValue(m1);
                Max_M.setValue(m2);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Min_T.getValue()<Max_T.getValue()&&Min_H.getValue()<Max_H.getValue()&&Min_M.getValue()<Max_M.getValue()) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("plant", plant_name.getText().toString());
                            map.put("min_temp", String.valueOf(Min_T.getValue()));
                            map.put("max_temp", String.valueOf(Max_T.getValue()));
                            map.put("min_hum", String.valueOf(Min_H.getValue()));
                            map.put("max_hum", String.valueOf(Max_H.getValue()));
                            map.put("min_moist", String.valueOf(Min_M.getValue()));
                            map.put("max_moist", String.valueOf(Max_M.getValue()));

                            FirebaseDatabase.getInstance().getReference()
                                    .child("PlantInfo")
                                    .child(getRef(i).getKey())
                                    .updateChildren(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            dialogplus.dismiss();
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(context.getApplicationContext(), "Max value must be greater than Min value", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                dialogplus.show();


            }
        });

        int tH,tL,hH,hL,mH,mL;
        tH=Integer.parseInt(holder.Max_T.getText().toString());
        tL=Integer.parseInt(holder.Min_T.getText().toString());
        hH=Integer.parseInt(holder.Max_H.getText().toString());
        hL=Integer.parseInt(holder.Min_H.getText().toString());
        mH=Integer.parseInt(holder.Max_M.getText().toString());
        mL=Integer.parseInt(holder.Min_M.getText().toString());

        holder.delete_Btn.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(holder.plant_name.getContext());
            builder.setTitle("Delete");
            builder.setMessage("Are you sure ???");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("PlantInfo")
                            .child(getRef(i).getKey())
                            .setValue(null);
                }

            });
            builder.setNegativeButton("N0", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            builder.show();
        });
        holder.select_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=i;
                notifyDataSetChanged();
                mDatabase=FirebaseDatabase.getInstance();
                ref=mDatabase.getReference().child("Threshold");


                HashMap<String,Object> map = new HashMap<>();
                map.put("High_Temperature",tH);
                map.put("High_Humidity",hH);
                map.put("High_Soil_Moisture",mH);
                map.put("Low_Temperature",tL);
                map.put("Low_Humidity",hL);
                map.put("Low_Soil_Moisture",mL);
                    ref.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context.getApplicationContext(),"Completed",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context.getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });

            }

        });
        mDatabase=FirebaseDatabase.getInstance();
        ref=mDatabase.getReference().child("Threshold");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                t1 = Integer.parseInt(dataSnapshot.child("Low_Temperature").getValue().toString());
                t2 = Integer.parseInt(dataSnapshot.child("High_Temperature").getValue().toString());
                h1 = Integer.parseInt(dataSnapshot.child("Low_Humidity").getValue().toString());
                h2 = Integer.parseInt(dataSnapshot.child("High_Humidity").getValue().toString());
                m1 = Integer.parseInt(dataSnapshot.child("Low_Soil_Moisture").getValue().toString());
                m2 = Integer.parseInt(dataSnapshot.child("High_Soil_Moisture").getValue().toString());

                if(t1==tL&&t2==tH&&h1==hL&&h2==hH&&m1==mL&&m2==mH){
                    holder.select_Btn.setBackgroundColor(Color.GREEN);
                    holder.select_Btn.setText("SELECTED");
                }else{
                    holder.select_Btn.setBackgroundColor(Color.parseColor("#DFD1D1"));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (row_index==i) {
            holder.select_Btn.setBackgroundColor(Color.GREEN);
            holder.select_Btn.setText("SELECTED");
        } else {
            holder.select_Btn.setBackgroundColor(Color.parseColor("#DFD1D1"));
        }

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_layout, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView plant_name,Min_T,Max_T,Min_H,Max_H,Min_M,Max_M;
        Button delete_Btn,edit_Btn,select_Btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plant_name=itemView.findViewById(R.id.plant_name);
            Min_T=itemView.findViewById(R.id.Min_T);
            Max_T=itemView.findViewById(R.id.Max_T);
            Min_H=itemView.findViewById(R.id.Min_H);
            Max_H=itemView.findViewById(R.id.Max_H);
            Min_M=itemView.findViewById(R.id.Min_M);
            Max_M=itemView.findViewById(R.id.Max_M);
            delete_Btn=itemView.findViewById(R.id.delete_Btn);
            edit_Btn=itemView.findViewById(R.id.edit_Btn);
            select_Btn=itemView.findViewById(R.id.select_Btn);


        }
    }
}
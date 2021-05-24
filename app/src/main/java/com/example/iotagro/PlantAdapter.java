package com.example.iotagro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class PlantAdapter extends FirebaseRecyclerAdapter<Plant, PlantAdapter.ViewHolder> {
    Context context;

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

                Min_T.setMaxValue(0);
                Min_T.setMaxValue(100);
                Max_T.setMaxValue(0);
                Max_T.setMaxValue(100);
                Min_H.setMaxValue(0);
                Min_H.setMaxValue(100);
                Max_H.setMaxValue(0);
                Max_H.setMaxValue(100);
                Min_M.setMaxValue(0);
                Min_M.setMaxValue(100);
                Max_M.setMaxValue(0);
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
                        Map<String, Object> map=new HashMap<>();
                        map.put("plant",plant_name.getText().toString());
                        map.put("min_temp",String.valueOf(Min_T.getValue()));
                        map.put("max_temp",String.valueOf(Max_T.getValue()));
                        map.put("min_hum",String.valueOf(Min_H.getValue()));
                        map.put("max_hum",String.valueOf(Max_H.getValue()));
                        map.put("min_moist",String.valueOf(Min_M.getValue()));
                        map.put("max_moist",String.valueOf(Max_M.getValue()));

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
                });

                dialogplus.show();


            }
        });



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


//        holder.edit_Btn.setOnClickListener(v -> {
//            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.plant_name.getContext())
//                    .setContentHolder(new ViewHolder(R.layout.content))
//                    .setExpanded(true)
//                    .create();
//
//            View updateView=dialogPlus.getHolderView();
//            final EditText plant_name=updateView.findViewById(R.id.uedit_plantName);
//            final EditText Min_T=updateView.findViewById(R.id.unpTempMin);
//            final EditText Max_T=updateView.findViewById(R.id.unpTempMax);
//            final EditText Min_H=updateView.findViewById(R.id.unpHumMin);
//            final EditText Max_H=updateView.findViewById(R.id.unpHumMax);
//            final EditText Min_M=updateView.findViewById(R.id.unpMoistMin);
//            final EditText Max_M=updateView.findViewById(R.id.unpMoistMax);
//            Button update=updateView.findViewById(R.id.updateBtn);
//
//            plant_name.setText(plant.getPlant());
//            Min_T.setText(plant.getMin_temp());
//            Max_T.setText(plant.getMax_temp());
//            Min_H.setText(plant.getMin_hum());
//            Max_H.setText(plant.getMax_hum());
//            Min_M.setText(plant.getMin_moist());
//            Max_M.setText(plant.getMax_moist());
//
//            dialogPlus.show();
//
//
//
//        });
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
        Button delete_Btn,edit_Btn;

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


        }

    }


}
//    setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            FirebaseDatabase.getInstance().getReference()
//                    .child("PlantInfo")
//                    .child(getRef(i).getKey())
//                    .setValue(null)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                        }
//                    });
//        }
//    });

//public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.MyViewHolder>{
//
//    Context context;
//    List<Plant> plantArrayList;
//
//    public PlantAdapter(Context context, List<Plant> plantArrayList) {
//        this.context = context;
//        this.plantArrayList = plantArrayList;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v= LayoutInflater.from(context).inflate(R.layout.info_layout,parent,false);
//        return new MyViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Plant plant = plantArrayList.get(position);
//        holder.plant_name.setText(plant.getName());
//        holder.Min_T.setText(plant.getTempMin());
//        holder.Max_T.setText(plant.getTempMax());
//        holder.Min_H.setText(plant.getHumMin());
//        holder.Max_H.setText(plant.getHumMax());
//        holder.Min_M.setText(plant.getMoistMin());
//        holder.Max_M.setText(plant.getMoistMax());
////        holder.delete_Btn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                FirebaseDatabase.getInstance().getReference()
////                        .child("PlantInfo")
////                        .child(get(position).getKey())
////            }
////        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return plantArrayList.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//        TextView plant_name,Min_T,Max_T,Min_H,Max_H,Min_M,Max_M;
//        Button delete_Btn;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            plant_name=itemView.findViewById(R.id.plant_name);
//            Min_T=itemView.findViewById(R.id.Min_T);
//            Max_T=itemView.findViewById(R.id.Max_T);
//            Min_H=itemView.findViewById(R.id.Min_H);
//            Max_H=itemView.findViewById(R.id.Max_H);
//            Min_M=itemView.findViewById(R.id.Min_M);
//            Max_M=itemView.findViewById(R.id.Max_M);
//            delete_Btn=itemView.findViewById(R.id.delete_Btn);
//        }
//    }
//
//}
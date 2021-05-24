package com.example.iotagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Addplant extends AppCompatActivity {
    Button addBtn,cancelBtn;
    EditText edit_plantName;
    NumberPicker npTempMin,npTempMax,npHumMin,npHumMax,npMoistMin,npMoistMax;

    FirebaseDatabase mDatabase;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplant);

        edit_plantName=findViewById(R.id.edit_plantName);
        npTempMin=findViewById(R.id.npTempMin);
        npTempMax=findViewById(R.id.npTempMax);
        npHumMin=findViewById(R.id.npHumMin);
        npHumMax=findViewById(R.id.npHumMax);
        npMoistMin=findViewById(R.id.npMoistMin);
        npMoistMax=findViewById(R.id.npMoistMax);
        addBtn=findViewById(R.id.addBtn);
        cancelBtn=findViewById(R.id.cancelBtn);

        npTempMin.setMaxValue(0);
        npTempMin.setMaxValue(100);
        npTempMax.setMaxValue(0);
        npTempMax.setMaxValue(100);
        npHumMin.setMaxValue(0);
        npHumMin.setMaxValue(100);
        npHumMax.setMaxValue(0);
        npHumMax.setMaxValue(100);
        npMoistMin.setMaxValue(0);
        npMoistMin.setMaxValue(100);
        npMoistMax.setMaxValue(0);
        npMoistMax.setMaxValue(100);

        cancelBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),Information.class));
            finish();
        });

        mDatabase=FirebaseDatabase.getInstance();
        ref=mDatabase.getReference().child("PlantInfo").push();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName=edit_plantName.getText().toString();
                String tempMin=String.valueOf(npTempMin.getValue());
                String tempMax=String.valueOf(npTempMax.getValue());
                String humMin=String.valueOf(npHumMin.getValue());
                String humMax=String.valueOf(npHumMax.getValue());
                String moistMin=String.valueOf(npMoistMin.getValue());
                String moistMax=String.valueOf(npMoistMax.getValue());

                HashMap<String,Object> map = new HashMap<>();
                map.put("plant",pName);
                map.put("min_temp",tempMin);
                map.put("max_temp",tempMax);
                map.put("min_hum",humMin);
                map.put("max_hum",humMax);
                map.put("min_moist",moistMin);
                map.put("max_moist",moistMax);

                ref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        edit_plantName.setText("");
                        npTempMin.setValue(0);
                        npTempMax.setValue(100);
                        npHumMin.setValue(0);
                        npHumMax.setValue(100);
                        npMoistMin.setValue(0);
                        npMoistMax.setValue(100);
                        Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_LONG).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to add",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}
package com.example.iotagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Addplant extends AppCompatActivity {
    Button saveBtn;
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
        saveBtn=findViewById(R.id.saveBtn);

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

        mDatabase=FirebaseDatabase.getInstance();
        ref=mDatabase.getReference().child("PlantInfo").push();

        saveBtn.setOnClickListener(new View.OnClickListener() {
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
                map.put("Plant",pName);
                map.put("Min Temp",tempMin);
                map.put("Max Temp",tempMax);
                map.put("Min Hum",humMin);
                map.put("Max Hum",humMax);
                map.put("Min Moist",moistMin);
                map.put("Max Moist",moistMax);

                ref.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent=new Intent(Addplant.this,Information.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });

    }
}
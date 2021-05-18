package com.example.iotagro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Manual extends AppCompatActivity {
    Switch Switch1, Switch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        Switch1=findViewById(R.id.switch1);
        Switch2=findViewById(R.id.switch2);

        Switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Switch1.isChecked()){
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myRef= database.getReference("RealTimeDHT11SensorDataAndLEDStat/LEDStat");
                    myRef.setValue(1);
                }
                else {
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myRef= database.getReference("RealTimeDHT11SensorDataAndLEDStat/LEDStat");
                    myRef.setValue(0);
                }
            }
        });
        Switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Switch2.isChecked()){
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myRef= database.getReference("RealTimeDHT11SensorDataAndLEDStat/RelayStat");
                    myRef.setValue(1);
                }
                else {
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myRef= database.getReference("RealTimeDHT11SensorDataAndLEDStat/RelayStat");
                    myRef.setValue(0);
                }
            }
        });
    }
}
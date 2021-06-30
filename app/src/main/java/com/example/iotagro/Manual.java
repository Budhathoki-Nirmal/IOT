package com.example.iotagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Manual extends AppCompatActivity {
    Switch Switch1, Switch2,Switch3;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int l,f,m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        Switch1=findViewById(R.id.switch1);
        Switch2=findViewById(R.id.switch2);
        Switch3=findViewById(R.id.switch3);
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference("RealTimeDHT11SensorDataAndLEDStat");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                final SwitchState switchState=datasnapshot.getValue(SwitchState.class);
                l =switchState.getLEDStat();
                f=switchState.getRelayFanStat();
                m=switchState.getRelayStat();
                Led();
                Fan();
                Motor();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void Motor() {
        if (m==0){
            Switch3.setChecked(false);

            Switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    myRef.child("RelayStat").setValue(1);
                }
            });
        }
        else if (m==1){
            Switch3.setChecked(true);
            Switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    myRef.child("RelayStat").setValue(0);
                }
            });
        }
    }

    private void Fan() {
        if (f==0){
            Switch2.setChecked(false);

            Switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    myRef.child("RelayFanStat").setValue(1);
                }
            });
        }
        else if (f==1){
            Switch2.setChecked(true);
            Switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    myRef.child("RelayFanStat").setValue(0);
                }
            });
        }

    }

    private void Led() {
        if(l==0){
            Switch1.setChecked(false);

            Switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    myRef.child("LEDStat").setValue(1);
                }
            });
        }
        else if (l==1){
            Switch1.setChecked(true);
            Switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    myRef.child("LEDStat").setValue(0);
                }
            });
        }
    }
}
package com.example.iotagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Automatic extends AppCompatActivity {
    TextView t1,t2,t3,h1,h2,h3,m1,m2,m3;
    Button btnApply;
    TextView textViewHighTemperature,textViewLowTemperature,textViewHighHumidity,textViewLowHumidity,textViewHighMoisture,textViewLowMoisture;
//    SeekBar seekBarTemp,seekBarHum,seekBarMoist;
    int Tmax,Tmin,Hmax,Hmin,Mmin,Mmax;
    FirebaseDatabase mDatabase;
    DatabaseReference ref;
    RangeSeekBar rangeTemperature, rangeHumidity, rangeMoisture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic);
        btnApply=findViewById(R.id.btnApply);

        textViewHighTemperature=findViewById(R.id.textViewHighTemperature);
        textViewLowTemperature=findViewById(R.id.textViewLowTemperature);
        textViewHighHumidity=findViewById(R.id.textViewHighHumidity);
        textViewLowHumidity=findViewById(R.id.textViewLowHumidity);
        textViewHighMoisture=findViewById(R.id.textViewHighMoisture);
        textViewLowMoisture=findViewById(R.id.textViewLowMoisture);

        rangeTemperature=findViewById(R.id.rangeTemperature);
        rangeHumidity=findViewById(R.id.rangeHumidity);
        rangeMoisture=findViewById(R.id.rangeMoisture);

        t1=findViewById(R.id.tvT1);
        t2=findViewById(R.id.tvT2);
        h1=findViewById(R.id.tvH1);
        h2=findViewById(R.id.tvH2);
        m1=findViewById(R.id.tvM1);
        m2=findViewById(R.id.tvM2);


        mDatabase=FirebaseDatabase.getInstance();
        ref=mDatabase.getReference().child("Threshold");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                t1.setText(datasnapshot.child("Low_Temperature").getValue().toString()+" \u2103");
                t2.setText(datasnapshot.child("High_Temperature").getValue().toString()+" \u2103");
                h1.setText(datasnapshot.child("Low_Humidity").getValue().toString()+" %");
                h2.setText(datasnapshot.child("High_Humidity").getValue().toString()+" %");
                m1.setText(datasnapshot.child("Low_Soil_Moisture").getValue().toString()+" %");
                m2.setText(datasnapshot.child("High_Soil_Moisture").getValue().toString()+" %");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        rangeTemperature.setSelectedMaxValue(100);
        rangeTemperature.setSelectedMinValue(0);
        rangeHumidity.setSelectedMaxValue(100);
        rangeHumidity.setSelectedMinValue(0);
        rangeMoisture.setSelectedMaxValue(100);
        rangeMoisture.setSelectedMinValue(0);



        rangeTemperature.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number tMinValue=bar.getSelectedMinValue();
                Number tMaxValue=bar.getSelectedMaxValue();
                Tmin=(int)tMinValue;
                Tmax=(int)tMaxValue;
                textViewLowTemperature.setText(""+Tmin);
                textViewHighTemperature.setText(""+Tmax);
                //textViewHighTemperature.setText(Tmax);
              //  textViewLowTemperature.setText(Tmin);
            }
        });
        rangeHumidity.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number hMinValue=bar.getSelectedMinValue();
                Number hMaxValue=bar.getSelectedMaxValue();
                Hmin=(int)hMinValue;
                Hmax=(int)hMaxValue;
                textViewHighHumidity.setText(""+Hmax);
                textViewLowHumidity.setText(""+Hmin);
            }
        });
        rangeMoisture.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number mMinValue=bar.getSelectedMinValue();
                Number mMaxValue=bar.getSelectedMaxValue();
                Mmin=(int)mMinValue;
                Mmax=(int)mMaxValue;
               textViewHighMoisture.setText(""+Mmax);
                textViewLowMoisture.setText(""+Mmin);
            }
        });


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tH,tL,hH,hL,mH,mL;
                tH=Integer.parseInt(textViewHighTemperature.getText().toString());
                tL=Integer.parseInt(textViewLowTemperature.getText().toString());
                hH=Integer.parseInt(textViewHighHumidity.getText().toString());
                hL=Integer.parseInt(textViewLowHumidity.getText().toString());
                mH=Integer.parseInt(textViewHighMoisture.getText().toString());
                mL=Integer.parseInt(textViewLowMoisture.getText().toString());

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
                            Toast.makeText(Automatic.this,"Completed",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Automatic.this,Setting.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Automatic.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });
    }


}
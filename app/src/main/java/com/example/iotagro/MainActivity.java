package com.example.iotagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private CardView cardSet,cardTable,cardDisease;
    TextView tv1,tv2,tv3,t5;
    FirebaseDatabase mDatabase;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1=(TextView) findViewById(R.id.tVtemperature);
        tv2=(TextView) findViewById(R.id.tVhumidity);
        tv3=(TextView) findViewById(R.id.tVmoisture);

        mDatabase=FirebaseDatabase.getInstance();
        ref=mDatabase.getReference().child("RealTimeDHT11SensorDataAndLEDStat");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String t1 = dataSnapshot.child("Temperature").getValue().toString();
                    String t2 = dataSnapshot.child("Humidity").getValue().toString();
                    String t3 = dataSnapshot.child("Moisture").getValue().toString();
                    tv1.setText("" + t1+" \u2103");
                    tv2.setText("" + t2+" %");
                    tv3.setText("" + t3+" %");
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cardTable=(CardView) findViewById(R.id.cardTable);
        cardTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivity1();
            }
        });

        cardDisease=(CardView) findViewById(R.id.cardDisease);
        cardDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivity2();
            }
        });

        cardSet=(CardView) findViewById(R.id.cardSetting);
        cardSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivity3();
            }
        });

    }

    private void openActivity1() {
        Intent intent = new Intent(this, DataTable.class);
        startActivity(intent);
    }

    public void openActivity2() {
        Intent intent = new Intent(this, DiseasePlant.class);
        startActivity(intent);
    }

    public void openActivity3() {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }
}
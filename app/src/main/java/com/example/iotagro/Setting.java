package com.example.iotagro;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Setting extends AppCompatActivity {

    Button btnManual, btnAutomatic,btnInformation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnManual=findViewById(R.id.btnManual);
        btnAutomatic=findViewById(R.id.btnAutomatic);
        btnInformation=findViewById(R.id.btnInformation);

        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityManual();
            }
        });

        btnAutomatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAutomatic();
            }
        });
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {openActivityInformation(); }
        });

    }

    private void openActivityManual() {
        Intent intent = new Intent(this, Manual.class);
        startActivity(intent);
    }
    private void openActivityAutomatic() {
        Intent intent=new Intent(this, Automatic.class);
        startActivity(intent);
    }
    private void openActivityInformation() {
        Intent intent = new Intent(this, Information.class);
        startActivity(intent);
    }

}
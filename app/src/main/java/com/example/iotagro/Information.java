package com.example.iotagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Information extends AppCompatActivity {
    RecyclerView recyclerView;
    PlantAdapter plantAdapter;
    List<Plant> plantList;
    Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        recyclerView=findViewById(R.id.plant_list);

        plantList=new ArrayList<Plant>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FetchData();



    }

    private void FetchData() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("PlantInfo")
                 .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Name=dataSnapshot.child("Plant").getValue().toString();
                            String tMin=dataSnapshot.child("Min Temp").getValue().toString();
                            String tMax=dataSnapshot.child("Max Temp").getValue().toString();
                            String hMin=dataSnapshot.child("Min Hum").getValue().toString();
                            String hMax=dataSnapshot.child("Max Hum").getValue().toString();
                            String mMin=dataSnapshot.child("Min Moist").getValue().toString();
                            String mMax=dataSnapshot.child("Max Moist").getValue().toString();
                            plantList.add(new Plant(Name,tMin,tMax,hMin,hMax,mMin,mMax));
                        }
                        plantAdapter = new PlantAdapter(Information.this, plantList);
                        recyclerView.setAdapter(plantAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(Information.this, Addplant.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
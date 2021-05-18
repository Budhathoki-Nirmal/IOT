package com.example.iotagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Filter extends AppCompatActivity{
    RecyclerView recycler_view;
    PaymentAdapter adapter;
    List<Model> list;
    LinearLayoutManager manager;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

    TextView input_minimum, input_maximum;
    Date date_minimum;
    Date date_maximum;
    Button buttonFilter;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-YYYY", new Locale("eng","NEP"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        input_minimum = findViewById(R.id.input_minimum);
        input_maximum = findViewById(R.id.input_maximum);
        buttonFilter=findViewById(R.id.buttonFilter);
        recycler_view=findViewById(R.id.recycler_view);
        manager=new LinearLayoutManager(this);
        list=new ArrayList<>();
        recycler_view.setLayoutManager(manager);

//        FetchData();

        input_minimum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Filter.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        calendar.set(year,month+1,dayOfMonth);
//                        input_minimum.setText(simpleDateFormat.format(calendar.getTime()));
                        if((month+1)<=9 && dayOfMonth<=9){
                            input_minimum.setText("0"+dayOfMonth+"-"+"0"+(month+1)+"-"+year);
                            date_minimum=calendar.getTime();

                        }
                        else if((month+1)<=9 && dayOfMonth>9){
                            input_minimum.setText(dayOfMonth+"-"+"0"+(month+1)+"-"+year);
                            date_minimum=calendar.getTime();

                        }
                        else if((month+1)>9 && dayOfMonth<=9) {
                            input_minimum.setText("0" + dayOfMonth + "-" + (month + 1) + "-" + year);
                            date_minimum = calendar.getTime();
                        }
                        else {
                            input_minimum.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                            date_minimum=calendar.getTime();
                        }

                        String input1 = input_minimum.getText().toString();
                        String input2 = input_maximum.getText().toString();
                        if (input1.isEmpty() && input2.isEmpty()){
                            buttonFilter.setEnabled(false);
                        }else {
                            buttonFilter.setEnabled(true);
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        input_maximum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Filter.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if((month+1)<=9 && dayOfMonth<=9){
                            input_maximum.setText("0"+dayOfMonth+"-"+"0"+(month+1)+"-"+year);
                            date_maximum=calendar.getTime();

                        }
                        else if((month+1)<=9 && dayOfMonth>9){
                            input_maximum.setText(dayOfMonth+"-"+"0"+(month+1)+"-"+year);
                            date_maximum=calendar.getTime();

                        }
                        else if((month+1)>9 && dayOfMonth<=9) {
                            input_maximum.setText("0" + dayOfMonth + "-" + (month + 1) + "-" + year);
                            date_maximum = calendar.getTime();
                        }
                        else {
                            input_maximum.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                            date_maximum=calendar.getTime();
                        }
//                        calendar.set(year,month,dayOfMonth);
//                        input_maximum.setText(simpleDateFormat.format(calendar.getTime()));
//                        input_maximum.setText(dayOfMonth+"-"+(month+1)+"-"+year);
//                        date_maximum=calendar.getTime();

                        String input1 = input_minimum.getText().toString();
                        String input2 = input_maximum.getText().toString();
                        if (input1.isEmpty() && input2.isEmpty()){
                            buttonFilter.setEnabled(false);
                        }else {
                            buttonFilter.setEnabled(true);
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = reference.child("DHT11Database").orderByChild("_Date").startAt(date_minimum.getTime()).endAt(date_maximum.getTime());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        showLisener(snapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        showData();
    }

    private void showData() {
        reference.child("DHT11Database").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showLisener(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showLisener(DataSnapshot snapshot) {
        list.clear();
        for (DataSnapshot item : snapshot.getChildren()) {
            Model data = item.getValue(Model.class);
            list.add(data);
        }
        adapter = new PaymentAdapter(Filter.this, list);
        recycler_view.setAdapter(adapter);
    }

//    private void FetchData() {
//        reference.child("DHT11Database")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
//                            String Date=snapshot.child("_Date").getValue().toString();
//                            String Temperature= snapshot.child("_Temperature").getValue().toString();
//                            String Humidity=snapshot.child("_Humidity").getValue().toString();
//                            String Soil_Moisture=snapshot.child("_Moisture").getValue().toString();
//                            list.add(new Model(Date,Temperature,Humidity,Soil_Moisture));
//                        }
//                        Collections.reverse(list);
//                        adapter=new PaymentAdapter(Filter.this,list);
//                        recycler_view.setAdapter(adapter);
////
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
    }
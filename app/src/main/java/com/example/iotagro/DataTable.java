package com.example.iotagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DataTable extends AppCompatActivity{
    RecyclerView recycler_view;
    TableAdapter adapter;
    List<Model> list;
    LinearLayoutManager manager;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    LayoutInflater layoutInflater;
    AlertDialog builderAlert;
    View showFilter;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("eng", "NEP"));
    Context context;


    Button dateStart,dateEnd,filterApply;
    EditText inputMinimum,inputMaximum;
    Date date_minimum;
    Date date_maximum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_table);

        context=this;
        recycler_view=findViewById(R.id.recycler_view);
        manager=new LinearLayoutManager(this);
        list=new ArrayList<>();
        recycler_view.setLayoutManager(manager);
        FetchData();

    }


    private void FetchData() {
        database.child("DHT11Database")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            try {
                                String Date = snapshot.child("_Date").getValue().toString();
                                String Time = snapshot.child("_Time").getValue().toString();
                                String Temperature = snapshot.child("_Temperature").getValue().toString();
                                String Humidity = snapshot.child("_Humidity").getValue().toString();
                                String Soil_Moisture = snapshot.child("_Moisture").getValue().toString();
                                list.add(new Model(Date, Time, Temperature, Humidity, Soil_Moisture));
                            }catch (Exception e){
                                Toast.makeText(DataTable.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        Collections.reverse(list);
                        adapter=new TableAdapter(DataTable.this,list);
                        recycler_view.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.table_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_search) {
            SearchView searchView = (SearchView)item.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    return false;
                }
            });
        }
        else if(item.getItemId()==R.id.action_filter) {

            filter();
        }

        return super.onOptionsItemSelected(item);

    }

    private void filter() {
        builderAlert = new AlertDialog.Builder(this).create();
        layoutInflater = getLayoutInflater();
        showFilter = layoutInflater.inflate(R.layout.filter_dialog, null);
        builderAlert.setView(showFilter);

        inputMinimum=showFilter.findViewById(R.id.input_minimum);
        inputMaximum=showFilter.findViewById(R.id.input_maximum);
        dateStart=showFilter.findViewById(R.id.btnDateStart);
        dateEnd=showFilter.findViewById(R.id.btnDateEnd);
        filterApply=showFilter.findViewById(R.id.btnFilterApply);

        builderAlert.show();

        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        inputMinimum.setText(simpleDateFormat.format(calendar.getTime()));
                        date_minimum = calendar.getTime();

                        String input1 = inputMinimum.getText().toString();
                        String input2 = inputMaximum.getText().toString();
                        if (input1.isEmpty() && input2.isEmpty()) {
                            filterApply.setEnabled(false);
                        } else {
                            filterApply.setEnabled(true);
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        inputMaximum.setText(simpleDateFormat.format(calendar.getTime()));
                        date_maximum = calendar.getTime();

                        String input1 = inputMinimum.getText().toString();
                        String input2 = inputMaximum.getText().toString();
                        if (input1.isEmpty() && input2.isEmpty()) {
                            filterApply.setEnabled(false);
                        } else {
                            filterApply.setEnabled(true);
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        filterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderAlert.dismiss();
                String Start=inputMinimum.getText().toString().trim();
                String End=inputMaximum.getText().toString().trim();
                try{
                    Date strDate=simpleDateFormat.parse(Start);
                    Date endDate=simpleDateFormat.parse(End);
                    assert strDate != null;
                    adapter.filterDateRange(strDate,endDate);
                }catch (ParseException e){
                    e.printStackTrace();
                }

            }
        });

    }

}
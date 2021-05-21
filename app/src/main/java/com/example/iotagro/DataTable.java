package com.example.iotagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.app.DatePickerDialog;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DataTable extends AppCompatActivity{
    RecyclerView recycler_view;
    TableAdapter adapter;
    List<Model> list;
    LinearLayoutManager manager;
//    EditText eT1;
//    DatePickerDialog.OnDateSetListener mDateSetListener;
//    private static final String TAG = "DAtaTable";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_table);


        recycler_view=findViewById(R.id.recycler_view);
        manager=new LinearLayoutManager(this);
        list=new ArrayList<>();
        recycler_view.setLayoutManager(manager);
        FetchData();



    }

    private void FetchData() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("DHT11Database")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            String Date=snapshot.child("_Date").getValue().toString();
                            String Temperature= snapshot.child("_Temperature").getValue().toString();
                            String Humidity=snapshot.child("_Humidity").getValue().toString();
                            String Soil_Moisture=snapshot.child("_Moisture").getValue().toString();
                            list.add(new Model(Date,Temperature,Humidity,Soil_Moisture));
                        }
                        Collections.reverse(list);
                        adapter=new TableAdapter(DataTable.this,list);
                        recycler_view.setAdapter(adapter);
//
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
        else if(item.getItemId()==R.id.action_filter){
            Intent intent = new Intent(DataTable.this, Filter.class);
            startActivity(intent);

//            Button btnDate;
//            Filter_Dialog fDialog = new Filter_Dialog();
//            fDialog.show(getSupportFragmentManager(), "Filter Dialog");
//            AlertDialog.Builder builder=new AlertDialog.Builder(DataTable.this, R.style.AlertDialogTheme);
//            final View filter_dialog=getLayoutInflater().inflate(R.layout.filter_dialog,null);
//            builder.setTitle("Select One");

//            final String[] listItems= new String[]{
//                    "Date",
//                    "Temperature",
//                    "Humidity",
//                    "Soil Moisture"
//            };
//
//            builder.setSingleChoiceItems(listItems, 0, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//            btnDate=findViewById(R.id.btn_minimal);
//            btnDate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Calendar cal = Calendar.getInstance();
//                    int year = cal.get(Calendar.YEAR);
//                    int month = cal.get(Calendar.MONTH);
//                    int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                    DatePickerDialog dialog = new DatePickerDialog(
//                            DataTable.this,
//                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                            mDateSetListener,
//                            year,month,day);
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    dialog.show();
//                }
//            });
//
//            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                    month = month + 1;
//                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//                    String date = month + "/" + day + "/" + year;
//                    eT1.setText(date);
//                }
//            };
//            builder.setView(filter_dialog);
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            AlertDialog dialog=builder.create();
//            dialog.show();
        }

        return super.onOptionsItemSelected(item);

    }

}
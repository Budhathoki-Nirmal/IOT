//package com.example.iotagro;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.DatePickerDialog;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.DatePicker;
//import android.widget.GridView;
//import android.widget.TextView;
//
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//import com.github.mikephil.charting.utils.ColorTemplate;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.List;
//
//public class Bchart extends AppCompatActivity {
//    private static final String TAG = "Bchart";
//    TextView tvFrom,tvTo;
//    DatePickerDialog.OnDateSetListener DateSetListener1;
//    DatePickerDialog.OnDateSetListener DateSetListener2;
//
//    FirebaseDatabase mDatabase;
//    DatabaseReference ref;
////    ArrayList<PointValue> list;
//
//    BarChart TempbarChart;
////    BarChart HumbarChart;
////    BarChart SoilbarChart;
////    LineDataSet lineDataSet=new LineDataSet(null,null);
////    ArrayList<ILineDataSet> iLineDataSets=new ArrayList<>();
////    LineData lineData;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bchart);
//
//        TempbarChart=findViewById(R.id.mp_TempBarChart);
////        HumbarChart=findViewById(R.id.mp_HumBarChart);
////        SoilbarChart=findViewById(R.id.mp_SoilBarChart);
////        BarDataSet barDataSet1=new BarDataSet(dataValues1(),"Temperature");
////
////        BarDataSet barDataSet2=new BarDataSet(dataValues2(),"Humidity");
////
////        BarDataSet barDataSet3=new BarDataSet(dataValues3(),"Soil Moisture");
//
////        BarData Data=new BarData(barDataSet1);
////        TempbarChart.setData(Data);
////        TempbarChart.invalidate();
////        BarData Data1=new BarData(barDataSet2);
////        HumbarChart.setData(Data1);
////        HumbarChart.invalidate();
////        BarData Data2=new BarData(barDataSet3);
////        SoilbarChart.setData(Data2);
////        SoilbarChart.invalidate();
//
//
//        mDatabase=FirebaseDatabase.getInstance();
//        ref=mDatabase.getReference().child("Post");
//        TempbarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter());
//
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                String id=ref.getKey();
//                ArrayList<BarEntry> dataVals=new ArrayList<>();
////                for (dataSnapshot.hasChildren()) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        String Date= snapshot.child("Date").getValue().toString();
//                        String  Temperature = snapshot.child("Temperature").getValue().toString();
//                        long d=Long.parseLong(Date);
//                        int t=Integer.parseInt(Temperature);
////                        list.add(new PointValue(Temperature,Humidity));
//                        PointValue pointValue = new PointValue(d, t);
//                        dataVals.add(new BarEntry(pointValue.getxValue(), pointValue.getyValue()));
//                        BarDataSet barDataSet=new BarDataSet(dataVals,"Humidity");
//                        BarData barData=new BarData(barDataSet);
//                        TempbarChart.setData(barData);
//                        TempbarChart.invalidate();
//                    }
////
////                    TempbarChart.invalidate();
////                }else {
////                    TempbarChart.clear();
////                    TempbarChart.invalidate();
////                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//
//
//        TempbarChart.setDescription(null);
////        String[] days=new String[]{"Sunday","Monday","Tuesday","Wednesday"};
//        //X-axis
////        XAxis xAxis=TempbarChart.getXAxis();
////        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
////        xAxis.setCenterAxisLabels(true);
////        xAxis.setDrawGridLines(false);
////        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
////        xAxis.setGranularity(1f);
////        xAxis.setGranularityEnabled(true);
//        Legend l = TempbarChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(true);
//        l.setYOffset(20f);
//        l.setXOffset(0f);
//        l.setYEntrySpace(0f);
//        l.setTextSize(8f);
//
//        TempbarChart.setDragEnabled(true);
//        TempbarChart.setVisibleXRangeMaximum(10);
//
//        tvFrom=findViewById(R.id.tvFrom);
//        tvTo=findViewById(R.id.tvTo);
//
//        tvFrom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog = new DatePickerDialog(
//                        Bchart.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        DateSetListener1,
//                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//            }
//        });
//        DateSetListener1 = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                month = month + 1;
//                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//                String date = month + "/" + day + "/" + year;
//                tvFrom.setText(date);
//            }
//        };
//        tvTo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog = new DatePickerDialog(
//                        Bchart.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        DateSetListener2,
//                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//            }
//        });
//        DateSetListener2 = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                month = month + 1;
//                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//                String date = month + "/" + day + "/" + year;
//                tvTo.setText(date);
//            }
//        };
//
//    }
//
////    private void showChart(ArrayList<Entry> dataVals) {
////        lineDataSet.setValues(dataVals);
////        lineDataSet.setLabel("DataSet 1");
////        iLineDataSets.clear();
////        iLineDataSets.add(lineDataSet);
////        lineData=new LineData(iLineDataSets);
////        TempbarChart.clear();
////        TempbarChart.setData(lineData);
////        TempbarChart.invalidate();
////    }
//
////    private ArrayList<BarEntry> dataValues1(){
////        ArrayList<BarEntry> dataVals=new ArrayList<>();
////        dataVals.add(new BarEntry(1,3));
////        dataVals.add(new BarEntry(2,4));
////        dataVals.add(new BarEntry(3,6));
////        dataVals.add(new BarEntry(4,4));
////        return dataVals;
////    }
////    private ArrayList<BarEntry> dataValues2(){
////        ArrayList<BarEntry> dataVals=new ArrayList<>();
////        dataVals.add(new BarEntry(1,4));
////        dataVals.add(new BarEntry(2,5));
////        dataVals.add(new BarEntry(3,7));
////        dataVals.add(new BarEntry(4,7));
////        return dataVals;
////    }
////    private ArrayList<BarEntry> dataValues3(){
////        ArrayList<BarEntry> dataVals=new ArrayList<>();
////        dataVals.add(new BarEntry(1,3));
////        dataVals.add(new BarEntry(2,4));
////        dataVals.add(new BarEntry(3,6));
////        dataVals.add(new BarEntry(4,6));
////        return dataVals;
////    }
//
//
//}
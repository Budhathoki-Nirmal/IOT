//package com.example.iotagro;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
//public class chart extends AppCompatActivity {
//    CardView cardBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chart);
//
//        cardBar=findViewById(R.id.cardBar);
//        cardBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { openActivity21();
//            }
//        });
//    }
//    private void openActivity21() {
//        Intent intent = new Intent(this, Bchart.class);
//        startActivity(intent);
//    }
//}
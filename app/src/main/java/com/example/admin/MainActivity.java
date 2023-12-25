package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button tour,hotel,cab,rent,other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        tour=findViewById(R.id.btntoursit);
        tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,toruisrtretrieve.class));

            }
        });
        hotel=findViewById(R.id.btnhotel);
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,hotelretrieve.class));

            }
        });
        cab=findViewById(R.id.btncab);
        cab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,cabretrieve.class));

            }
        });
        rent=findViewById(R.id.btnrent);
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,rentretrieve.class));

            }
        });
        other=findViewById(R.id.btnother);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,otherretreieve.class));

            }
        });
    }
}
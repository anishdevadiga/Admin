package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class hotelretrieve extends AppCompatActivity {
    RecyclerView hotelrecycle;
    FloatingActionButton hoteladd;
    HotelAdapter hotelAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotelretrieve);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2979FF")));
        getSupportActionBar().setTitle("");
        hotelrecycle=findViewById(R.id.hotelrecyle);
        hotelrecycle.setLayoutManager(new LinearLayoutManager(this));
        hoteladd=findViewById(R.id.hotel);
        FirebaseRecyclerOptions<Hotelmodel> options =
                new FirebaseRecyclerOptions.Builder<Hotelmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotel" ), Hotelmodel.class)
                        .build();
        hotelAdapter=new HotelAdapter(options);
        hotelrecycle.setAdapter(hotelAdapter);

        hoteladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(hotelretrieve.this,hoteladd.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        hotelAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hotelAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void txtSearch(String str) {
        FirebaseRecyclerOptions<Hotelmodel> options =
                new FirebaseRecyclerOptions.Builder<Hotelmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotel").orderByChild("hotelname").startAt(str).endAt(str + "~"), Hotelmodel.class)
                        .build();
        hotelAdapter = new HotelAdapter(options);
        hotelAdapter.startListening();
        hotelrecycle.setAdapter(hotelAdapter);
    }
}

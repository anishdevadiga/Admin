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

public class toruisrtretrieve extends AppCompatActivity {
    RecyclerView tourrecycle;
    TourAdapter tourAdapter;

    FloatingActionButton addtour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toruisrtretrieve);
        tourrecycle=findViewById(R.id.tourrecyle);
        tourrecycle.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2979FF")));
        getSupportActionBar().setTitle("");
        addtour=findViewById(R.id.floatingActionButton2);
        addtour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(toruisrtretrieve.this,addtour.class));
                finish();
            }
        });


        FirebaseRecyclerOptions<Tourmodel> options =
                new FirebaseRecyclerOptions.Builder<Tourmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tourist_places"), Tourmodel.class)
                        .build();
        tourAdapter=new TourAdapter(options);
        tourrecycle.setAdapter(tourAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        tourAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tourAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();
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
    private void txtSearch(String str){
        FirebaseRecyclerOptions<Tourmodel> options =
                new FirebaseRecyclerOptions.Builder<Tourmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tourist_places").orderByChild("placename").startAt(str).endAt(str+"~"), Tourmodel.class)
                        .build();
        tourAdapter=new TourAdapter(options);
        tourAdapter.startListening();
        tourrecycle.setAdapter(tourAdapter);


    }
}
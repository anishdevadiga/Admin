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

public class rentretrieve extends AppCompatActivity {

    RecyclerView rentrecycle;
    RentAdapter rentAdapter;
    FloatingActionButton rent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentretrieve);
        rentrecycle=findViewById(R.id.rentrecyle);
        rentrecycle.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2979FF")));
        getSupportActionBar().setTitle("");
        FirebaseRecyclerOptions<RentModel> options =
                new FirebaseRecyclerOptions.Builder<RentModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("rent"), RentModel.class)
                        .build();
        rentAdapter=new RentAdapter(options);
        rentrecycle.setAdapter(rentAdapter);
        rent=(FloatingActionButton) findViewById(R.id.rent);
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),rentadd.class));
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        rentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        rentAdapter.stopListening();
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
        FirebaseRecyclerOptions<RentModel> options =
                new FirebaseRecyclerOptions.Builder<RentModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("rent").orderByChild("name").startAt(str).endAt(str+"~"), RentModel.class)
                        .build();
        rentAdapter=new RentAdapter(options);
        rentrecycle.setAdapter(rentAdapter);
        rentAdapter.startListening();



    }
}
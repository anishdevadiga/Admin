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
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class cabretrieve extends AppCompatActivity {
    RecyclerView cabrecycle;
    CabAdapter cabAdapter;
    FloatingActionButton btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabretrieve);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2979FF")));
        getSupportActionBar().setTitle("");
        cabrecycle=findViewById(R.id.cabrecyle);
        cabrecycle.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CabModel>options=new FirebaseRecyclerOptions.Builder<CabModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Cab"),CabModel.class).build();
        cabAdapter=new CabAdapter(options);
        cabrecycle.setAdapter(cabAdapter);
        btnadd=findViewById(R.id.cab);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cabretrieve.this,Cabadd.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        cabAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cabAdapter.stopListening();
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
        FirebaseRecyclerOptions<CabModel> options =
                new FirebaseRecyclerOptions.Builder<CabModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cab").orderByChild("cabname").startAt(str).endAt(str + "~"),CabModel.class)
                        .build();
        cabAdapter=new CabAdapter(options);
        cabrecycle.setAdapter(cabAdapter);
        cabAdapter.startListening();
    }
}
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

public class otherretreieve extends AppCompatActivity {
    RecyclerView otherrecycle;
    FloatingActionButton other;
    OtherAdapter otherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherretreieve);
        otherrecycle=findViewById(R.id.otherrecyle);
        otherrecycle.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2979FF")));
        getSupportActionBar().setTitle("");
        FirebaseRecyclerOptions<Othermodel> options =
                new FirebaseRecyclerOptions.Builder<Othermodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("other"), Othermodel.class)
                        .build();
        otherAdapter=new OtherAdapter(options);
        otherrecycle.setAdapter(otherAdapter);
        other=findViewById(R.id.other);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(otherretreieve.this,otheradd.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        otherAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        otherAdapter.stopListening();
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
        FirebaseRecyclerOptions<Othermodel> options =
                new FirebaseRecyclerOptions.Builder<Othermodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("other").orderByChild("name").startAt(str).endAt(str+"~"), Othermodel.class)
                        .build();

        otherAdapter=new OtherAdapter(options);
        otherrecycle.setAdapter(otherAdapter);
        otherAdapter.startListening();



    }

}
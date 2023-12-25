package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class otheradd extends AppCompatActivity {
    Button add,back;
    EditText name,rating,time,location,description,pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otheradd);
        getSupportActionBar().hide();
        back=findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(otheradd.this,otherretreieve.class));finish();;
            }
        });
        name=(EditText) findViewById(R.id.addothername);
       // rating=(EditText) findViewById(R.id.addotherrating);
        time=(EditText) findViewById(R.id.addothertime);
       // location=(EditText) findViewById(R.id.addotherlocation);
        description=(EditText) findViewById(R.id.addotherdesc);
        pic=(EditText) findViewById(R.id.addotherpic);
        add=findViewById(R.id.btnadd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(name.getText().toString())&&!TextUtils.isEmpty(time.getText().toString())&&!TextUtils.isEmpty(description.getText().toString())&&!TextUtils.isEmpty(pic.getText().toString())) {
                    insertall();
                    clearall();
                }else {
                    Toast.makeText(otheradd.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void clearall() {
        name.setText("");
        time.setText("");
        //rating.setText("");
        //location.setText("");
        description.setText("");
        pic.setText("");
    }

    private void insertall() {
        Map<String,Object> map=new HashMap<>();
        map.put("name",name.getText().toString());
       // map.put("rating",rating.getText().toString());
        map.put("time",time.getText().toString());
       // map.put("location",location.getText().toString());
        map.put("description",description.getText().toString());
        map.put("pic",pic.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("other").push().setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(otheradd.this, "Added", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(otheradd.this, "Error While Adding", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
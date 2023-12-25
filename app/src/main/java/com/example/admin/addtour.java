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

public class addtour extends AppCompatActivity {

    Button back,add;
    EditText placename,placedescription,placelocationurl,placetime,placepicurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtour);
        getSupportActionBar().hide();
        placename=findViewById(R.id.addtourname);
        placedescription=findViewById(R.id.addtourdesc);
        //placelocationurl=findViewById(R.id.addtourlocation);
        placetime=findViewById(R.id.addtourtime);
        placepicurl=findViewById(R.id.addtourpic);
        back=findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addtour.this,toruisrtretrieve.class));
                finish();

            }
        });
        add=findViewById(R.id.btnadd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(placename.getText().toString())&&!TextUtils.isEmpty(placedescription.getText().toString())&&!TextUtils.isEmpty(placelocationurl.getText().toString())&&!TextUtils.isEmpty(placetime.getText().toString())&&!TextUtils.isEmpty(placepicurl.getText().toString()))
                {

                    inserttourdata();cleardata();
                }else{
                    Toast.makeText(addtour.this, "Enter all Values", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void inserttourdata(){
        Map<String,Object>map=new HashMap<>();
        map.put("placename",placename.getText().toString());
        map.put("placedescription",placedescription.getText().toString());
       // map.put("placelocationurl",placelocationurl.getText().toString());
        map.put("placetime",placetime.getText().toString());
        map.put("placepicurl",placepicurl.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Tourist_places").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(addtour.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addtour.this,"Data Insertion Failed",Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void cleardata(){
        placename.setText("");
        placedescription.setText("");
        //placelocationurl.setText("");
        placetime.setText("");
        placepicurl.setText("");
    }

}
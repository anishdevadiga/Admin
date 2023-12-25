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

public class  hoteladd extends AppCompatActivity {
    EditText  hotelname, hotelratings, hoteldescription, hoteltime, hotelroomstatus, hotellocation, hotelpic;
    Button btnadd,btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoteladd);
        getSupportActionBar().hide();
        hotelname=(EditText) findViewById(R.id.addhotelname);
        //hotelratings=(EditText) findViewById(R.id.addhotelrating);
        hoteldescription=(EditText) findViewById(R.id.addhoteldesc);
        hoteltime=(EditText) findViewById(R.id.addhoteltime);
       // hotelroomstatus=(EditText) findViewById(R.id.addhotelstatus);
        //hotellocation=(EditText) findViewById(R.id.addhotelloc);
        hotelpic=(EditText) findViewById(R.id.addhotelpic);

        btnadd=(Button) findViewById(R.id.btnadd);
        btnback=(Button)findViewById(R.id.btnback);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(hotelname.getText().toString())&&!TextUtils.isEmpty(hoteldescription.getText().toString())&&!TextUtils.isEmpty(hoteltime.getText().toString())&&!TextUtils.isEmpty(hotelpic.getText().toString())) {
                    insertdata();
                    clearall();
                }else {
                    Toast.makeText(hoteladd.this, "enter all values", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(hoteladd.this,hotelretrieve.class));
                finish();
            }
        });


    }
    private void insertdata()
    {
        Map<String,Object>map=new HashMap<>() ;
        map.put("hotelname", hotelname.getText().toString());
       // map.put("hotelratings", hotelratings.getText().toString());
        map.put("hoteldescription", hoteldescription.getText().toString());
        map.put("hoteltime", hoteltime.getText().toString());
       // map.put("hotelroomstatus", hotelroomstatus.getText().toString());
       // map.put("hotellocation", hotellocation.getText().toString());
        map.put("hotelpic", hotelpic.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Hotel").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(hoteladd.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Toast.makeText(hoteladd.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void clearall(){
        hotelname.setText("");
        hoteldescription.setText("");
       // hotellocation.setText("");
       // hotelratings.setText("");
       // hotelroomstatus.setText("");
        hoteltime.setText("");
        hotelpic.setText("");

    }
}
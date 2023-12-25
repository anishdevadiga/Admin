package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Cabadd extends AppCompatActivity {
    Button btnadd,btnback;
    EditText cabname,cabnumber,cabprice,cabpic,cabrating,cabstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabadd);
        getSupportActionBar().hide();
        btnadd=findViewById(R.id.btncabadd);
        btnback=findViewById(R.id.btncabback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cabadd.this,cabretrieve.class));
                finish();
            }
        });
        cabname=(EditText) findViewById(R.id.cabname);
        cabnumber=(EditText) findViewById(R.id.cabnumber);
       // cabstatus=(EditText) findViewById(R.id.cabstatus);
        cabpic=(EditText) findViewById(R.id.cabpic);
      // cabrating=(EditText) findViewById(R.id.cabrating);
        cabprice=(EditText) findViewById(R.id.cabprice);
         String name=cabname.getText().toString();
         String number=cabnumber.getText().toString();
        //String status=cabstatus.getText().toString();
         String pic=cabpic.getText().toString();
        //String rating=cabrating.getText().toString();
        String price=cabprice.getText().toString();
         btnadd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(!TextUtils.isEmpty(cabname.getText().toString())&&!TextUtils.isEmpty(cabpic.getText().toString())&&!TextUtils.isEmpty(cabnumber.getText().toString())&&cabnumber.length()==10&&!TextUtils.isEmpty(cabprice.getText().toString())) {
                     insertdata();
                     clearall();
                 } else if (cabnumber.length()!=10) {
                     cabnumber.setError("Enter valid phone number");
                     cabnumber.requestFocus();

                 } else{
                     Toast.makeText(Cabadd.this, "Enter Values ", Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }

    private void clearall() {
        cabname.setText("");
        cabnumber.setText("");
        cabprice.setText("");
        cabpic.setText("");
        //cabrating.setText("");
       // cabstatus.setText("");
    }

    private void insertdata() {
            Map<String, Object> map = new HashMap<>();
            map.put("cabname", cabname.getText().toString());
            map.put("cabnumber", cabnumber.getText().toString());
            map.put("cabprice", cabprice.getText().toString());
            //map.put("cabrating",cabrating.getText().toString());
            map.put("cabpic", cabpic.getText().toString());
            //map.put("cabstatus",cabstatus.getText().toString());
            FirebaseDatabase.getInstance().getReference().child("Cab").push().setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Cabadd.this, "Added", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Cabadd.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

    }

}
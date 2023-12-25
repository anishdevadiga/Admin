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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class rentadd extends AppCompatActivity {

    Button add,back;
    EditText name,location,price,rating,status,time,pic,number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentadd);
        name=(EditText)findViewById(R.id.uprentname);
      //  location=(EditText)findViewById(R.id.uprentloc);
        price=(EditText)findViewById(R.id.uprentprice);
       // rating=(EditText)findViewById(R.id.uprentrating);
       // status=(EditText)findViewById(R.id.uprentstatus);
        time=(EditText)findViewById(R.id.uprenttime);
        pic=(EditText)findViewById(R.id.uprentpic);
        number=(EditText)findViewById(R.id.uprentnum);

        getSupportActionBar().hide();
        back=findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(rentadd.this,rentretrieve.class));
                finish();
            }
        });
        add=findViewById(R.id.btnadd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(name.getText().toString())&&!TextUtils.isEmpty(price.getText().toString())&&!TextUtils.isEmpty(time.getText().toString())&&!TextUtils.isEmpty(pic.getText().toString())&&!TextUtils.isEmpty(number.getText().toString())&&number.length()==10)
                {
                    insertdata();
                    clearall();
                } else if (number.length()!=10) {
                    number.setError("enter valid password");
                    number.requestFocus();
                    Toast.makeText(rentadd.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(rentadd.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void clearall() {
        name.setText("");
        //location.setText("");
        pic.setText("");
        number.setText("");
       // status.setText("");
       // rating.setText("");
        time.setText("");
        price.setText("");
    }

    private void insertdata() {
        Map<String,Object> map=new HashMap<>();
        //name,location,price,rating,status,time,pic,number
        map.put("name",name.getText().toString());
        //map.put("location",location.getText().toString());
        map.put("price",price.getText().toString());
        //map.put("rating",rating.getText().toString());
        map.put("time",time.getText().toString());
        map.put("pic",pic.getText().toString());
        map.put("number",number.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("rent").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(rentadd.this,"data inserted",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Toast.makeText(rentadd.this,"Error while insert",Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
package com.example.admin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class RentAdapter extends FirebaseRecyclerAdapter<RentModel,RentAdapter.myViewHolder>{


    public RentAdapter(@NonNull FirebaseRecyclerOptions<RentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull RentModel model) {
        holder.rentname.setText(model.getName());
        Glide.with(holder.rentpic.getContext()).load(model.getPic()).placeholder(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(com.google.firebase.storage.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.rentpic);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.rentpic.getContext()).setContentHolder(new ViewHolder(R.layout.update_rent))
                        .setExpanded(true,1500).create();
                View view=dialogPlus.getHolderView();
                EditText name=view.findViewById(R.id.uprentname);
                //EditText location=view.findViewById(R.id.uprentloc);
                EditText  price=view.findViewById(R.id.uprentprice);
                //EditText rating=view.findViewById(R.id.uprentrating);
              // EditText status=view.findViewById(R.id.upcabstatus);
                EditText time=view.findViewById(R.id.uprenttime);
                EditText pic=view.findViewById(R.id.uprentpic);
                EditText number=view.findViewById(R.id.uprentnum);
                Button btnup=view.findViewById(R.id.btnrentup);
                name.setText(model.getName());
               // location.setText(model.getLocation());
                price.setText(model.getPrice());
                //rating.setText(model.getRating());

                time.setText(model.getTime());
                pic.setText(model.getPic());
                number.setText(model.getNumber());
                dialogPlus.show();

                btnup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(name.getText().toString())&&!TextUtils.isEmpty(price.getText().toString())&&!TextUtils.isEmpty(time.getText().toString())&&!TextUtils.isEmpty(pic.getText().toString())&&!TextUtils.isEmpty(number.getText().toString())&&number.length()==10) {
                            Map<String, Object> map = new HashMap<>();
                            //name,location,price,rating,status,time,pic,number
                            map.put("name", name.getText().toString());
                            //map.put("location",location.getText().toString());
                            map.put("price", price.getText().toString());
                            // map.put("rating",rating.getText().toString());

                            map.put("time", time.getText().toString());
                            map.put("pic", pic.getText().toString());
                            map.put("number", number.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("rent").child(getRef(position).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.rentname.getContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(holder.rentname.getContext(), "Error while Updated", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else if (number.length()!=10) {
                            number.setError("enter valid phonenumber");
                            number.requestFocus();
                        }else {
                           name.setError("enter name");
                           time.setError("enter time");
                           price.setError("enter price");
                           pic.setError("enter pic");
                        }
                    }
                });
            }
        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.rentname.getContext());
                builder.setTitle("Are You Sure");
                builder.setMessage("Deleted data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("rent").child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.rentname.getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.rentname.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.rentitem,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView rentpic;
        TextView rentname;
        Button edit,del;

        public myViewHolder(@NonNull View itemView) {

            super(itemView);
            rentpic=(ShapeableImageView)itemView.findViewById(R.id.rentpic) ;
            rentname=(TextView) itemView.findViewById(R.id.rentname);
            edit=(Button)itemView.findViewById(R.id.btnrentedit);
            del=(Button) itemView.findViewById(R.id.btnrentdelete);
        }
    }
}
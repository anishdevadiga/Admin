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

public class HotelAdapter  extends FirebaseRecyclerAdapter<Hotelmodel,HotelAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HotelAdapter(@NonNull FirebaseRecyclerOptions<Hotelmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull Hotelmodel model) {
        holder.hotelname.setText(model.getHotelname());
        Glide.with(holder.hotelimg.getContext()).load(model.getHotelpic()).placeholder(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(com.google.firebase.storage.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.hotelimg);
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.hotelimg.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_hotel))
                        .setExpanded(true,1500).create();

                View view=dialogPlus.getHolderView();
                EditText hotelname=view.findViewById(R.id.uphotelname);
               // EditText hotelratings=view.findViewById(R.id.uphotelrating);
                EditText hoteldescription=view.findViewById(R.id.uphoteldesc);
                EditText  hoteltime=view.findViewById(R.id.uphoteltime);
               // EditText hotelroomstatus=view.findViewById(R.id.uphotelstatus);
                //EditText hotellocation=view.findViewById(R.id.uphotelloc);
                EditText hotelpic=view.findViewById(R.id.uphotelpic);
                Button btnup=view.findViewById(R.id.btnup);
                 hotelname.setText(model.getHotelname());
                 hoteldescription.setText(model.getHoteldescription());
                 //hotelratings.setText(model.getHotelratings());
                 //hotellocation.setText(model.getHotellocation());
                // hotelroomstatus.setText(model.getHotelroomstatus());
                 hoteltime.setText(model.getHoteltime());
                 hotelpic.setText(model.getHotelpic());
                dialogPlus.show();
                btnup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(hotelname.getText().toString())&&!TextUtils.isEmpty(hoteldescription.getText().toString())&&!TextUtils.isEmpty(hoteltime.getText().toString())&&!TextUtils.isEmpty(hotelpic.getText().toString())) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("hotelname", hotelname.getText().toString());
                            //map.put("hotelratings", hotelratings.getText().toString());
                            map.put("hoteldescription", hoteldescription.getText().toString());
                            map.put("hoteltime", hoteltime.getText().toString());
                            // map.put("hotelroomstatus", hotelroomstatus.getText().toString());
                            // map.put("hotellocation", hotellocation.getText().toString());
                            map.put("hotelpic", hotelpic.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("Hotel").child(getRef(position).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.hotelname.getContext(), "Updated", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Toast.makeText(holder.hotelname.getContext(), "Error while Updating", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });
                        }else{
                            hotelname.setError("enter value");
                            hoteldescription.setError("enter value");
                            hoteltime.setError("enter value");
                            hotelpic.setError("enter value");
                        }
                    }
                });
            }
        });
        holder.btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.hotelname.getContext());
                builder.setTitle("Are You Sure");
                builder.setMessage("Deleted data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Hotel").child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.hotelname.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hotelecyleitem,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView hotelimg;
        TextView hotelname;
        Button btnedit,btndel;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelimg=(ShapeableImageView) itemView.findViewById(R.id.hotelimg);
            hotelname=(TextView)itemView.findViewById(R.id.hotelname);
            btnedit=(Button) itemView.findViewById(R.id.btnhoteledit);
            btndel=(Button)itemView.findViewById(R.id.btnhoteldelete);

        }
    }
}

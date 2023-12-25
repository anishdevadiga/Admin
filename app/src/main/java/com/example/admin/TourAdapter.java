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

public class TourAdapter extends FirebaseRecyclerAdapter<Tourmodel,TourAdapter.myViewHolder>{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TourAdapter(@NonNull FirebaseRecyclerOptions<Tourmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView")  final int position, @NonNull Tourmodel model) {
        holder.tourname.setText(model.getPlacename());
        Glide.with(holder.tourimg.getContext()).load(model.getPlacepicurl())
                .placeholder(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.tourimg);

        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.tourimg.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_tour_popup))
                        .setExpanded(true, 1750).create();

                View view = dialogPlus.getHolderView();

                EditText placename = view.findViewById(R.id.edittourname);
                EditText placedescription = view.findViewById(R.id.edittourdesc);
               // EditText placelocationurl = view.findViewById(R.id.edittourlocation);
                EditText placetime = view.findViewById(R.id.edittourtime);
                EditText placepicurl = view.findViewById(R.id.edittourpic);

                Button btnupdate = view.findViewById(R.id.btnupdate);
                placename.setText(model.getPlacename());
                placedescription.setText(model.getPlacedescription());
                //placelocationurl.setText(model.getPlacelocationurl());
                placetime.setText(model.getPlacetime());
                placepicurl.setText(model.getPlacepicurl());
                dialogPlus.show();

                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(placename.getText().toString())&&!TextUtils.isEmpty(placedescription.getText().toString())&&!TextUtils.isEmpty(placetime.getText().toString())&&!TextUtils.isEmpty(placepicurl.getText().toString()))
                        {
                            Map<String, Object> map = new HashMap<>();
                            map.put("placename", placename.getText().toString());
                            map.put("placedescription", placedescription.getText().toString());
                           // map.put("placelocationurl", placelocationurl.getText().toString());
                            map.put("placetime", placetime.getText().toString());
                            map.put("placepicurl", placepicurl.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("Tourist_places").child(getRef(position).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.tourname.getContext(), "Data Updated ", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                            ;

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Toast.makeText(holder.tourname.getContext(), "Error while Updating ", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                            ;
                                        }
                                    });
                        }else {
                            placedescription.setError("Enter value");
                            placename.setError("Enter value");
                           // placelocationurl.setError("Enter val");
                            placepicurl.setError("Enter value");
                            placetime.setError("Enter Value");
                        }
                    }
                });
            }
        });
        holder.btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.tourname.getContext());
                builder.setTitle("Are You Sure");
                builder.setMessage("Deleted data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Tourist_places").child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.tourname.getContext(), "Cancelled ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tourview= LayoutInflater.from(parent.getContext()).inflate(R.layout.tourrecyleitem,parent,false);

        return  new myViewHolder(tourview);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView tourimg;
        TextView tourname;
        Button btnedit,btndel;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tourimg=(ShapeableImageView)itemView.findViewById(R.id.tourimg1);
            tourname=(TextView)itemView.findViewById(R.id.tourname);
            btnedit=(Button)itemView.findViewById(R.id.btntouredit);
            btndel=(Button)itemView.findViewById(R.id.btntourdelete);
        }
    }


}

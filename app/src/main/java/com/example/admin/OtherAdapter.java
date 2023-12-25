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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class OtherAdapter extends FirebaseRecyclerAdapter<Othermodel,OtherAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OtherAdapter(@NonNull FirebaseRecyclerOptions<Othermodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView")  final int position, @NonNull Othermodel model) {
        holder.name.setText(model.getName());
        Glide.with(holder.otherpic.getContext()).load(model.getPic()).placeholder(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(com.google.firebase.storage.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.otherpic);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.otherpic.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_other)).setExpanded(true,1500).create();

                View view=dialogPlus.getHolderView();
                EditText name=view.findViewById(R.id.upothername);
                EditText time=view.findViewById(R.id.upothertime);
                EditText pic=view.findViewById(R.id.upotherpic);
               // EditText rating=view.findViewById(R.id.uprentrating);
               // EditText location=view.findViewById(R.id.upotherlocation);
                EditText desc=view.findViewById(R.id.upotherdesc);
                Button btnup=view.findViewById(R.id.btnup);
                name.setText(model.getName());
                time.setText(model.getTime());
                pic.setText(model.getPic());
              //  if(rating!=null){
                //rating.setText(model.getRating());}
                //location.setText(model.getLocation());
                desc.setText(model.getDescription());
                dialogPlus.show();
                //name,rating,time,location,description,pic
                btnup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(name.getText().toString())&&!TextUtils.isEmpty(time.getText().toString())&&!TextUtils.isEmpty(desc.getText().toString())&&!TextUtils.isEmpty(pic.getText().toString())) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("name", name.getText().toString());
                            //  if(rating!=null){
                            //  map.put("rating",rating.getText().toString());}
                            map.put("time", time.getText().toString());
                            //map.put("location",location.getText().toString());
                            map.put("description", desc.getText().toString());
                            map.put("pic", pic.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("other").child(getRef(position).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.name.getContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Toast.makeText(holder.name.getContext(), "Data Update Failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }else{
                            name.setError("Enter name");
                            desc.setError("enter description");
                            time.setError("enter time");
                            pic.setError("enter pic link");
                        }
                    }
                });
            }
        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are You Sure");
                builder.setMessage("Deleted data can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("other").child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.name.getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.other,parent,false);
        return new OtherAdapter.myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{
       ShapeableImageView otherpic;
       TextView name,rating,time,location,description,pic;
       Button edit,del;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            otherpic=(ShapeableImageView) itemView.findViewById(R.id.otherpic);
            name=(TextView) itemView.findViewById(R.id.othername);

            edit=(Button) itemView.findViewById(R.id.btnotheredit);
            del=(Button) itemView.findViewById(R.id.btnotherdelete);

        }
    }

}

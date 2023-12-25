package com.example.admin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.text.Layout;
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

public class CabAdapter extends FirebaseRecyclerAdapter<CabModel,CabAdapter.myView> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CabAdapter(@NonNull FirebaseRecyclerOptions<CabModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myView holder, @SuppressLint("RecyclerView") final int position, @NonNull CabModel model) {
        holder.cabname.setText(model.getCabname());
        Glide.with(holder.cabpic.getContext()).load(model.getCabpic()).placeholder(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(com.google.firebase.storage.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.cabpic);
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.cabpic.getContext()).setContentHolder(new ViewHolder(R.layout.update_cab))
                        .setExpanded(true,1200).create();
                View view =dialogPlus.getHolderView();

                EditText cabname=view.findViewById(R.id.upcabname);
                EditText cabnumber=view.findViewById(R.id.upcabnum);
                EditText cabprice=view.findViewById(R.id.upcabprice);
                EditText cabpic=view.findViewById(R.id.upcabpic);
              //  EditText cabrating=view.findViewById(R.id.upcabrating);
               // EditText cabstatus=view.findViewById(R.id.upcabstatus);
                Button btnup=view.findViewById(R.id.btncabup);
                cabname.setText(model.getCabname());
                cabpic.setText(model.getCabpic());
                cabnumber.setText(model.getCabnumber());
                cabprice.setText(model.getCabprice());
               // cabrating.setText(model.getCabrating());
                //cabstatus.setText(model.getCabstatus());
                dialogPlus.show();
                btnup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(cabname.getText().toString())&&!TextUtils.isEmpty(cabpic.getText().toString())&&!TextUtils.isEmpty(cabnumber.getText().toString())&&cabnumber.length()==10&&!TextUtils.isEmpty(cabprice.getText().toString())) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("cabname", cabname.getText().toString());
                            map.put("cabnumber", cabnumber.getText().toString());
                            map.put("cabprice", cabprice.getText().toString());
                            // map.put("cabrating",cabrating.getText().toString());
                            map.put("cabpic", cabpic.getText().toString());
                            //map.put("cabstatus",cabstatus.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("Cab")
                                    .child(getRef(position).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.cabname.getContext(), "Updated", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Toast.makeText(holder.cabname.getContext(), "Error While Updating", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();

                                        }
                                    });
                        }else if (cabnumber.length()!=10) {
                            cabnumber.setError("Enter valid phone number");
                            cabnumber.requestFocus();

                        } else{
                            cabname.setError("Enter all fields");
                            cabnumber.setError("Enter all fields");
                            cabprice.setError("Enter all fields");
                            cabpic.setError("Enter all fields");
                        }
                        }


                });
            }
        });
        holder.btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.cabname.getContext());
                builder.setTitle("Are You Sure");
                builder.setMessage("Deleted data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Cab").child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.cabname.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });

    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cabitem,parent,false);
        return new myView(view);
    }

    class myView extends RecyclerView.ViewHolder{
        ShapeableImageView cabpic;
        TextView cabname;
        Button btnedit,btndel;

        public myView(@NonNull View itemView) {
            super(itemView);
            cabpic=(ShapeableImageView)itemView.findViewById(R.id.cabpic);
            cabname=(TextView)itemView.findViewById(R.id.cabname);
            btnedit=(Button) itemView.findViewById(R.id.btncabedit);
            btndel=(Button) itemView.findViewById(R.id.btncabdelete);


        }
    }
}

package com.example.myproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowActivity_image_viws extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<Module_image> list;
    private Myadapter adapter;
    private FirebaseFirestore db;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    ProgressDialog dialag;
    SwipeRefreshLayout swipe;
    public String sametitLe1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_viws);
        if (!isConnect(this)) {
            showInternetDialog();
        }else{
        recyclerView = findViewById(R.id.recycler_show_image);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new Myadapter(this, list);
        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            sametitLe1 = bundle.getString("sametitle");

        } else {
            sametitLe1 = null;
        }

        swipe = findViewById(R.id.swipn);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ShowActivity_image_viws.this.show_image_fierstore();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                }, 500);
                Toast.makeText(ShowActivity_image_viws.this, "Refreshing...", Toast.LENGTH_SHORT).show();
            }
        });
        dialag = new ProgressDialog(this);

        dialag.setTitle("Upload Image...");
        dialag.setMessage("Place wait!");
        dialag.show();
        //show_Image_fierbase();
        show_image_fierstore();
    }

}
    public void show_Image_fierbase(){//realtimedata base
        dialag.show();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Module_image modul=dataSnapshot.getValue(Module_image.class);
                    list.add(modul);
                    dialag.dismiss();
                }
                adapter.notifyDataSetChanged();
            }

            @Override




            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowActivity_image_viws.this, "Check net work!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void show_image_fierstore(){
        //System.err.println(admain_maths_module.getTitle()+"seeeeeeeeeeeeeeeeeeeeeeeeeeeee");

        db.collection("Image_upload").whereEqualTo("equaltitle",sametitLe1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                dialag.dismiss();
                for (DocumentSnapshot snapshot : task.getResult()) {
                    Module_image module = new Module_image(snapshot.getString("Image_url"), snapshot.getString("Image_Title"),snapshot.getString("Image_id"));
                    list.add(module);


                }
                adapter.notifyDataSetChanged();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialag.dismiss();
                Toast.makeText(ShowActivity_image_viws.this,"somthing rong",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showInternetDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view= LayoutInflater.from(this).inflate(R.layout.no_internet_dialog,findViewById(R.id.nointernetlayout));
        view.findViewById(R.id.tryagin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnect(ShowActivity_image_viws.this)){
                    showInternetDialog();
                }else{
                    startActivity(new Intent(getApplicationContext(),ShowActivity_image_viws.class));
                }
            }
        });
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private boolean isConnect(ShowActivity_image_viws showActivity_image_viws){
        ConnectivityManager connectivityManager=(ConnectivityManager) showActivity_image_viws.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificonnect=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilconnect=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wificonnect !=null && wificonnect.isConnected()) || (mobilconnect !=null && mobilconnect.isConnected());


    }
    }


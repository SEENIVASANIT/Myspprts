package com.example.myproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Admain_show_all_match_data extends AppCompatActivity  {
    Toolbar toolbar;

    // Switch switchCompat;
private FirebaseFirestore db;
private Admain_maths_adpater adpater;
private List<Admain_maths_module> list;
    ProgressDialog dialag;
    SwipeRefreshLayout swipe;
    FloatingActionButton floatingActionButton;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admain_show_all_match_data);
        if (!isConnect(this)) {
            showInternetDialog();
        }else {
            // ActionBar actionBar=getSupportActionBar();

            toolbar = findViewById(R.id.admain_maths_serch_tool);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();


//        actionBar.setDisplayHomeAsUpEnabled(true);

            RecyclerView recyclerView = findViewById(R.id.show_admain_maths);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            db = FirebaseFirestore.getInstance();
            list = new ArrayList<>();
            adpater = new Admain_maths_adpater(this, list);
            recyclerView.setAdapter(adpater);
            floatingActionButton = findViewById(R.id.add_mathss);
            dialag = new ProgressDialog(this);
            dialag.setTitle("showing data");
            dialag.setMessage("Please wait");
            swipe = findViewById(R.id.swip);
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                // @SuppressLint("ResourceAsColor")
                //@RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onRefresh() {
                    //swipe.setProgressBackgroundColorSchemeColor(R.color.refersh);

                    Admain_show_all_match_data.this.show_maths_data();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipe.setRefreshing(false);
                        }
                    }, 500);

                    Toast.makeText(Admain_show_all_match_data.this, "Refreshing...", Toast.LENGTH_SHORT).show();
                }
            });
            dialag.show();

            //   switchCompat=findViewById(R.id.win_switch);
            // SharedPreferences sharedPreferences=getSharedPreferences("save",MODE_PRIVATE);

            //switchCompat.setChecked(sharedPreferences.getBoolean("value",true));

            //switchCompat.setOnClickListener(new View.OnClickListener() {

            //  @Override
            //public void onClick(View view) {
            //  if(switchCompat.isChecked())
            //{
            //  SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();
            //editor.putBoolean("value",true);
            //editor.apply();
            //switchCompat.setChecked(true);
            // }
            //else {
            //  SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();
            //editor.putBoolean("value",false);
            //editor.apply();
            //switchCompat.setChecked(false);
            //        }
            //  }
            // });


            show_maths_data();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    admain_macths_details();
                }
            });


        }
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serch_matchs,menu);
        MenuItem item=menu.findItem(R.id.serch_matchs);

        SearchView searchView= (SearchView) item.getActionView();
        //SearchView.SearchAutoComplete searchAutoComplete=(SearchView.SearchAutoComplete)searchView.findViewById(R.id.show_admain_maths);

        searchView.setQueryHint("Search Title");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);

                //Toast.makeText(Admain_show_all_match_data.this,"No data found",Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);

               //Toast.makeText(Admain_show_all_match_data.this,"No data found",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void search(String m_title){
        dialag.setTitle("Searching data...");
        //dialag.show();
        db.collection("MATHS_DATA").orderBy("title").startAt(m_title).endAt(m_title+"~").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                dialag.dismiss();
                for(DocumentSnapshot snapshot :task.getResult()){
                    Admain_maths_module module=new Admain_maths_module(snapshot.getString("title"),snapshot.getString("maths"),snapshot.getString("id"));
                    list.add(module);


                }
                RecyclerView recyclerView = findViewById(R.id.show_admain_maths);
                recyclerView.setHasFixedSize(true);
                adpater=new Admain_maths_adpater(Admain_show_all_match_data.this,list);

                recyclerView.setAdapter(adpater);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialag.show();
                Toast.makeText(Admain_show_all_match_data.this,"No data found",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void show_maths_data(){
        //dialag.setTitle("SHOW MATHS DATA");

        db.collection("MATHS_DATA").orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                dialag.dismiss();

                for(DocumentSnapshot snapshot :task.getResult()){
                    Admain_maths_module module=new Admain_maths_module(snapshot.getString("title"),snapshot.getString("maths"),snapshot.getString("id"));
                    list.add(module);


                }
                adpater.notifyDataSetChanged();
                Collections.reverse(list);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Admain_show_all_match_data.this,"somthing rong",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void admain_macths_details(){
        Toast.makeText(this,"MATCH DETALIS...",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,Admain_macth_text.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent(this,Admain_main_page.class);
        startActivity(intent);
    }
    private void showInternetDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view= LayoutInflater.from(this).inflate(R.layout.no_internet_dialog,findViewById(R.id.nointernetlayout));
        view.findViewById(R.id.tryagin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnect(Admain_show_all_match_data.this)){
                    showInternetDialog();
                }else{
                    startActivity(new Intent(getApplicationContext(),Admain_show_all_match_data.class));
                }
            }
        });
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private boolean isConnect(Admain_show_all_match_data admain_show_all_match_data){
        ConnectivityManager connectivityManager=(ConnectivityManager) admain_show_all_match_data.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificonnect=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilconnect=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wificonnect !=null && wificonnect.isConnected()) || (mobilconnect !=null && mobilconnect.isConnected());


    }

}

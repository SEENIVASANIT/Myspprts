package com.example.myproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.myproject.Admain.Admain_Mainpage;

public class Admain_main_page extends AppCompatActivity {
    Toolbar toolbar;
    private static final int POST_NOTIFICATIONS=123;
    Button admain_macth_detail,admain_show_user_data,admain_upload_things;
private static final int TIME_INTERVAL=2000;
public long back_to_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admain_main_page);



        toolbar = findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        admain_macth_detail = findViewById(R.id.matchs);
        admain_show_user_data = findViewById(R.id.players_data);
        admain_upload_things = findViewById(R.id.sports_things);


        admain_macth_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admain_macths_details();

            }
        });
        admain_show_user_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admain_show_user_datas();
            }
        });
        admain_upload_things.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

    }
private final ActivityResultLauncher<String> requestPermissionLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted->{
    if (isGranted){
        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
    }else {
        Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
    }
    });
   @RequiresApi(33)
           private void askNotificationPermission(){
       if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)== PackageManager.PERMISSION_GRANTED){
           Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
       }else if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_NOTIFICATION_POLICY)){

       }else {
           requestPermissionLauncher.launch(Manifest.permission.ACCESS_NOTIFICATION_POLICY);
       }
   }

    int Exit_count=0;

    @Override
    public void onBackPressed() {
        if(back_to_exit +TIME_INTERVAL > System.currentTimeMillis()){

            this.finish();
        }else{
            Toast.makeText(this,"Pross agin exit app",Toast.LENGTH_SHORT).show();
        }
        back_to_exit=System.currentTimeMillis();
        //finishAffinity();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admain_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.user) {
            Toast.makeText(this, "gotoadmainpage", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,Notification.class);
            startActivity(intent);
        } else if (id == R.id.abort) {
            Toast.makeText(this, "CLUB Full Details..." , Toast.LENGTH_SHORT).show();
            open_abort_club_page();
        }else if(id==R.id.user1){
            Intent intent=new Intent(this,Common_login.class);
            startActivity(intent);
        }
        else if(id==R.id.user2){
            Intent intent=new Intent(this, Admain_Mainpage.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void open_abort_club_page() {
        Intent intent = new Intent(this, Image_tech_view.class);
        startActivity(intent);
    }
    public void admain_macths_details(){
        Toast.makeText(this,"MATCH DETALIS...",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,Admain_show_all_match_data.class);
        startActivity(intent);
    }
    public void admain_show_user_datas(){
        Toast.makeText(this,"MATCH DETALIS...",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,Switch_button.class);
        startActivity(intent);

    }
    public void uploadImage(){
        Intent intent=new Intent(this,ChoseImage.class);
        startActivity(intent);
    }

}
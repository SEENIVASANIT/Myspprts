package com.example.myproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;


public class Switch_button extends AppCompatActivity {
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Toast.makeText(Switch_button.this, "Complect", Toast.LENGTH_SHORT).show();
        }
    };
    private static final int PERMISSION_STORAGE_CODE = 1000;
    Toolbar toolbar;
TextView date,count;
EditText call,urls,urls1;
public int a,b,c,q;
public String s,val;
int coun=0;
Button click,down,down1;
    private static final int TIME_INTERVAL = 2000;
    public long back_to_exit;
String dudate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_button);
        coun++;
        toolbar = findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
date=findViewById(R.id.date1);
call=findViewById(R.id.match_call);
click=findViewById(R.id.callb);
urls=findViewById(R.id.url_d);
urls1=findViewById(R.id.url_d1);
count=findViewById(R.id.counter);

FirebaseMessagingService firebaseMessagingService=new FirebaseMessagingService();
val= String.valueOf(firebaseMessagingService.getCount());
a=firebaseMessagingService.getCount();
click.setEnabled(false);





System.out.println("COUNT"+a);
down=findViewById(R.id.down_url);
down1=findViewById(R.id.down_url1);
down1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String geturl=urls1.getText().toString();
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(geturl));
        String title= URLUtil.guessFileName(geturl,null,null);
        request.setDescription("DOWNLOADING....");
        String cookie = CookieManager.getInstance().getCookie(geturl);
        request.addRequestHeader("cookie",cookie);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
        DownloadManager downloadManager=(DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
        Toast.makeText(Switch_button.this, "DOWNLOADE cOMPLicte", Toast.LENGTH_SHORT).show();
    }
});
down.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String []permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);

                    }else {
                        startDownload();
                    }
                }else {
                    startDownload();
                }
            }
        };
        Thread thread=new Thread(runnable);
        thread.start();

    }
});
click.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String number= call.getText().toString();
       // Intent intent=new Intent(Intent.ACTION_CALL);
       // intent.setData(Uri.parse("tel:"+number));
        Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+Uri.encode(number)));
        startActivity(intent);
    }
});
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int MONTH=calendar.get(Calendar.MONTH);
                int YEAR=calendar.get(Calendar.YEAR);
                int DAY=calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog=new DatePickerDialog(Switch_button.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1=i1+1;
                        date.setText(i2+"/"+i1+"/"+i);
                        dudate=i2+"/"+i1+"/"+i;
                    }
                },YEAR,MONTH,DAY);
                datePickerDialog.show();
            }
        });

    }









    private void startDownload() {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                String url=urls.getText().toString().toString();
                DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

                request.setDescription("IMAGE DOWN....");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());
                DownloadManager manager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                String title= URLUtil.guessFileName(url,null,null);
                request.setDescription("DOWNLOADING....");
                String cookie = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie",cookie);
                manager.enqueue(request);
                Toast.makeText(Switch_button.this, "DOWNLOADE cOMPLicte", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessage(0);
            }

        };
        Thread thread=new Thread(runnable);
        thread.start();


    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISSION_STORAGE_CODE:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startDownload();
                }else {
                    Toast.makeText(this, "Permission no", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
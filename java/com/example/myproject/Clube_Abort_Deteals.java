package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Clube_Abort_Deteals extends AppCompatActivity {
Button facebok,youtube,instagram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clube_abort_deteals);
        facebok=findViewById(R.id.open_facebook);
        youtube=findViewById(R.id.open_youtube);
        instagram=findViewById(R.id.open_instagram);
        facebok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.facebook.com/SivakasiTownHockeyClub/");
            }


        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.youtube.com/channel/UC3hPZNZUwiS_revAM0s6y9g");

            }


        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.instagram.com/");
            }


        });


    }
    private void gotourl(String URL) {
        Uri url= Uri.parse(URL);
        startActivity(new Intent(Intent.ACTION_VIEW,url));
    }
}

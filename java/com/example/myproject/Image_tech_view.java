package com.example.myproject;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class Image_tech_view extends AppCompatActivity {
WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_tech_view);
        Bundle bundle = getIntent().getExtras();
//        String image_url=bundle.getString("url");
        webView=(WebView) findViewById(R.id.web);
        webView.loadUrl("https://firebasestorage.googleapis.com/v0/b/mysports-9a0b3.appspot.com/o/1657266364589.jpg?alt=media&token=014ef18b-9bdb-45e5-964e-346f8b539e4e");
    }
}
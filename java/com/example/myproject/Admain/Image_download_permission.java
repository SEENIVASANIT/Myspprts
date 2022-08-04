package com.example.myproject.Admain;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

public interface Image_download_permission {
    @SuppressLint("MissingSuperCall")
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}

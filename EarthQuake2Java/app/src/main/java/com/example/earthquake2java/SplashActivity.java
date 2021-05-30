package com.example.earthquake2java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         Handler handler=new Handler(Looper.getMainLooper());
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {
                 Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                 startActivity(intent);
             }
         },5000);


    }
}
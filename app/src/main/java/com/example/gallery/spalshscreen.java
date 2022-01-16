package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class spalshscreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalshscreen);


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
            Intent home=new Intent(spalshscreen.this,MainActivity.class);
            startActivity(home);
            finish();
        }
        },SPLASH_TIME_OUT);

    }

}
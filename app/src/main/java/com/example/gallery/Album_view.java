package com.example.gallery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Album_view extends AppCompatActivity {

    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);


        //back button code start
        back =findViewById(R.id.back_btn);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Album_view.this, MainActivity.class));
            }
        });


    }


    //back button handlling

    @Override
    public void onBackPressed() {
            super.onBackPressed();
        }

    //backbutton code ends
}
package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class about extends AppCompatActivity {

    Window window;
    ImageButton back;
    TextView version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //window=this.getWindow();
        this.getWindow().setStatusBarColor(0xff012105);
        String versionName = BuildConfig.VERSION_NAME;
        version=findViewById(R.id.verSion);
        version.setText(versionName);
        //int versionCode = BuildConfig.VERSION_CODE;
        //version.setText(versionCode);

        /*back button code start
        back =findViewById(R.id.back_btn);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
                //startActivity(new Intent(about.this, MainActivity.class));
            }
        });

        //back button ends*/
    }
}
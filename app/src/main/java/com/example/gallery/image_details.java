package com.example.gallery;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class image_details extends AppCompatActivity {
    ImageButton back;
    TextView text_path;
    TextView text_size;
    TextView text_name;
    TextView text_modified_date;
    int position;
    String img_link;
    long img_size;
    String img_name;
    File file;
    Date mod_date;
    DateFormat simple;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        text_path=findViewById(R.id.img_path);
        text_size=findViewById(R.id.img_size);
        text_name=findViewById(R.id.img_name);
        text_modified_date=findViewById(R.id.img_modified_date);
        //------------ back
        //back button code start
        back =findViewById(R.id.back_btn_d);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
                //startActivity(new Intent(full_view.this, MainActivity.class));
            }
        });

        //back button ends
        //------------ back

        //----------- bundle/image from previous activity
        Bundle bundele=getIntent().getExtras();
        if(bundele!=null)
        {
            img_link=bundele.getString("img_link");
            file=new File(img_link);
            img_link=file.getAbsolutePath();  //in Bytes
            img_size=file.length()/1024;  // in KiloBytes
            //img_size=img_size/1024;
            img_name=file.getName();
            long xyz=file.lastModified();
            simple= new SimpleDateFormat("dd MMM YYYY HH:mm:ss");
            mod_date=new Date(xyz);



        }
        else
        {
            img_link="not found!";
        }

        text_path.setText(img_link);
        text_size.setText(img_size+" KB");
        text_name.setText(img_name);
        text_modified_date.setText(simple.format(mod_date));

        //------------ bundle/image from previous activity*/

    }
}
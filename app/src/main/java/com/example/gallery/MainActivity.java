package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.view.MenuItem;
import android.view.View;

import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener,onItemClickListener {

    ArrayList<File> imageFile;
    com.example.gallery.CustomAdapter customAdapter;
    List<String> mList;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    int rows;
    private  static final String SHARED_PREF_NAME="mypin";
    private  static  final String NUM_OF_ROWS="rows";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.rview);
        CheckUserPermissions();
        mList=new ArrayList<>();

        //sharedpreference values
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        rows=sharedPreferences.getInt(NUM_OF_ROWS,3);
        if(rows==0)
        {
            rows=1;
        }

        //------------- Gallery text
        //code to redirect to hidden_home page
        TextView textView= findViewById(R.id.text_logo);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(MainActivity.this,hidden_home.class);
                startActivity(intent);
                return false;
            }
        });

        //------------- Gallery text ends


    }




    //----------- permission
    void CheckUserPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        display();  // init the contact list

    }



    //get access to location permission
   final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                display();  // init the contact list
            } else {
                // Permission Denied
                Toast.makeText(this, "Please allow storage access ", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    //----------- permission\


    private ArrayList<File> findImage(File file) {


        ArrayList<File>imageList=new ArrayList<>();

        File[] imageFile=file.listFiles();

        assert imageFile != null;
        for(File singling: imageFile)
        {
            if(singling.isDirectory() && !singling.isHidden()){

                imageList.addAll(findImage(singling));
            }
            else
            {
                if(singling.getName().endsWith(".jpg") ||
                    singling.getName().endsWith(".jpeg") ||
                    singling.getName().endsWith(".png") ||
                        singling.getName().endsWith(".webp")
                )
                {
                    imageList.add(singling);
                }
            }
        }

        return imageList;
    }

    //----------display

    private void display() {
        imageFile =findImage(Environment.getExternalStorageDirectory());


        for(int j = 0; j< imageFile.size(); j++)
        {
            mList.add(String.valueOf(imageFile.get(j)));
            customAdapter=new com.example.gallery.CustomAdapter(mList,this);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this,rows));
        }
    }

    /* ------------------ */


    //popup menu
    public void show_item(View v)
            {
                PopupMenu popup=new PopupMenu(this,v);
                popup.setOnMenuItemClickListener(this);
                popup.inflate(R.menu.xmlmenu);
                popup.show();
            }



    //Menu items redirection
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
                {
                    case R.id.item_about:
                        Intent intent=new Intent(MainActivity.this,about.class);
                        startActivity(intent);
                      return true;
                    case R.id.settings:
                        Intent intent1=new Intent(MainActivity.this,settings.class);
                        startActivity(intent1);
                        return true;
                    default:
                        return false;
                }
    }

    @Override
    public void onClick(int position) {
        //Toast.makeText(this,"Position"+position,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,full_view.class);
        intent.putExtra("image_link",imageFile.get(position).toString());
        intent.putExtra("image_name",imageFile.get(position).getName());
        intent.putExtra("pos",position);
        intent.putExtra("image_list",imageFile);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {

        finish();
    }

}
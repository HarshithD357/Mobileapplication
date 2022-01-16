package com.example.gallery;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Add_image extends AppCompatActivity implements onItemClickListener {

    List<String> selectedImage;
    ArrayList<File> imageFile;
    com.example.gallery.CustomAdapter customAdapter;
    List<String> mList;
    RecyclerView recyclerView;
    Button add_btn;
    File f;
    String ffname;
    String to;
    //boolean seleced=false;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        recyclerView=findViewById(R.id.recycler_view2);
        mList=new ArrayList<>();
        display();
        selectedImage=new ArrayList<>();
        add_btn=findViewById(R.id.button2);
        imageView=findViewById(R.id.img);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(selectedImage.size()>0)
                {
                    //String hidden_folder_url="/document/primary:.galley dir/";
                    String hidden_folder_url="/storage/emulated/0/.gallery dir/";
                    for(int k = 0; k<selectedImage.size(); k++)
                    {
                        //Toast.makeText(getApplicationContext(),"url:"+selectedImage.get(k),Toast.LENGTH_LONG).show();
                        try {

                            File f=new File(selectedImage.get(k));
                            String img_name=f.getName();
                            img_name= new StringBuilder().append("/storage/emulated/0/.gallery dir/").append(img_name).toString();
                            Path temp = Files.move
                                    (Paths.get(selectedImage.get(k).toString()),Paths.get(img_name));
                            //to=hidden_folder_url.concat(ffname);
                            if(temp!=null)
                            {
                                Toast.makeText(getApplicationContext(),"Image(s) are protected successfuly",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Unable to protect Image(s)",Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"Err",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                    selectedImage=null;
                    finish();
                    startActivity(getIntent());

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please select Image(s) to hide",Toast.LENGTH_LONG).show();
                }

            }
        });

    }



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
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        }
    }

    /* ------------------ */

    @Override
    public void onClick(int position) {
        boolean isRemoved=false;
        String temp=imageFile.get(position).toString();
        for(int i=0;i<selectedImage.size();i++)
        {
            if((selectedImage.get(i)).equals(temp))
            {
                selectedImage.remove(temp);
                isRemoved=true;
                break;
            }
        }
        if(!isRemoved)
        {
            selectedImage.add(temp);
            //imageView.setAlpha(0.5F);
            //imageView.setColorFilter(Color.argb(125, 233, 230, 230));
        }
        if(selectedImage.size()>=0)
        {
            add_btn.setBackgroundColor(0xff0ceda2);
            add_btn.setTextColor(0xff000000);
        }
        else
        {
            add_btn.setBackgroundColor(0xffb3bdba);
            add_btn.setTextColor(0xff000000);
        }


    }

}
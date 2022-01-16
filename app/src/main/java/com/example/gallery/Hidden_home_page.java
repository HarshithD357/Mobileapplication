package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;



public class Hidden_home_page extends AppCompatActivity implements onItemClickListener{

    ArrayList<File> imageFile;
    com.example.gallery.CustomAdapter customAdapter;
    List<String> mList;
    RecyclerView recyclerView;
    ImageButton addImage;
    Button unhide_btn;
    List<String> selectedImage;
    TextView text_on_selected;
    RecyclerView recyclerView23;
    View vvv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_home_page);

        addImage=findViewById(R.id.add_image);
        unhide_btn=findViewById(R.id.un_btn);
        recyclerView=findViewById(R.id.recycler_view);
        mList=new ArrayList<>();
        display();
        selectedImage=new ArrayList<>();
        //text_on_selected=findViewById(R.id.slct);


        //create file
        /*boolean directory_status=check_directory_status();
        boolean status=true;
        if(!file.exists())
        {
            Toast.makeText(getApplicationContext(),"Directory does not exist, create it",
                    Toast.LENGTH_LONG).show();
            file.mkdir();
            Toast.makeText(getApplicationContext(),"Directory created ",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Failed to create Directory",
                    Toast.LENGTH_LONG).show();
        }*/


        //add image button
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Hidden_home_page.this,Add_image.class);
                startActivity(intent);
            }
        });



        unhide_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(selectedImage.size()>0)
                {
                    //String hidden_folder_url="/document/primary:.galley dir/";
                    String to="/storage/emulated/0/Pictures/";
                    for(int k = 0; k<selectedImage.size(); k++)
                    {
                        //Toast.makeText(getApplicationContext(),"url:"+selectedImage.get(k),Toast.LENGTH_LONG).show();

                        try {

                            File f=new File(selectedImage.get(k));
                            String img_name=f.getName();
                            img_name= new StringBuilder().append("/storage/emulated/0/Pictures/").append(img_name).toString();
                            Path temp = Files.move(Paths.get(selectedImage.get(k).toString()),Paths.get(img_name));
                            //to=hidden_folder_url.concat(ffname);
                            if(temp!=null)
                            {
                                Toast.makeText(getApplicationContext(),"Image(s) are unhided successfuly",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Unable to unhide Image(s)",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(),"Please select Image(s) to unhide",Toast.LENGTH_LONG).show();
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
            String fname=singling.getName();
            if(singling.isHidden() && fname.equals(".gallery dir")){

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
            customAdapter=new com.example.gallery.CustomAdapter(mList, (onItemClickListener) this);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        }
    }

   /* private boolean check_directory_status() {
        file=new File(Environment.getExternalStorageDirectory()+"/.gallery dir");
        if(file.exists())
        {
            return true;
        }
        else
        {
            file.mkdir();
            return true;
        }
    }/*/
   @Override
   public void onClick(int position) {
       //Toast.makeText(this,"Position"+position,Toast.LENGTH_SHORT).show();
       /*Intent intent=new Intent(Hidden_home_page.this,full_view.class);
       intent.putExtra("image_link",imageFile.get(position).toString());
       intent.putExtra("image_name",imageFile.get(position).getName());
       intent.putExtra("pos",position);
       intent.putExtra("image_list",imageFile);
       startActivity(intent);*/
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
           //vvv = recyclerView.getLayoutManager().findViewByPosition(position);
           selectedImage.add(temp);
           //vvv.getVisibility();
           //text_on_selected.setText("Selected");
           //imageView.setAlpha(0.5F);
           //imageView.setColorFilter(Color.argb(125, 233, 230, 230));
       }
       if(selectedImage.size()>=0)
       {
           unhide_btn.setBackgroundColor(0xff0ceda2);
           unhide_btn.setTextColor(0xff000000);
       }
       else
       {
           unhide_btn.setBackgroundColor(0xffb3bdba);
           unhide_btn.setTextColor(0xff000000);
       }


   }


    @Override
    public void onBackPressed() {
       super.onBackPressed();
        finish();
    }
}
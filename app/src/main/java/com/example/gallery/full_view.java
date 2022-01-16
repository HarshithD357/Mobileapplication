package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class full_view extends AppCompatActivity {

    int position;
    String imageLink;
    TextView textView_img_name;
    ImageView imageView;
    String img_name;
    ImageButton back;
    ArrayList myImage;
    ImageButton delete;
    ImageButton details;
    ImageButton share;
    RelativeLayout relativeLayout;
    SwipeListener swipeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);


        //varialbles

        imageView=findViewById(R.id.imgfull);
        textView_img_name=findViewById(R.id.img_name);
        relativeLayout=findViewById(R.id.img_layout);
        //initializing swipe listner
        swipeListener=new SwipeListener(relativeLayout);




        //------------ back
        //back button code start
        back =findViewById(R.id.back_btn_f);
        delete=findViewById(R.id.delete_btn);
        share=findViewById(R.id.share_btn);

        details=(ImageButton) findViewById(R.id.details_btn);

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


        //------------ bundle/image from previous activity
        Bundle bundele=getIntent().getExtras();
        if(bundele!=null)
        {
            position=bundele.getInt("pos");
            imageLink =bundele.getString("image_link");
            myImage=(ArrayList) bundele.getParcelableArrayList("image_list");
            File tempf=new File(myImage.get(position).toString());
            img_name=tempf.getName();
            if(img_name.length()>14)
            {
                img_name=img_name.substring(0,14).concat("...");
            }

        }

        textView_img_name.setText(img_name);
        imageView.setImageURI(Uri.parse(imageLink));

        //------------ bundle/image from previous activity


        //------------- deleting an image

         //delete button code start
        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(getApplicationContext(),"Image deleted:"+uri,Toast.LENGTH_LONG).show();
                File fdelete = new File(myImage.get(position).toString());
                fdelete= new File(fdelete.getAbsolutePath());
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        Toast.makeText(getApplicationContext(),"Image deleted:"+fdelete,Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Image Not deleted:"+fdelete,Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"!st loop:"+fdelete,Toast.LENGTH_LONG).show();
                }

                /*File file_link;
                file_link= new File(myImage.get(position).toString());
                file_link.delete();
                Toast.makeText(getApplicationContext(),"Image deleted",Toast.LENGTH_LONG).show();
                position=((position+1)%myImage.size());
                imageView.setImageURI(Uri.parse(myImage.get(position).toString()));
            */
            }
        });

        //delete button ends

        //------------- details of image

        //details button code start
        details.setOnClickListener(new View.OnClickListener()
        {
            File file_link;
            @Override
            public void onClick(View v)
            {
                //file_link= new File(myImage.get(position).toString());
                Intent intent=new Intent(full_view.this,image_details.class);
                intent.putExtra("img_link",String.valueOf(myImage.get(position)));
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"Last Image",Toast.LENGTH_SHORT).show();
            }
        });

        //details button ends

        //share button code start
        share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent_share=new Intent(Intent.ACTION_SEND);
                intent_share.setType("image/jpg");
                intent_share.putExtra(Intent.EXTRA_STREAM,Uri.parse("File:///"+myImage.get(position).toString()));
                startActivity(Intent.createChooser(intent_share,"share via..."));

            }
        });

        //share button ends





    }


    //--------- guesture listener and detector
    private class SwipeListener  implements View.OnTouchListener {

        //variables
        GestureDetector gestureDetector;

        //constructor

        SwipeListener(View view)
        {
            //initialaizing threshold value
            int threshold=100;
            int velocity_threshold=100;

            //initializing simple guesture listner
            GestureDetector.SimpleOnGestureListener listener=
                    new GestureDetector.SimpleOnGestureListener(){

                        @Override
                        public boolean onDown(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            //x and y difference

                            float xDiff=e2.getX()-e1.getX();
                            float yDiff=e2.getY()-e1.getY();

                            try {
                                //check condition

                                if(Math.abs((xDiff))>Math.abs(yDiff))
                                {
                                    //X>Y
                                    //condition

                                    if(Math.abs(xDiff) > threshold
                                            && Math.abs(velocityX) >velocity_threshold
                                     )
                                    {
                                        //xdiff > threshold
                                        //x velocity > than velocity threshold
                                        if(xDiff>0)
                                        {
                                            //swiped left
                                            position=((position-1) % img_name.length());
                                            if(position>=0)
                                            {
                                                imageView.setImageURI(Uri.parse(myImage.get(position).toString()));
                                                File tempL=new File(myImage.get(position).toString());
                                                img_name=tempL.getName();
                                                if(img_name.length()>14)
                                                {
                                                    img_name=img_name.substring(0,14).concat("...");
                                                }
                                                textView_img_name.setText(img_name);
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(),"First Image",Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                        else {
                                            //swiped right
                                            position=((position+1)% img_name.length());
                                            if(position<myImage.size())
                                            {
                                                imageView.setImageURI(Uri.parse(myImage.get(position).toString()));
                                                File tempR=new File(myImage.get(position).toString());
                                                img_name=tempR.getName();
                                                if(img_name.length()>14)
                                                {
                                                    img_name=img_name.substring(0,14).concat("...");
                                                }
                                                textView_img_name.setText(img_name);
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(),"Last image",Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                        return true;
                                    }
                                }

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            return false;
                        }
                    };

            //initialize gesture detector

            gestureDetector=new GestureDetector(listener);
            //set listener on view
            view.setOnTouchListener(this);

        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //return gesture event
            return gestureDetector.onTouchEvent(event);
        }
    }

    //--------- guesture listener and detector


}
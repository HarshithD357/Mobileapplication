package com.example.gallery;


import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

public class hidden_home extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    //name and key name
    private  static final String SHARED_PREF_NAME="mypin";
    private  static final String KEY_PIN="pin";
    private  static final String KEY_EMAIL="email";

    Button okButton;
    EditText editText_pin;
    TextView msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_home);

        okButton=findViewById(R.id.ok_btn);
        editText_pin=findViewById(R.id.pin);
        msg=findViewById(R.id.msg);
        //SmsManager smsManager= SmsManager.getDefault();

        //code to redirect to pin_recovery page
        TextView textView_forgotPIN=(TextView) findViewById(R.id.forgot_password);
        textView_forgotPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                String pinInSp=sharedPreferences.getString(KEY_PIN,null);
                if(pinInSp==null)
                {
                    msg.setTextColor(0xffff0000);
                    msg.setText("Set up a PIN first");
                }
                else
                {
                    /*//permission
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                    {
                        if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                        {
                            //s
                        }
                        else
                        {
                            requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                        }
                    }
                    String number="8310054168".trim();
                    String msg="Hi this is my first message";
                    try {
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(number,null,msg,null,null);
                        Toast.makeText(getApplicationContext(),"Message Sent",Toast.LENGTH_LONG).show();
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Some fiedls is Empty",Toast.LENGTH_LONG).show();
                    }*/

                    Intent intent=new Intent(hidden_home.this,pin_recovery.class);
                    startActivity(intent);
                }

            }
        });


        //redirect  code ends


        // ok button
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read PIN
                String entered_pin=editText_pin.getText().toString();
                //shared preference
                sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

                // when open activity first check shared preference data available or not
                String pinInSp=sharedPreferences.getString(KEY_PIN,null);
                if(pinInSp!=null)
                {
                    if(pinInSp.equals(entered_pin))
                    {
                        Intent intent=new Intent(hidden_home.this,Hidden_home_page.class);
                        startActivity(intent);
                    }
                    else
                    {
                        msg.setTextColor(0xffff0000);
                        msg.setText("Wrong PIN");

                    }
                }
                else
                {
                    msg.setTextColor(0xffff0000);
                    msg.setText("Setup a PIN first");

                }
                //Intent intent=new Intent(hidden_home.this,Hidden_home_page.class);
               // startActivity(intent);
            }
        });
        //ok button

        //code to redirect to pin_setup page
        TextView  textView_setup_PIN=(TextView) findViewById(R.id.setup_pin);
        textView_setup_PIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                String pinInSp=sharedPreferences.getString(KEY_PIN,null);
                if(pinInSp==null)
                {
                    Intent intent=new Intent(hidden_home.this,setup_pin.class);
                    startActivity(intent);

                }
                else
                {
                    msg.setTextColor(0xffff0000);
                    msg.setText("You cannot set PIN again,try 'forgot pin' to recover PIN ");
                }

            }
        });

        //redirect  code ends
  }


    //back button handlling
    int count=0;
    @Override
    public void onBackPressed() {
        count++;
        if(count==1)
        {
            Toast.makeText(hidden_home.this,"Press again to go back",Toast.LENGTH_LONG).show();
        }

        if(count==2){
            super.onBackPressed();
        }
    }

    //backbutton code ends


}
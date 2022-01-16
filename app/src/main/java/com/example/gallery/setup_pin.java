package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class setup_pin extends AppCompatActivity {

    Button confirm_btn;
    TextView text_msg;
    TextView error_msg;
    String pin;
    String cpin;
    //String email;
    String sec_ques;
    SharedPreferences sharedPreferences;
    //name and key name
    private  static final String SHARED_PREF_NAME="mypin";
    private  static final String KEY_PIN="pin";
    private  static final String KEY_EMAIL="email";
    private  static  final String KEY_SEC_QUES="security_question";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_pin);

        //collecting information
        //PIN
        EditText text_pin = (EditText)findViewById(R.id.pin);

        //confirm PIN
        EditText text_cpin = (EditText) findViewById(R.id.cpin);

        //Email
        //EditText text_email = (EditText) findViewById(R.id.email);

        //Security question
        EditText text_sec_ques=(EditText) findViewById(R.id.sq1);
        //error message
        error_msg=findViewById(R.id.msg);

        confirm_btn=findViewById(R.id.confirm_btn);
        //text_msg=findViewById(R.id.msg);


        //shared preference
        //shared preference
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = text_pin.getText().toString();
                cpin = text_cpin.getText().toString();
               // email = text_email.getText().toString();
                sec_ques=text_sec_ques.getText().toString();

                //if all fields filled
                if(!sec_ques.isEmpty() && !pin.isEmpty() && !cpin.isEmpty())
                {
                    //if pin and cpin of length less than 4
                    if(pin.length()<4 || cpin.length()<4)
                    {
                        error_msg.setTextColor(0xffff0000);
                        error_msg.setText("PIN should be of 4 digit length");
                        //Toast.makeText(getApplicationContext(),"Less than 4",Toast.LENGTH_SHORT).show();
                        // String message="PIN and Re-enter PIN are same";
                        //text_msg.setText(message);
                    }
                    //if all ok
                    else
                    {
                        //if both pin and cpin are same
                        if(pin.equals(cpin))
                        {
                            char C0=pin.charAt(0);
                            char C1=pin.charAt(1);
                            char C2=pin.charAt(2);
                            char C3=pin.charAt(3);

                            if(C0==C1 && C0==C2 && C0==C3)
                            {
                                error_msg.setTextColor(0xffff0000);
                                error_msg.setText("Your PIN is not secure,use different combination of digits.");
                            }
                            else
                            {
                                    //saving pin and email
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString(KEY_PIN,pin.toString());
                                    //editor.putString(KEY_EMAIL,email);
                                    editor.putString(KEY_SEC_QUES,sec_ques);
                                    editor.apply();
                                    //String s="PIN and Email saved,please remember your PIN for further use";
                                    //error_msg.setTextColor(0xff00ff00);
                                    Toast.makeText(setup_pin.this,"PIN and Email ID saved",Toast.LENGTH_LONG).show();
                                    //error_msg.setText("PIN and Email saved");
                                    Intent intent=new Intent(setup_pin.this,hidden_home.class);
                                    startActivity(intent);


                            }

                        }
                        //if pin and cpin not same
                        else
                        {
                            error_msg.setTextColor(0xffff0000);
                            error_msg.setText("PIN  and Confirm PIN should be same");
                            // error_msg.setText(pin);
                        }
                        //b.setText(pin);
                        //Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
                        //String message="PIN and Re-enter PIN should be same";
                        //text_msg.setText(message);

                    }

                }
                //if any field is empty
                else
                {
                    error_msg.setTextColor(0xffff0000);
                    error_msg.setText("Please fill all the details");
                }


            }



        });



        /*for first time PIN setup
        SharedPreferences preferences=getSharedPreferences("PREFERENCESS",MODE_PRIVATE);
        String FirstTime=preferences.getString("k","");

        if(FirstTime.equals("yes"))
        {
            Intent intent=new Intent(setup_pin.this,hidden_home.class);
            startActivity(intent);
        }
        else
        {
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("k","yes");
            editor.apply();
        }*/

    }
}
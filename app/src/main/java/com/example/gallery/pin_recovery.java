package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class pin_recovery extends AppCompatActivity {

    TextView tv;
    Button validate;
    SharedPreferences sharedPreferences;
    private  static final String SHARED_PREF_NAME="mypin";
    private  static  final String KEY_SEC_QUES="security_question";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_recovery);

        validate=findViewById(R.id.validate_btn);
        tv=findViewById(R.id.info_text_2);

        //answer for the security question
        EditText text_sec_q=(EditText) findViewById(R.id.sec_q);
        //OTP generation
        int randomPin   =(int) (Math.random()*9000)+1000;
        String otp  = String.valueOf(randomPin);

        //tv.setText(otp);

        //shared preference
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp2=text_sec_q.getText().toString();
                String temp=sharedPreferences.getString(KEY_SEC_QUES,null);
                if(temp.equals(temp2))
                {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent=new Intent(pin_recovery.this,setup_pin.class);
                    startActivity(intent);
                    //finish();
                }
                else
                {
                 tv.setText("No!, It's not the right answer, try again..!");
                }
            }
        });
    }
}

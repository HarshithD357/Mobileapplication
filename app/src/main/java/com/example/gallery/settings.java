package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class settings extends AppCompatActivity {

    TextView text;
    SeekBar skbr;
    int temp;
    SharedPreferences sharedPreferences;
    private  static final String SHARED_PREF_NAME="mypin";
    private  static  final String NUM_OF_ROWS="rows";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        text=findViewById(R.id.ttx_info);
        skbr=findViewById(R.id.seekBar);
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        temp=sharedPreferences.getInt(NUM_OF_ROWS,3);
        skbr.setProgress(temp);
        if(temp==0)
        {
            temp=1;
        }
        text.setText(""+temp);

        skbr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0)
                {
                    progress=1;
                }
                //saving pin and email
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt(NUM_OF_ROWS,progress);
                editor.apply();
                text.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
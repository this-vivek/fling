package com.example.fling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread=new Thread()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                    Intent i = new Intent(getApplicationContext(),aftersplash.class);
                    startActivity(i);
                    finish();


                }
                catch (Exception e)
                {

                }
            }
        };thread.start();
    }
}

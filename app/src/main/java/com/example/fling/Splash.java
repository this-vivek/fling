package com.example.fling;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Splash extends AppCompatActivity {
String flag,flag2;
TextView quotes;
ImageView heart;
Random rand;
String quoting[]={"Act as if what you do makes a diffrence. IT DOES",
"Success is not final , failure is not fatal: it is the COURAGE TO CONTINUE that counts",
"Never bend your head. Always hold it high. Look the world Straight in the eye",
"What you get by achieving your goals is not as important as what you become by achieving your goals"};
LinearLayout boxContainer;
    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            boxContainer.setVisibility(View.VISIBLE);
            heart.setVisibility(View.VISIBLE);
           // heart.setVisibility(View.VISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // quotes=(TextView)findViewById(R.id.quotes);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN)
        {
            ((ViewGroup)findViewById(R.id.google_pixe)).getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        }
        rand=new Random();

        heart=(ImageView)findViewById(R.id.heart);
        boxContainer=(LinearLayout)findViewById(R.id.containBottom);
        quotes=(TextView)findViewById(R.id.quotes);
        quotes.setText(getRandArrayElement());
        sharedpref();

        Thread thread=new Thread()
        {
            public void run()
            {
                try
                {  handler.postDelayed(runnable,1000);
                    Thread.sleep(6000);

                        if(!flag.equals("0")) {
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            startActivity(i);
                            //finish();
                        }
                        else
                        {
                            Intent i = new Intent(getApplicationContext(), Login.class);
                            startActivity(i);
                            finish();
                        }

                }
                catch (Exception e)
                {

                }
            }
        };thread.start();
    }
    String getRandArrayElement()
    {
        return quoting[rand.nextInt(quoting.length)];
    }
    public void sharedpref() {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);

        flag=sharedPreferences.getString("username","0");


    }
}

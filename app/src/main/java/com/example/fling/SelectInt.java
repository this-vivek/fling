package com.example.fling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SelectInt extends AppCompatActivity {
    String data;
    SharedPreferences sharedPreferences;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_int);
        Helper.createStrictMode();
        Button mens = (Button) findViewById(R.id.mens);
        mens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SelectCol.class);
                try {
                    sharedpref();
                    data= URLEncoder.encode("interest","UTF-8")+"="+URLEncoder.encode("mens","UTF-8")+
                            "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+
                            "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("ADDINT","UTF-8");

                          Data obj=new Data();
                            obj.insertData(getApplicationContext(),"add_user.php",data);


                } catch (UnsupportedEncodingException e) {
                    Helper.createLog("ErSignUp",e.toString());
                }
                startActivity(i);
            }
        });
        Button women = (Button) findViewById(R.id.women);
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SelectCol.class);
                try {
                    sharedpref();
                    Log.d("username",username);
                    data= URLEncoder.encode("interest","UTF-8")+"="+URLEncoder.encode("womens","UTF-8")+
                            "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+
                            "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("ADDINT","UTF-8");

                    Data obj=new Data();
                    obj.insertData(getApplicationContext(),"add_user.php",data);


                } catch (UnsupportedEncodingException e) {
                    Helper.createLog("ErSignUp",e.toString());
                }
                startActivity(i);
            }
        });
    }
    public void sharedpref() {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);



        username=sharedPreferences.getString("username","NA");


    }

}

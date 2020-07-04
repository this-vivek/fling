package com.example.fling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SelectCol extends AppCompatActivity {
    String collage,block;
    String data,username="";
    Spinner spblock,spcollage;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_col);
        spblock=(Spinner)findViewById(R.id.spBlock);
        spcollage=(Spinner)findViewById(R.id.spCollage);
        intent=getIntent();
        Helper.createStrictMode();
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Personal_detail.class);
                try {
                 //   sharedpref();
                    collage=spcollage.getSelectedItem().toString();
                    block=spblock.getSelectedItem().toString();
                    data= URLEncoder.encode("collage","UTF-8")+"="+URLEncoder.encode(collage,"UTF-8")+
                            "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+
                            "&"+URLEncoder.encode("block","UTF-8")+"="+URLEncoder.encode(block,"UTF-8")+
                            "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("ADDCOL","UTF-8");

                    Data obj=new Data();
                  //  obj.insertData(getApplicationContext(),"add_user.php",data);



                } catch (UnsupportedEncodingException e) {
                    Helper.createLog("ErSignUp",e.toString());
                }

                sendData(i,block,collage);
                startActivity(i);
            }
        });

    }
    void sendData(Intent i,String block,String collage)
    {
        i.putExtra("username", intent.getStringExtra("username"));
        i.putExtra("name", intent.getStringExtra("name"));
        i.putExtra("email",  intent.getStringExtra("email"));
        i.putExtra("phone",  intent.getStringExtra("phone"));
        i.putExtra("password",  intent.getStringExtra("password"));
        i.putExtra("interest",intent.getStringExtra("interest"));
        i.putExtra("block",block);
        i.putExtra("collage",collage);
    }
    public void sharedpref() {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);



        username=sharedPreferences.getString("username","NA");


    }

}

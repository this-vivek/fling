package com.example.fling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

public class SelectCol extends AppCompatActivity {
    String collage,block;
    String data,username;
    Spinner spblock,spcollage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_col);
        spblock=(Spinner)findViewById(R.id.spBlock);
        spcollage=(Spinner)findViewById(R.id.spCollage);
        collage=spcollage.getSelectedItem().toString();
        block=spblock.getSelectedItem().toString();
        Helper.createStrictMode();
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Personal_detail.class);
                try {
                    sharedpref();

                    data= URLEncoder.encode("collage","UTF-8")+"="+URLEncoder.encode(collage,"UTF-8")+
                            "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+
                            "&"+URLEncoder.encode("block","UTF-8")+"="+URLEncoder.encode(block,"UTF-8")+
                            "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("ADDCOL","UTF-8");

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

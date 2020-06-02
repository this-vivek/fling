package com.example.fling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Personal_detail extends AppCompatActivity {
    Spinner spstate,spgender;
    String state,gender,cityname,prAge;
    EditText city,age;
    Button next;
    String data;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);
        bindWidget();
        Helper.createStrictMode();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityname=city.getText().toString();
                prAge=age.getText().toString();
                state=spstate.getSelectedItem().toString();
                gender=spgender.getSelectedItem().toString();
                insert();
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
            }
        });

    }
    void bindWidget()
    {
        spstate=(Spinner)findViewById(R.id.spstate);
        spgender=(Spinner)findViewById(R.id.spgender);
        city= (EditText) findViewById(R.id.city);
        age=(EditText)findViewById(R.id.age);
        next=(Button)findViewById(R.id.next);

    }
    void insert()
    {
        try {
            sharedpref();
            Helper.makeToast(getApplicationContext(),prAge);
            data= URLEncoder.encode("state","UTF-8")+"="+URLEncoder.encode(state,"UTF-8")+
                    "&"+URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(cityname,"UTF-8")+
                    "&"+URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender,"UTF-8")+
                    "&"+URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(prAge,"UTF-8")+
                    "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+
                    "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("ADDDETAIL","UTF-8");
            Data obj=new Data();

            obj.insertData(getApplicationContext(),"add_user.php",data);
        } catch (UnsupportedEncodingException e) {
            Helper.createLog("ErSignUp",e.toString());
        }

    }
    public void sharedpref() {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);



        username=sharedPreferences.getString("username","NA");


    }
}

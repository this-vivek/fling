package com.example.fling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Personal_detail extends AppCompatActivity {
    Spinner spstate,spgender;
    String state,gender,cityname,prAge,pabout;
    EditText city,age,about;
    Button next;
    String data;
    SharedPreferences sharedPreferences;
    String username,name,phone,password,email,interest,collage,block;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);
        bindWidget();
        sharedPreferences =getSharedPreferences("username", Context.MODE_PRIVATE);
        Helper.createStrictMode();
        Intent i=getIntent();
        username=i.getStringExtra("username");
        name=i.getStringExtra("name");
        phone=i.getStringExtra("phone");
        password=i.getStringExtra("username");
        email=i.getStringExtra("email");
        interest=i.getStringExtra("interest");
        collage=i.getStringExtra("collage");
        block=i.getStringExtra("block");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityname=city.getText().toString();
                prAge=age.getText().toString();
                pabout=about.getText().toString();
                state=spstate.getSelectedItem().toString();
                gender=spgender.getSelectedItem().toString();
                insert();

            }
        });

    }
    void bindWidget()
    {
        spstate=(Spinner)findViewById(R.id.spstate);
        spgender=(Spinner)findViewById(R.id.spgender);
        city= (EditText) findViewById(R.id.city);
        age=(EditText)findViewById(R.id.age);
        about=(EditText)findViewById(R.id.about);
        next=(Button)findViewById(R.id.next);

    }
    void insert()
    {
        try {
            //sharedpref();
            Helper.makeToast(getApplicationContext(),prAge);
            data= URLEncoder.encode("state","UTF-8")+"="+URLEncoder.encode(state,"UTF-8")+
                    "&"+URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+
                    "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+
                    "&"+URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+
                    "&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+
                    "&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+
                    "&"+URLEncoder.encode("interest","UTF-8")+"="+URLEncoder.encode(interest,"UTF-8")+
                    "&"+URLEncoder.encode("block","UTF-8")+"="+URLEncoder.encode(block,"UTF-8")+
                    "&"+URLEncoder.encode("collage","UTF-8")+"="+URLEncoder.encode(collage,"UTF-8")+
                    "&"+URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(cityname,"UTF-8")+
                    "&"+URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender,"UTF-8")+
                    "&"+URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(prAge,"UTF-8")+
                    "&"+URLEncoder.encode("about","UTF-8")+"="+URLEncoder.encode(pabout,"UTF-8")+
                    "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("ADDALL","UTF-8");
            Data obj=new Data();
        Log.d("bio",pabout);
            obj.insertData(getApplicationContext(),"add_user.php",data);
            shared(username);
            Intent i=new Intent(getApplicationContext(),Home.class);
            startActivity(i);
        } catch (UnsupportedEncodingException e) {
            Helper.createLog("ErSignUp",e.toString());
        }

    }
    void shared(String pusername)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",pusername);

        editor.commit();
    }

}

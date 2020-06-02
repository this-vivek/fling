package com.example.fling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SignUp extends AppCompatActivity {

    EditText name,username,password,email,phone;
    String data,pname,pusername,pass,pemail,pn;
    SharedPreferences sharedPreferences;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        callIt();
        sharedPreferences =getSharedPreferences("username", Context.MODE_PRIVATE);
        Helper.createStrictMode();


            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pname=name.getText().toString();
                    pusername=username.getText().toString();
                    pass=password.getText().toString();
                    pemail=email.getText().toString();
                    pn=phone.getText().toString();
                    shared(pusername);
                    insert();
                    Intent i=new Intent(getApplicationContext(),SelectInt.class);
                    startActivity(i);
                }
            });

    }
    void shared(String pusername)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",pusername);

        editor.commit();
    }
    void insert()
    {
        try {

            data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(pname,"UTF-8")+
                    "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(pusername,"UTF-8")+
                    "&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+
                    "&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(pemail,"UTF-8")+
                    "&"+URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(pn,"UTF-8")+
                    "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("ADD","UTF-8");
            Data obj=new Data();

            obj.insertData(getApplicationContext(),"add_user.php",data);
        } catch (UnsupportedEncodingException e) {
            Helper.createLog("ErSignUp",e.toString());
        }

    }
    void callIt() {
        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        signup=(Button)findViewById(R.id.signup);
    }
}

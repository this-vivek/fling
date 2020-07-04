package com.example.fling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SelectInt extends AppCompatActivity {
    String data;
    String usernamePre,name,phone,email,password;
    SharedPreferences sharedPreferences;
    String username;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_int);
        Helper.createStrictMode();
        i=getIntent();
        usernamePre=i.getStringExtra("username");

        Button mens = (Button) findViewById(R.id.mens);
        mens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SelectCol.class);
                sendData(intent,"mens");
                startActivity(intent);
            }
        });
        Button women = (Button) findViewById(R.id.women);
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SelectCol.class);
                sendData(i,"womens");
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(getApplicationContext(),SignUp.class);
        startActivity(i);
    }
    void sendData(Intent intent,String interest)
    {
        intent.putExtra("username", usernamePre);
        intent.putExtra("name", i.getStringExtra("name"));
              intent.putExtra("email",  i.getStringExtra("email"));
        intent.putExtra("phone",  i.getStringExtra("phone"));
        intent.putExtra("password",  i.getStringExtra("password"));
        intent.putExtra("interest",interest);
    }
    private void removeData() throws UnsupportedEncodingException {
        data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(usernamePre,"UTF-8")+
                "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("removeFromUsers","UTF-8");

        Data obj=new Data();
        obj.insertData(getApplicationContext(),"remove.php",data);


    }

    public void sharedpref() {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);



        username=sharedPreferences.getString("username","NA");


    }

}

package com.example.fling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SignUp extends AppCompatActivity {

    EditText name,username,password,email,phone;
    String data,pname,pusername,pass,pemail,pn;
    SharedPreferences sharedPreferences;
    ImageView back;
    Button signup;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        callIt();
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });
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
                   // shared(pusername);
                    insert();
                    if(message.equals("clear")) {
                        Intent i = new Intent(getApplicationContext(), Personal_detail.class);
                       /* i.putExtra("name", pname);
                        i.putExtra("username", pusername);
                        i.putExtra("email", pemail);
                        i.putExtra("phone", pn);
                        i.putExtra("password", pass);*/

                        Intent intent = new Intent(getApplicationContext(),SelectInt.class);
                        intent.putExtra("username", pusername);
                        intent.putExtra("name", pname);
                        intent.putExtra("username", pusername);
                        intent.putExtra("email", pemail);
                        intent.putExtra("phone", pn);
                        intent.putExtra("password", pass);
                        startActivity(intent);
                    }
                    else{
                        Helper.makeToast(getApplicationContext(),message);
                    }
                }
            });

    }
  /*  void shared(String pusername)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",pusername);

        editor.commit();
    }*/
    void insert()
    {
        try {

            data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(pname,"UTF-8")+
                    "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(pusername,"UTF-8")+
                    "&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+
                    "&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(pemail,"UTF-8")+
                    "&"+URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(pn,"UTF-8")+
                    "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("CHECKSIGNUP","UTF-8");
            Data obj=new Data();

           String data2= obj.selectData(getApplicationContext(),"add_user.php",data);
            JSONObject jsonObject = new JSONObject(data2);
            message= jsonObject.getString("message");



        } catch (UnsupportedEncodingException e) {
            Helper.createLog("ErSignUp",e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
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
  /*  void shared()
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("username",username);
        editor.putString("name",name);
        editor.putString("password",pn);
        editor.putString("email",pemail);
        editor.putString("username",id);

        editor.commit();
    }*/
}

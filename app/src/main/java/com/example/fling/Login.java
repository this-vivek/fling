package com.example.fling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Connection;
import com.example.fling.Helper.Helper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class Login extends AppCompatActivity {
    EditText Lemail,Lpass;
    String id,password;
    Button Blogin;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Lemail=(EditText)findViewById(R.id.Lemail);
        Lpass=(EditText)findViewById(R.id.Lpass);
        Blogin=(Button)findViewById(R.id.Blogin);
    Helper.createStrictMode();
        Blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

    }
    void check()
    {  id=Lemail.getText().toString();
        password=Lpass.getText().toString();
        StringBuilder stringBuilder=new StringBuilder();
        StringBuilder stringBuilder1=new StringBuilder();
        String line="",line2="";
        int x=0;
        try
        {
            HttpURLConnection connection= Connection.createConnection("add_user.php");
            OutputStream outputStream=connection.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            String data= URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+
                    "&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+
                    "&"+  URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("LOGIN","UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            String data1 = stringBuilder.toString();
            Log.d("Data", data1);

            JSONObject jsonObject = new JSONObject(data1);
            x = jsonObject.getInt("code");
            connection.disconnect();
        }
        catch(Exception e)
        {
            Log.d("data",e.toString());
        }
        if(x==1)
        {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);


        }
        else
        {
            Toast.makeText(this,"invalid username and password",Toast.LENGTH_LONG).show();
        }

    }
}

package com.example.fling;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fling.Data.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fling.R.drawable.ic_businessman;

public class InsideChats extends AppCompatActivity {
String usernameOLD;
String message[],username[],user_id[],status[];
TextView name,subname;
EditText sendBox;
String image;
CircleImageView chatPic;
String sendData;
String reciverId;
String data;
Bitmap bitmap;
String recivername;
Button send,back;
int length2;
int length;
ProgressDialog progressDialog;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_chats);
        sharedpref();
        progressDialog=new ProgressDialog(InsideChats.this);
        bindWidget();
        Intent intent=getIntent();
        recivername=intent.getStringExtra("name");
        name.setText(recivername);
        reciverId=intent.getStringExtra("user_id");
        Log.d("user_id",reciverId);
        subname.setText(reciverId);
        image=intent.getStringExtra("image");

        if(image.equals("blank")) {

        }

        else  if(image.equals("male"))
        {
            Drawable drawable=getResources().getDrawable(R.drawable.ic_businessman);
            chatPic.setImageDrawable(drawable);
        }
        else  if(image.equals("female"))
        {
            Drawable drawable=getResources().getDrawable(R.drawable.ic_woman);
            chatPic.setImageDrawable(drawable);

        }
        else {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(image).getContent());
            } catch (IOException e) {
                e.printStackTrace();

            }
            chatPic.setImageBitmap(bitmap);
        }

            try {
                fetchChats();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        if(length2!=0) {
            RecyclerView insideChats = (RecyclerView) findViewById(R.id.chatList);
            insideChats.setLayoutManager(new LinearLayoutManager(this));
            insideChats.setAdapter(new InsideChatsAdapter(username, user_id, message, status, this));
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(sendBox.getText().toString()=="")) {
                    try {
                        sendMessage();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent(getApplicationContext(), InsideChats.class);
                    i.putExtra("user_id", reciverId);
                    i.putExtra("image", image);
                    i.putExtra("name", recivername);
                    startActivity(i);
                    finish();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Chats.class);
                startActivity(i);
                finish();
            }
        });


    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),Chats.class);
        startActivity(i);
        finish();
    }
    void sendMessage() throws UnsupportedEncodingException {
        sendData=sendBox.getText().toString();
        Data obj=new Data();
        data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(usernameOLD,"UTF-8")+
                "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(reciverId,"UTF-8")+
                "&"+URLEncoder.encode("message","UTF-8")+"="+URLEncoder.encode(sendData,"UTF-8")+
                "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("send","UTF-8");

        obj.insertData(getApplicationContext(),"Chats.php",data);

    }
    void bindWidget()
    {
        name=(TextView)findViewById(R.id.name);
        subname=(TextView)findViewById(R.id.username);
        sendBox=(EditText)findViewById(R.id.sendBox);
        chatPic=(CircleImageView) findViewById(R.id.chatPic);
        send=(Button)findViewById(R.id.send);
        back=(Button)findViewById(R.id.back);

    }
    void fetchChats() throws UnsupportedEncodingException, JSONException {
        Data obj=new Data();
        data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8")+
                "&"+ URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("display", "UTF-8")+
                "&"+URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(reciverId, "UTF-8");
        String newData=obj.selectData(getApplicationContext(),"Chats.php",data);
        JSONArray jsonArray=new JSONArray(newData);

      /*  name = new String[jsonArray.length()];
        dp = new String[jsonArray.length()];*/
        username = new String[jsonArray.length()];
        user_id = new String[jsonArray.length()];
        message = new String[jsonArray.length()];
        status = new String[jsonArray.length()];


        JSONObject jsonObject=null;
        length2=jsonArray.length();
        for (int i=0;i<jsonArray.length();i++)
        {
            jsonObject=jsonArray.getJSONObject(i);

            //   uid1[i]=jsonObject.getString("uid");
            username[i]=jsonObject.getString("username");
            user_id[i]=jsonObject.getString("user_id");
            message[i]=jsonObject.getString("message");
            status[i]=jsonObject.getString("status");

        }
        Log.d("username", String.valueOf(message[0]));


    }
    public void sharedpref() {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);



        usernameOLD=sharedPreferences.getString("username","NA");


    }
}

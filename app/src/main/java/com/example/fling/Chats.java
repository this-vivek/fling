package com.example.fling;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Chats extends AppCompatActivity {
String name[],dp[];
String usernameOLD;
String username[],msg[];
String data;
ImageView back;
Context context;
int length2;
    RecyclerView chatList;
ProgressDialog progressDialog;
int length;
LinearLayout ifNot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Helper.createStrictMode();
        progressDialog=new ProgressDialog(Chats.this);
        ifNot=(LinearLayout)findViewById(R.id.ifNot);
        sharedpref();
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
            try {
                fetch();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        for(int j=0;j<length2;j++) {
            try {
                fetchOriginal(username[j],j);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
      /*  for(int j=0;j<length2;j++) {
            try {
                fetchMsg(username[j],j);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
       /* for(int j=0;j<length;j++) {
            try {
                fetchLast(username[j],j);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
        chatList=(RecyclerView)findViewById(R.id.recycleChats);
        if(length2!=0) {
            chatList.setLayoutManager(new LinearLayoutManager(this));

        chatList.setAdapter(new RecyclerChatsAdapter(name,dp,this,username));
        }
        else {
            ifNot.setVisibility(View.VISIBLE);
            chatList.setVisibility(View.GONE);
        }
        back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
                finish();
            }
        });

    }
    void fetch() throws UnsupportedEncodingException, JSONException {
        Data data2=new Data();

        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8")+
                "&"+URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("first", "UTF-8");
        String newData=data2.selectData(getApplicationContext(),"fetchChats.php",data);
        if(!newData.isEmpty())
            {
                JSONArray jsonArray = new JSONArray(newData);

      /*  name = new String[jsonArray.length()];
        dp = new String[jsonArray.length()];*/
                username = new String[jsonArray.length()];
                name = new String[jsonArray.length()];
                dp = new String[jsonArray.length()];
                msg = new String[jsonArray.length()];

                JSONObject jsonObject = null;
                length2 = jsonArray.length();
                Log.d("length", String.valueOf(length2));
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    //   uid1[i]=jsonObject.getString("uid");
                    username[i] = jsonObject.getString("username");

                }
                Log.d("username", String.valueOf(username[0]));
            }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),Home.class);
        startActivity(i);
        finish();
    }

    void fetchOriginal(String user_id, int j) throws UnsupportedEncodingException, JSONException {

        Data data2=new Data();
        Log.d("userOLD",usernameOLD);
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8")+
                "&"+URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("second", "UTF-8");
        String newData=data2.selectData(getApplicationContext(),"fetchChats.php",data);
        JSONArray jsonArray=new JSONArray(newData);





        JSONObject jsonObject=null;
        length=jsonArray.length();
        for (int i=0;i<jsonArray.length();i++)
        {
            jsonObject=jsonArray.getJSONObject(i);

            //   uid1[i]=jsonObject.getString("uid");

            name[j]=jsonObject.getString("name");
            dp[j]=jsonObject.getString("image");
        }
        Log.d("dp", dp[0]);
    }
    void fetchMsg(String user_id,int j) throws UnsupportedEncodingException, JSONException {

        Data data2=new Data();
        Log.d("userOLD",usernameOLD);
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8")+
              "&"+  URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8")+
                "&"+URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("third", "UTF-8");
        String newData=data2.selectData(getApplicationContext(),"fetchChats.php",data);
        JSONArray jsonArray=new JSONArray(newData);





        JSONObject jsonObject=null;
        length=jsonArray.length();
        for (int i=0;i<jsonArray.length();i++)
        {
            jsonObject=jsonArray.getJSONObject(i);

            //   uid1[i]=jsonObject.getString("uid");


            msg[j]=jsonObject.getString("message");
        }
        try {
            Log.d("msg", msg[0]);
        }
        catch (Exception e)
        {
            Log.d("exception",e.toString());
        }
    }
    public void sharedpref() {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);



        usernameOLD=sharedPreferences.getString("username","NA");


    }
}

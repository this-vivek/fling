package com.example.fling;

import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;


public class drawer extends AppCompatActivity {

    TextView name,address,errorInOld,errorInconfirm;
    CircleImageView dp;
    String usernameOLD;
    String rec[]=new String[3];
    String data;
    EditText old,newPass,confirmPass;
    Button back,cancel,change,close;
    Bitmap bitmap;
    Dialog mydialog;
    ImageView imageButton;
    CardView home,chat,profile,passwordChange,rate,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Helper.createStrictMode();
        bindWidget();
        imageButton=(ImageView)findViewById(R.id.profileButton);
        mydialog=new Dialog(this);
        mydialog.setContentView(R.layout.change_password);
         change=(Button)mydialog.findViewById(R.id.change);
         cancel=(Button)mydialog.findViewById(R.id.close);
        errorInOld=(TextView) mydialog.findViewById(R.id.errorInOld);
        errorInconfirm=(TextView) mydialog.findViewById(R.id.errorInConfirm);
        close=(Button)mydialog.findViewById(R.id.cancel);
        old=(EditText)mydialog.findViewById(R.id.old);
        newPass=(EditText)mydialog.findViewById(R.id.newPass);
        confirmPass=(EditText)mydialog.findViewById(R.id.confirmPass);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    errorInOld.setVisibility(View.GONE);
                    errorInconfirm.setVisibility(View.GONE);
                    checkOld(old.getText().toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        sharedpref();
        try {
            fetchData(usernameOLD);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
                finish();
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Chats.class);
                startActivity(i);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),BigProfile.class);
                startActivity(i);
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),rateAndReviews.class);
                i.putExtra("name",rec[0]);
                i.putExtra("dp",rec[1]);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               disable();
            }
        });
        passwordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.show();


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
            }
        });
            }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),Home.class);
        startActivity(i);
    }

    private void fetchData(String usernameOLD) throws UnsupportedEncodingException, JSONException {

            Data obj = new Data();
            Log.d("username",usernameOLD);
            data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8");
            String data2 = obj.selectData(getApplicationContext(), "fetchInDrawer.php", data);
            JSONObject jsonObject = new JSONObject(data2);
            String count = jsonObject.getString("o");
            String temp= String.valueOf(count);
            Log.d("dataString",count);
            StringTokenizer stringTokenizer=new StringTokenizer(temp);
            int i=0;
            while(stringTokenizer.hasMoreTokens()) {
                rec[i]=stringTokenizer.nextToken("|");
                i++;
            }

            name.setText(rec[0]);
        if(rec[1].equals("blank")) {

        }

        else  if(rec[1].equals("male"))
        {
            Log.d("true","true");
            Drawable drawable2=getResources().getDrawable(R.drawable.ic_businessman);
            dp.setImageDrawable(drawable2);
        }
        else  if(rec[1].equals("female"))
        {
            Drawable drawable2=getResources().getDrawable(R.drawable.ic_woman);
            dp.setImageDrawable(drawable2);
            imageButton.setImageDrawable(drawable2);
        }
        else {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(rec[1]).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dp.setImageBitmap(bitmap);
        }

            address.setText(rec[2]);



    }

    private void checkOld(String old) throws UnsupportedEncodingException, JSONException {
        Data obj = new Data();
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8")+
       "&"+ URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(old, "UTF-8");
        String data2 = obj.selectData(getApplicationContext(), "checkOLD.php", data);
        JSONObject jsonObject = new JSONObject(data2);
        String count = jsonObject.getString("code");
        String temp= String.valueOf(count);
        if(temp.equals("true"))
        {
           boolean check= checkSame();
           if(check)
           {
               changePass(confirmPass.getText().toString());

           }
           else
           {
               errorInconfirm.setVisibility(View.VISIBLE);
               confirmPass.setText("");
           }
        }
        else
        {
            errorInOld.setText("incorrect Old Password try again");
            errorInOld.setVisibility(View.VISIBLE);
        }



    }

    private void changePass(String password) throws UnsupportedEncodingException {

        Data obj=new Data();
        data= URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+
                "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(usernameOLD,"UTF-8");
        String data2=obj.selectData(getApplicationContext(),"changePass.php",data);
    }

    private boolean checkSame() {
        if(newPass.getText().toString().equals(confirmPass.getText().toString()))
        {
            return true;
        }
        else
            return false;
    }

    private void bindWidget() {
    name=(TextView)findViewById(R.id.name);
    address=(TextView)findViewById(R.id.address);
    dp=(CircleImageView) findViewById(R.id.dp);
    home=(CardView)findViewById(R.id.home);
    back=(Button)findViewById(R.id.back) ;
        chat=(CardView)findViewById(R.id.chat);
        profile=(CardView)findViewById(R.id.profile);
        passwordChange=(CardView)findViewById(R.id.passwordChange);
        rate=(CardView)findViewById(R.id.review);
        logout=(CardView)findViewById(R.id.logout);
    }
    public void sharedpref() {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);



        usernameOLD=sharedPreferences.getString("username","NA");


    }
    void disable()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);

        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("username","0");
        editor.commit();
        Intent i=new Intent(getApplicationContext(),Login.class);
        startActivity(i);
        finish();
    }
}
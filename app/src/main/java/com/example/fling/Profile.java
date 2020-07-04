package com.example.fling;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fling.R.drawable.ic_businessman;

public class Profile extends AppCompatActivity {

    EditText pusername,pemail,pname,pphone,pabout,phobbies;
    TextView followers,following,changedp;
    CircleImageView profilepic;
    Button cancel,ok;
    Uri filePath;
    String image2;
    int countFollowing;
    boolean flag=false;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    String data;
    String option;
    Bitmap bitmap;
    String username,email,name,phone,about,Dfollowers,Dfollowing,image,hobbies;
    String usernameOLD;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Helper.createStrictMode();
        bindWidget();
        sharedpref();
        Intent intent=getIntent();
        progressDialog=new ProgressDialog(Profile.this);
        username=intent.getStringExtra("username");
        email=intent.getStringExtra("email");
        name=intent.getStringExtra("name");
        phone=intent.getStringExtra("phone");
        about=intent.getStringExtra("bio");
        Dfollowers=intent.getStringExtra("followers");
        Dfollowing=intent.getStringExtra("followings");
        image=intent.getStringExtra("image");
        hobbies=intent.getStringExtra("hobbies");
        pusername.setText(username);
        pname.setText(name);
        pemail.setText(email);
        pphone.setText(phone);
        phobbies.setText(hobbies);
        pabout.setText(about);
        followers.setText(Dfollowers);
        following.setText(Dfollowing);

        if(image.equals("blank")) {

        }

        else  if(image.equals("male"))
        {
            Drawable drawable=getResources().getDrawable(ic_businessman);
            profilepic.setImageDrawable(drawable);
        }
        else  if(image.equals("female"))
        {
            Drawable drawable=getResources().getDrawable(R.drawable.ic_woman);
            profilepic.setImageDrawable(drawable);

        }
        else
        {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(image).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            profilepic.setImageBitmap(bitmap);
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    update();
                    if(flag) {
                        image2 = getStringImage(bitmap);
                        uploadDP();
                    }
                    Intent i=new Intent(getApplicationContext(),BigProfile.class);
                    startActivity(i);
                    finish();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),BigProfile.class);
                startActivity(i);
                finish();

            }
        });
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDP();


            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),BigProfile.class);
        startActivity(i);
        finish();
    }

        void bindWidget()
    {
        pusername=(EditText)findViewById(R.id.pUsername);
        pemail=(EditText)findViewById(R.id.pEmail);
        pname=(EditText)findViewById(R.id.pName);
        pphone=(EditText)findViewById(R.id.pPhone);
        pabout=(EditText)findViewById(R.id.pAbout);
        followers=(TextView)findViewById(R.id.nfollowers);
        following=(TextView)findViewById(R.id.nfollow);
        profilepic=(CircleImageView) findViewById(R.id.profilePic);
        cancel=(Button)findViewById(R.id.cancel);
        ok=(Button)findViewById(R.id.ok);
        changedp=(TextView)findViewById(R.id.changePic);
        phobbies=(EditText) findViewById(R.id.pHobbies);
    }
    void update() throws UnsupportedEncodingException, JSONException {
        String hob=phobbies.getText().toString();
        Log.d("hobbies",hob);
        Data obj=new Data();
        data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(pname.getText().toString(),"UTF-8")+
                "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(pusername.getText().toString(),"UTF-8")+
                "&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(pemail.getText().toString(),"UTF-8")+
                "&"+URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(pphone.getText().toString(),"UTF-8")+
                "&"+URLEncoder.encode("about","UTF-8")+"="+URLEncoder.encode(pabout.getText().toString(),"UTF-8")+
                "&"+URLEncoder.encode("hobbies","UTF-8")+"="+URLEncoder.encode(hob,"UTF-8")+
                "&"+URLEncoder.encode("usernameOLD","UTF-8")+"="+URLEncoder.encode(usernameOLD,"UTF-8")+
                "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("UPDATE","UTF-8");
        Log.d("pphone",pemail.getText().toString());
        String data2=obj.selectData(getApplicationContext(),"update.php",data);
      /*  JSONObject jsonObject = new JSONObject(data2);
       String message= jsonObject.getString("mesg");
        Helper.makeToast(getApplicationContext(),message);*/

    }

    public void sharedpref() {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);



        usernameOLD=sharedPreferences.getString("username","NA");


    }
    void updateDP()
    {
        if (isReadStorageAllowed())
        {
            option="gallery";
            showFileChooser();
            return;
        }

        requestStoragePermission();

    }
    public void takeImageFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
    private void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (option.equals("camera")) {
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                profilepic.setImageBitmap(bitmap);
            }
        }
        if (option.equals("gallery")) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                    profilepic.setImageBitmap(bitmap);
                    flag=true;

                } catch (IOException e) {
                    Log.d("BigError",e.toString());
                }
            }
        }

    } //converting image to string

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //camera permissions
    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Profile.this, android.Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(Profile.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
     void uploadDP() {
        try {

          Log.d("data",image2);
            data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image2, "UTF-8")+
                    "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("UPDATEDP","UTF-8")+
                    "&"+URLEncoder.encode("usernameOLD","UTF-8")+"="+URLEncoder.encode(usernameOLD,"UTF-8");
            Data obj = new Data();

            obj.insertData(getApplicationContext(), "update.php", data);
        } catch (UnsupportedEncodingException e) {
            Helper.createLog("imageUpload", e.toString());
        }

    }
}

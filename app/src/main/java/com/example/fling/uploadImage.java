package com.example.fling;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.StringTokenizer;

public class uploadImage extends AppCompatActivity {
Button imageUpload;
String usernameOLD;
String option;
Bitmap bitmap;
String count;
int length;
String image2;
String data;
Uri filePath;
private  String img1[];
    private String img2[];
boolean flag;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        sharedpref();
        imageUpload=(Button)findViewById(R.id.imageUpload);
        Button uploadtodb=(Button)findViewById(R.id.uploadNow);
        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDP();

            }
        });
        uploadtodb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image2 = getStringImage(bitmap);
                uploadDP();
                onRestart();
            }
        });
        try {
            fetch();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView imagegallery=(RecyclerView)findViewById(R.id.imageGallery);
        imagegallery.setLayoutManager(new LinearLayoutManager(this));
        if(length!=0)
        {
            imagegallery.setAdapter(new Gallery_Adapter(img1,img2,usernameOLD,this));
        }

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
               // profilepic.setImageBitmap(bitmap);
            }
        }
        if (option.equals("gallery")) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                  //  profilepic.setImageBitmap(bitmap);
                    flag=true;

                } catch (IOException e) {
                    Log.d("BigError",e.toString());
                }
            }
        }

    } //converting image to string

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(uploadImage.this, android.Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(uploadImage.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
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

         //   Log.d("data",image2);
            data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image2, "UTF-8")+
                    "&"+URLEncoder.encode("usernameOLD","UTF-8")+"="+URLEncoder.encode(usernameOLD,"UTF-8");
            Data obj = new Data();

            obj.insertData(getApplicationContext(), "updateGallery.php", data);
        } catch (UnsupportedEncodingException e) {
            Helper.createLog("imageUpload", e.toString());
        }

    }
    void fetch() throws IOException, JSONException {
        Data obj = new Data();
        String fetched[]=new String[2];
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8");
        String data2 = obj.selectData(getApplicationContext(), "fetchGallery.php", data);
        JSONObject jsonObject = new JSONObject(data2);
         count = jsonObject.getString("o");
        String temp= String.valueOf(count);
        if(temp.equals("|"))
            return;
        Log.d("dataString",temp);
        StringTokenizer stringTokenizer=new StringTokenizer(temp);
        int i=0;
        while(stringTokenizer.hasMoreTokens()) {
        fetched[i]=stringTokenizer.nextToken("|");
        i++;
        }

         i=0;
        int j=0;
        int flag=0;
         length=Integer.parseInt(fetched[1]);
            if(length==1)
        {
            img1 = new String[length];
            img2 = new String[length];
            img2[0]="empty";
        }
       else  if(length%2==0) {
             img1 = new String[length/2];
             img2 = new String[length/2];
         }
       else
            {
                img1 = new String[(length/2)+1];
                img2 = new String[(length/2)+1];
                img2[(length/2)]="empty";
            }

        try{
            Log.d("img1", String.valueOf(img1.length));
            Log.d("img2", String.valueOf(img2.length));

        }
        catch(Exception e)
        {
            Log.d("exception",e.toString());
        }

        StringTokenizer stringTokenizer2=new StringTokenizer(fetched[0]);
        while(stringTokenizer2.hasMoreTokens()) {
            if(flag==0) {
                img1[i] = stringTokenizer2.nextToken(",");
                flag=1;
                i++;
            }
           else
            {
                try {


                    img2[j] = stringTokenizer2.nextToken(",");
                    j++;
                }
                catch (Exception e)
                {
                    Log.d("exception",e.toString());
                }
                flag=0;
            }
           try{
               Log.d("img1",img1[0]);
               Log.d("img2", String.valueOf(img2.length));

           }
           catch(Exception e)
           {
               Log.d("exception",e.toString());
           }

        }


    }
}

package com.example.fling;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Connection;
import com.example.fling.Helper.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class tryImage extends AppCompatActivity {
    private String option;

    String data;
    String image;
    String Dimage[];

String image2;
    //Image request code
    private Bitmap bitmap = null;
    Uri filePath;
    ImageView user_img;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;

    Button uploadit,galleryit,server,fetch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_image);
        Helper.createStrictMode();
        user_img = (ImageView) findViewById(R.id.userimg);
    uploadit=(Button)findViewById(R.id.uploadit);
    galleryit=(Button)findViewById(R.id.gallery);
    server=(Button)findViewById(R.id.server);
    fetch=(Button)findViewById(R.id.fetch);
    uploadit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isReadStorageAllowed())
            {
                option="camera";
                takeImageFromCamera(v);
                return;
            }

            requestStoragePermission();
        }
    });
    galleryit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                option="gallery";
                showFileChooser();

        }
    });
server.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        image=getStringImage(bitmap);
        insert();
    }
});
fetch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
            display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
});
    }

    public void takeImageFromCamera(View view) {
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
                user_img.setImageBitmap(bitmap);
            }
        }
        if (option.equals("gallery")) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                    user_img.setImageBitmap(bitmap);

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
        if (ActivityCompat.shouldShowRequestPermissionRationale(tryImage.this, android.Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(tryImage.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
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

    void insert() {
        try {
        Log.d("ImageData",image);
            data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8");
            Data obj = new Data();

            obj.insertData(getApplicationContext(), "uploadImage.php", data);
        } catch (UnsupportedEncodingException e) {
            Helper.createLog("imageUpload", e.toString());
        }

    }
   void display() throws IOException {
       String line="";
       StringBuilder stringBuilder=new StringBuilder();

       try
       {

           HttpURLConnection conn= Connection.createConnection("fetch.php");
           OutputStream outputStream= conn.getOutputStream();
           OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
           BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
           String data2="";
           bufferedWriter.write(data2);
           bufferedWriter.close();
           outputStreamWriter.close();
           outputStream.close();

           InputStream inputStream=conn.getInputStream();
           InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
           BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
           while((line=bufferedReader.readLine())!=null)
           {
               stringBuilder.append(line+"\n");
           }
           String data=stringBuilder.toString();
           JSONArray jsonArray=new JSONArray(data);
           //init the array;
           //uid1=new String[jsonArray.length()];
           Dimage = new String[jsonArray.length()];

           JSONObject jsonObject=null;
           int length=jsonArray.length();
           for (int i=0;i<jsonArray.length();i++)
           {
               jsonObject=jsonArray.getJSONObject(i);

               //   uid1[i]=jsonObject.getString("uid");
               Dimage[i]=jsonObject.getString("image");
                Log.d("showData",Dimage[i]);
           }
       }
       catch(Exception e)
       {
           Log.d("error2",e.toString());
       }

       bitmap= BitmapFactory.decodeStream((InputStream)new URL(Dimage[0]).getContent());
       user_img.setImageBitmap(bitmap);

   }
}

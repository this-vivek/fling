package com.example.fling;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ImageUpload extends AppCompatActivity  {

    //Declaring views
     Button buttonChoose;
   Button buttonUpload;
    private String option;

    String data;
    String image;



    //Image request code
    private Bitmap bitmap = null;
    ImageView user_img;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Helper.createStrictMode();
  bindWidget();
       buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    private void bindWidget() {
       /* user_img = (ImageView) findViewById(R.id.imageUpload);*/
        buttonChoose =  findViewById(R.id.buttonChoose);
        buttonUpload = findViewById(R.id.buttonUpload);

    }

    public void takeImageFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getApplicationContext(), android.Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext().getApplicationContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext().getApplicationContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    void insert() {
        try {

            data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8");
            Data obj = new Data();

            obj.insertData(getApplicationContext(), "uploadImage.php", data);
        } catch (UnsupportedEncodingException e) {
            Helper.createLog("imageUpload", e.toString());
        }

    }


}

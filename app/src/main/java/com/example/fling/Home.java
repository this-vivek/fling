package com.example.fling;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fling.R.drawable.ic_businessman;

public class Home extends AppCompatActivity {
    LinearLayout constraintLayout;
    String usernameOLD;
    LinearLayout layout2,layout3;
    int length;
    String data,ownName[],ownEmail[];
    TextView TitleName,TitleEmail;
    CircleImageView profile;
    Bitmap bitmap;
    Uri filePath;
    ImageView galleryPhoto;
    String image;
    static boolean flag1=false;
    private int PICK_IMAGE_REQUEST = 1;
    String option;
    Button post,cancel;
    String ownDp[];
    private static final int CAMERA_REQUEST = 1888;
    Dialog mydialog,mydialog2;
    ViewPager viewPager;
    Adapter2 adapter2;
    Button Home;
    Integer[] color=null;
    ArgbEvaluator argbEvaluator=new ArgbEvaluator();
ProgressDialog progressDialog;
    /*String name[]={"vivek","praveen","shivani","shailesh"},course[]={"BCA","MCA","CSE","MBA"},desc[]={"Well educated person and well dressed","sweet and silent","no talk no trouble","funny and humuor"},age[]={"20","24","21","25"};
     */
    String name[],course[],desc[],age[],dp[],username[];
    Button home,chats,search;

    static boolean flag=true;



    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Helper.createStrictMode();
        constraintLayout=(LinearLayout) findViewById(R.id.layout1);
        layout2=(LinearLayout) findViewById(R.id.layout2);
        layout3=(LinearLayout) findViewById(R.id.layout3);
        home=(Button)findViewById(R.id.home);
        TitleName=(TextView)findViewById(R.id.TitleName);
        TitleEmail=(TextView)findViewById(R.id.TitleEmail);
        progressDialog=new ProgressDialog(Home.this);

        mydialog=new Dialog(this);
        mydialog2=new Dialog(this);

        viewPager=findViewById(R.id.viewPager);
        Home=(Button)findViewById(R.id.home);
        Home.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("black")));
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=getIntent();
                startActivity(i);

                finish();
            }
        });
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
        profile=(CircleImageView)findViewById(R.id.ownDp);
        sharedpref();
        Helper.createLog("user",usernameOLD);
        chats=(Button)findViewById(R.id.chats);
        search=(Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent i=new Intent(getApplicationContext(),uploadImage.class);
                startActivity(i);*/
                setImagepopUp();
                if(flag1) {
                    Log.d("flag", String.valueOf(flag1));
                    //
                    //    mydialog.dismiss();
                }
            }
        });
        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_bar);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Intent intent=new Intent(getApplicationContext(),Chats.class);
               startActivity(intent);


            }
        });


       /* RecyclerView dataList=(RecyclerView)findViewById(R.id.dataList);
        dataList.setLayoutManager(new LinearLayoutManager(this));*/
        try {
            fetch();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(length!=0) {
            try {
                fetchDesc();
            } catch (UnsupportedEncodingException e) {

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                fetchAge();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                fetchCourse();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("age",age[0]+age[1]);
            Log.d("desc",desc[0]+desc[1]);
            adapter2=new Adapter2(name, course, desc, age, dp,username,usernameOLD,this);

            Log.d("errorData",username[0]);
            viewPager.setAdapter(adapter2);
            viewPager.setPadding(30,180,30,300);

            viewPager.setPageMargin(26);

            Integer[] color_temp={getResources().getColor(R.color.color1),
                    getResources().getColor(R.color.color2),
                    getResources().getColor(R.color.color3),
                    getResources().getColor(R.color.color5),
                    getResources().getColor(R.color.color6),
                    getResources().getColor(R.color.color7),
                    getResources().getColor(R.color.color8),
                    getResources().getColor(R.color.color9),
                    getResources().getColor(R.color.color10),
                    getResources().getColor(R.color.color11),
                    getResources().getColor(R.color.color12),
                    getResources().getColor(R.color.color4)};
            color=color_temp;
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                   /* if(position<(adapter2.getCount()-1)&& position<(color.length-1))
                    {
                      *//*  layout2.setBackgroundColor(color[position]);
                        layout3.setBackgroundColor(color[position]);*//*
                        viewPager.setBackgroundColor(
                                (Integer)argbEvaluator.evaluate
                                        (positionOffset,
                                                color[position],
                                                color[position+1]
                                        )
                        );


                    }
                    else
                    {
                    *//*    layout2.setBackgroundColor(color[color.length-1]);
                        layout3.setBackgroundColor(color[color.length-1]);*//*
                        viewPager.setBackgroundColor(color[color.length-1]);

                    }*/
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            /*  dataList.setAdapter(new RecycleAdapter(name, course, desc, age, dp,username,usernameOLD,this));*/
        }
        else {
            Helper.makeToast(getApplicationContext(),usernameOLD);
        }
        try {
            fetchOwnDp();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_bar);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Intent intent=new Intent(getApplicationContext(),drawer.class);
                startActivity(intent);
            }
        });
    }
    void setImagepopUp()
    {
        mydialog.setContentView(R.layout.upload_image_popup);
        Button upload=(Button)mydialog.findViewById(R.id.upload);
        Button close=(Button)mydialog.findViewById(R.id.cancel);
        mydialog2.setContentView(R.layout.post_gallery);

        post=(Button)mydialog2.findViewById(R.id.post);
        cancel=(Button)mydialog2.findViewById(R.id.cancel);
        galleryPhoto=(ImageView)mydialog2.findViewById(R.id.galleryPhoto);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog2.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDP();




            }
        });


        mydialog.show();

    }

    void updateDP()
    {
        if (isReadStorageAllowed())
        {
            option="gallery";
            showFileChooser();
            Log.d("flag", String.valueOf(flag1));
            mydialog.dismiss();
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
                galleryPhoto.setImageBitmap(bitmap);
            }
        }
        if (option.equals("gallery")) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    galleryPhoto.setImageBitmap(bitmap);
                    mydialog2.show();
                    image = getStringImage(bitmap);
                    post.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Helper.createStrictMode();
                            uploadImageToGallery(image);
                            mydialog2.dismiss();
                        }
                    });
                    flag1=true;


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
        if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this, android.Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
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
    void fetch() throws UnsupportedEncodingException, JSONException {
        Data data2=new Data();
        Log.d("userOLD",usernameOLD);
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8");
        String newData=data2.selectData(getApplicationContext(),"fetchUsers.php",data);
        if(!newData.isEmpty()) {
            JSONArray jsonArray = new JSONArray(newData);

            name = new String[jsonArray.length()];
            dp = new String[jsonArray.length()];
            username = new String[jsonArray.length()];


            JSONObject jsonObject = null;
            length = jsonArray.length();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                //   uid1[i]=jsonObject.getString("uid");
                name[i] = jsonObject.getString("name");
                dp[i] = jsonObject.getString("image");
                username[i] = jsonObject.getString("username");

            }
            Log.d("dp", name[0]);

        }



    }
    void fetchOwnDp() throws UnsupportedEncodingException, JSONException {
        Data data2 = new Data();
        Log.d("userOLD", usernameOLD);
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8");
        String newData = data2.selectData(getApplicationContext(), "fetchOwnDp.php", data);
        if (!newData.isEmpty()) {
            JSONArray jsonArray = new JSONArray(newData);

            ownDp = new String[jsonArray.length()];
            ownName = new String[jsonArray.length()];
            ownEmail = new String[jsonArray.length()];


            JSONObject jsonObject = null;
            length = jsonArray.length();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                ownDp[i] = jsonObject.getString("image");
                ownName[i] = jsonObject.getString("name");
                ownEmail[i] = jsonObject.getString("email");


            }
            TitleName.setText(ownName[0]);
            TitleEmail.setText(ownEmail[0]);
            Log.d("gender",ownDp[0]);
            if(ownDp[0].equals("blank")) {

            }

            else  if(ownDp[0].equals("male"))
            {
                Drawable drawable=getResources().getDrawable(ic_businessman);
               profile.setImageDrawable(drawable);
            }
            else  if(ownDp[0].equals("female"))
            {
                Drawable drawable=getResources().getDrawable(R.drawable.ic_woman);
                profile.setImageDrawable(drawable);
            }
            else
            {
                try {
                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(ownDp[0]).getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profile.setImageBitmap(bitmap);
            }


        }
    }
    void fetchCourse() throws UnsupportedEncodingException, JSONException {
        Data data2=new Data();
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8");
        String newData=data2.selectData(getApplicationContext(),"fetchCollage.php",data);
        JSONArray jsonArray=new JSONArray(newData);
        //init the array;
        //uid1=new String[jsonArray.length()];
        course = new String[jsonArray.length()];



        JSONObject jsonObject=null;
        length=jsonArray.length();
        for (int i=0;i<jsonArray.length();i++)
        {
            jsonObject=jsonArray.getJSONObject(i);

            //   uid1[i]=jsonObject.getString("uid");
            course[i]=jsonObject.getString("collage");


        }
        Log.d("course", String.valueOf(course.length));


    }
    void fetchAge() throws UnsupportedEncodingException, JSONException {
        Data data2=new Data();
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8");
        String newData=data2.selectData(getApplicationContext(),"fetchAge.php",data);
        JSONArray jsonArray=new JSONArray(newData);
        //init the array;
        //uid1=new String[jsonArray.length()];
        age = new String[jsonArray.length()];



        JSONObject jsonObject=null;
        length=jsonArray.length();
        for (int i=0;i<jsonArray.length();i++)
        {
            jsonObject=jsonArray.getJSONObject(i);

            //   uid1[i]=jsonObject.getString("uid");
            age[i]=jsonObject.getString("age");


        }
        Log.d("age", String.valueOf(age.length));


    }
    void fetchDesc() throws UnsupportedEncodingException, JSONException {
        Data data2=new Data();
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8");
        String newData=data2.selectData(getApplicationContext(),"fetchDesc.php",data);
        JSONArray jsonArray=new JSONArray(newData);
        //init the array;
        //uid1=new String[jsonArray.length()];
        desc = new String[jsonArray.length()];



        JSONObject jsonObject=null;
        length=jsonArray.length();
        for (int i=0;i<jsonArray.length();i++)
        {
            jsonObject=jsonArray.getJSONObject(i);

            //   uid1[i]=jsonObject.getString("uid");
            desc[i]=jsonObject.getString("description");


        }

        Log.d("desc", String.valueOf(desc.length));
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
    void uploadImageToGallery(String image2) {
        try {

            //   Log.d("data",image2);
            data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image2, "UTF-8")+
                    "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(usernameOLD,"UTF-8");
            Data obj = new Data();

            obj.insertData(this, "updateGallery.php", data);
        } catch (UnsupportedEncodingException e) {
            Helper.createLog("imageUpload", e.toString());
        }

    }
}


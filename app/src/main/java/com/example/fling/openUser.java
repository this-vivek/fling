package com.example.fling;

import android.Manifest;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fling.R.drawable.ic_businessman;

public class openUser extends AppCompatActivity {

    CircleImageView dp;
    TextView following, follower, name, username, hobbies, bio;
    String dFollowing[],dFollower[],dHobbies[];

    String imageDP;
    String data;
    Bitmap bitmap;
    Button back;
    String followers,address,followings,posts,hobbie;
    String count;
    int length;
    String fetched[];
    String img1[];

    String img2[];
    String usernameOLD;

    String thisUser;

    String rec[]=new String[4];
    private static final int CAMERA_REQUEST = 1888;
    boolean flag=false,flag1=false;
    private int PICK_IMAGE_REQUEST = 1;
    Button chats,search,home;
    CircleImageView profile;
    Dialog mydialog,mydialog2;
    Button post,cancel;
    ImageView galleryPhoto;
    String option;
    Uri filePath;
    RecyclerView imagegallery;
    String image;
    LinearLayout ifNot;
    String ownDp[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_user);
        bindWidget();
        sharedpref();
        ifNot=(LinearLayout)findViewById(R.id.ifNot);
        mydialog=new Dialog(this);
        mydialog2=new Dialog(this);
        profile=(CircleImageView)findViewById(R.id.ownDp);
        chats=(Button)findViewById(R.id.chats);
        home=(Button)findViewById(R.id.home);
        search=(Button)findViewById(R.id.search);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),Home.class);
                startActivity(i);
            }
        });
        Helper.createStrictMode();
        Intent intent=getIntent();
        name.setText(intent.getStringExtra("name"));
        String data=intent.getStringExtra("name");
        thisUser=intent.getStringExtra("username");
        followings=intent.getStringExtra("followings");
        followers=intent.getStringExtra("followers");
        hobbie=intent.getStringExtra("hobbies");
        posts=intent.getStringExtra("posts");
        Log.d("user",thisUser);
        username.setText(thisUser);
        follower.setText(followers);
        following.setText(followings);
        hobbies.setText(hobbie);
        bio.setText(intent.getStringExtra("bio"));
        imageDP=intent.getStringExtra("image");
       // username.setText(usernameOLD);

        if(imageDP.equals("blank")) {

        }

        else  if(imageDP.equals("male"))
        {
            Drawable drawable=getResources().getDrawable(ic_businessman);
            dp.setImageDrawable(drawable);
        }
        else  if(imageDP.equals("female"))
        {
            Drawable drawable=getResources().getDrawable(R.drawable.ic_woman);
            dp.setImageDrawable(drawable);

        } else {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageDP).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dp.setImageBitmap(bitmap);
        }

        try {
            fetchGallery();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
         imagegallery=(RecyclerView)findViewById(R.id.userGallery);
        imagegallery.setLayoutManager(new LinearLayoutManager(this));
        imagegallery.setVisibility(View.VISIBLE);
        ifNot.setVisibility(View.GONE);
        if(!fetched[1].equals("0"))
        {
            imagegallery.setAdapter(new Gallery_Adapter(img1,img2,thisUser,this));
        }
        else
        {
           imagegallery.setVisibility(View.GONE);
           ifNot.setVisibility(View.VISIBLE);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
                finish();
            }
        });
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
                Intent intent=new Intent(getApplicationContext(),Chats.class);
                startActivity(intent);
            }
        });
        try {
            fetchOwnDp();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),BigProfile.class);
                startActivity(i);

            }
        });*/

    }
    void fetchOwnDp() throws UnsupportedEncodingException, JSONException {
        Data data2 = new Data();
        Log.d("userOLD", usernameOLD);
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8");
        String newData = data2.selectData(getApplicationContext(), "fetchOwnDp.php", data);
        if (!newData.isEmpty()) {
            JSONArray jsonArray = new JSONArray(newData);

            ownDp = new String[jsonArray.length()];


            JSONObject jsonObject = null;
            length = jsonArray.length();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                ownDp[i] = jsonObject.getString("image");


            }
      /*      if(ownDp[0].equals("blank")) {

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
*/

        }
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),Home.class)
    }*/
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
                            Intent i=getIntent();
                            startActivity(i);
                            finish();
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(openUser.this, android.Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(openUser.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
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
    private void bindWidget() {
        dp = (CircleImageView) findViewById(R.id.profilePic);
        following = (TextView) findViewById(R.id.following);
        follower = (TextView) findViewById(R.id.follower);
        name = (TextView) findViewById(R.id.name);
        bio = (TextView) findViewById(R.id.bio);
        username=(TextView)findViewById(R.id.username);
        hobbies = (TextView) findViewById(R.id.interest);
        back = (Button) findViewById(R.id.back);
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




    public void sharedpref() {
        SharedPreferences sharedPreferences = getSharedPreferences("username", 0);


        usernameOLD = sharedPreferences.getString("username", "NA");


    }
    void fetchGallery() throws IOException, JSONException {
        Data obj = new Data();
         fetched=new String[2];
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(thisUser, "UTF-8");
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
        if(fetched[1].equals("0")) {
        return;
        }
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
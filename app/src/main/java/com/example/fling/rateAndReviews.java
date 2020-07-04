package com.example.fling;

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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fling.Data.Data;
import com.example.fling.Helper.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fling.R.drawable.ic_businessman;

public class rateAndReviews extends AppCompatActivity {

    RatingBar ratingBar;
    EditText comment;
    String name[],rating[],fetchedComments[],dp[],username[];
    String usernameOLD;
    String rec[]=new String[3];
    Button post,editPost;
    String userRating,userComment;
    String passed_name="",passed_image="";
    TextView user_name,user_comment,editComment,editButton;
    RatingBar user_rating,editUserRating;
    Bitmap bitmap;
    int length,length2;
    CircleImageView userDp;
    LinearLayout reviewSection,editReview,editReviewSection;
    reviewAdapter recycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.createStrictMode();
        sharedpref();
        Intent i=getIntent();
        passed_name=i.getStringExtra("name");
        passed_image=i.getStringExtra("dp");
        setContentView(R.layout.activity_rate_and_reviews);
        bindWidget();
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postRating(ratingBar.getRating(),comment.getText().toString());
                    Intent i=getIntent();
                    startActivity(i);

                    finish();
                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editReviewSection.setVisibility(View.VISIBLE);
                editReview.setVisibility(View.INVISIBLE);

            }
        });
        editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateRating(editUserRating.getRating(),editComment.getText().toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i=getIntent();
                startActivity(i);
                finish();
            }
        });
        String flag= null;
        try {
            flag = checkReviewExist();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(flag.equals("true"))
        {
            makeEditReview();

            reviewSection.setVisibility(View.GONE);
            editReview.setVisibility(View.VISIBLE);
        }
        else
        {
            reviewSection.setVisibility(View.VISIBLE);
            editReview.setVisibility(View.GONE);
        }

        try {
            fetchReviews();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("length", String.valueOf(length));
        if(length!=0) {
            RecyclerView reviewList=(RecyclerView)findViewById(R.id.showReview);
            reviewList.setLayoutManager(new LinearLayoutManager(this));
            recycleAdapter = new reviewAdapter(this, name, rating, fetchedComments, dp);
            reviewList.setAdapter(recycleAdapter);


        }
    }

    private void fetchReviews() throws UnsupportedEncodingException, JSONException {
        Data data2 = new Data();
        Log.d("userOLD", usernameOLD);
       String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8");
        String newData = data2.selectData(getApplicationContext(), "fetchFromReview.php", data);
        if (!newData.isEmpty()) {
            JSONArray jsonArray = new JSONArray(newData);

            name = new String[jsonArray.length()];
            rating = new String[jsonArray.length()];
            fetchedComments = new String[jsonArray.length()];
            dp = new String[jsonArray.length()];
            username = new String[jsonArray.length()];


            JSONObject jsonObject = null;
            length = jsonArray.length();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                username[i] = jsonObject.getString("username");
                fetchOtherDetails(username[i],i);
                fetchedComments[i] = jsonObject.getString("comment");
                rating[i] = jsonObject.getString("rating");


            }
        }
    }

    private void fetchOtherDetails(String user,int j) throws UnsupportedEncodingException, JSONException {
        Data data2 = new Data();
        Log.d("userOLD", usernameOLD);
        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
        String newData = data2.selectData(getApplicationContext(), "fetchFromUsersForReview.php", data);
        if (!newData.isEmpty()) {
            JSONArray jsonArray = new JSONArray(newData);

            JSONObject jsonObject = null;
            length2 = jsonArray.length();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                name[j] = jsonObject.getString("name");
                dp[j] = jsonObject.getString("image");

            }
        }
    }

    private void postRating(float rating, String comment) throws UnsupportedEncodingException, JSONException {
      String  data = URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(rating), "UTF-8")+
                "&"+URLEncoder.encode("comment","UTF-8")+"="+URLEncoder.encode(comment,"UTF-8")+
                "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(usernameOLD,"UTF-8");
        Data obj = new Data();

        obj.insertData(getApplicationContext(), "postReview.php", data);
    }
    private void updateRating(float rating, String comment) throws UnsupportedEncodingException, JSONException {
        String  data = URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(rating), "UTF-8")+
                "&"+URLEncoder.encode("comment","UTF-8")+"="+URLEncoder.encode(comment,"UTF-8")+
                "&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(usernameOLD,"UTF-8");
        Data obj = new Data();

        obj.insertData(getApplicationContext(), "updateReview.php", data);
    }
    private void makeEditReview() {
        if(passed_image.equals("blank")) {

        }

        else  if(passed_image.equals("male"))
        {
            Drawable drawable=getResources().getDrawable(ic_businessman);
            userDp.setImageDrawable(drawable);
        }
        else  if(passed_image.equals("female"))
        {
            Drawable drawable=getResources().getDrawable(R.drawable.ic_woman);
            userDp.setImageDrawable(drawable);

        }
        else {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(passed_image).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            userDp.setImageBitmap(bitmap);
        }
        Log.d("passedImage",passed_image);

        user_name.setText(passed_name);
        float stars= Float.parseFloat(userRating);
        user_rating.setRating(stars);
        user_comment.setText(userComment);
        editComment.setText(userComment);
        editUserRating.setRating(stars);



    }

    private String checkReviewExist() throws UnsupportedEncodingException, JSONException {
        Data obj = new Data();
        Log.d("username",usernameOLD);
       String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameOLD, "UTF-8");
        String data2 = obj.selectData(getApplicationContext(), "checkReview.php", data);
        JSONObject jsonObject = new JSONObject(data2);
        String count = jsonObject.getString("code");
        String temp= String.valueOf(count);
        Log.d("dataString",count);
        StringTokenizer stringTokenizer=new StringTokenizer(temp);
        int i=0;
        while(stringTokenizer.hasMoreTokens()) {
            rec[i]=stringTokenizer.nextToken("|");
            i++;
        }
        if(rec[0].equals("true"))
        {
            userComment=rec[2];
            userRating=rec[1];
            return "true";
        }
        else
        {
            return "false";
        }
    }

    public void sharedpref() {
        SharedPreferences sharedPreferences=getSharedPreferences("username",0);



        usernameOLD=sharedPreferences.getString("username","NA");


    }
    private void bindWidget() {
    ratingBar=(RatingBar)findViewById(R.id.ratingBar);
    comment=(EditText)findViewById(R.id.comment);
    editReview=(LinearLayout)findViewById(R.id.editReview);
    reviewSection=(LinearLayout)findViewById(R.id.reviewSection);
    user_comment=(TextView)findViewById(R.id.userReview);
    user_rating=(RatingBar)findViewById(R.id.userRating);
    user_name=(TextView)findViewById(R.id.name);
    post=(Button)findViewById(R.id.post);
    editPost=(Button)findViewById(R.id.editPost);
    userDp=(CircleImageView)findViewById(R.id.userDp);
    editReviewSection=(LinearLayout)findViewById(R.id.EditReviewSection);
    editComment=(TextView)findViewById(R.id.editComment);
    editUserRating=(RatingBar)findViewById(R.id.editRatingBar);
    editButton=(TextView)findViewById(R.id.editButton);
    }
}
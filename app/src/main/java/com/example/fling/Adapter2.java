package com.example.fling;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.example.fling.Data.Data;
import com.example.fling.Helper.Connection;
import com.example.fling.Helper.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fling.R.drawable.ic_businessman;

public class Adapter2 extends PagerAdapter {
    private String name[];
    Dialog mydialog;
    private String course[],description[],age[],dp[],username1[];
    String usernameOLD;
    Bitmap bitmap;
    CardView cardView;
    String username2;
    LinearLayout linearLayout;
    Context context;
    String data;
    String followers,address,followings,posts,hobbies;
    String rec[]=new String[5];
    LayoutInflater layoutInflater;


    public Adapter2(String[] name, String[] course, String[] description, String[] age, String[] dp, String[] username1,String usernameOLD,Context context) {
        this.name = name;
        this.course = course;
        this.description = description;
        this.age = age;
        this.dp = dp;
        this.username1=username1;
        this.usernameOLD=usernameOLD;
        this.context=context;
    }
    @Override
    public int getCount() {
        return dp.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater=LayoutInflater.from(context);
        View itemView=layoutInflater.inflate(R.layout.adapter2,container,false);
        ImageView photo;

        TextView username,userage,userdesc,usercourse;

        SwipeButton swipeButton;

            photo=(ImageView)itemView.findViewById(R.id.photo);
            cardView=(CardView)itemView.findViewById(R.id.cardView);
            username=(TextView)itemView.findViewById(R.id.mainName);
            userage=(TextView)itemView.findViewById(R.id.mainAge);
            mydialog=new Dialog(context);
            usercourse=(TextView)itemView.findViewById(R.id.mainCourse);
            userdesc=(TextView)itemView.findViewById(R.id.mainDesc);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.singleton);
            swipeButton=(SwipeButton)itemView.findViewById(R.id.swipeToFollow);
        username2=username1[position];
        Log.d("position",username1[position]);
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                try {
                    follow(username1[position]);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        String name1=name[position];
        Log.d("name",name1);
        username.setText(name1);
        String age1=age[position];
        userage.setText(age1);
        String course1=course[position];
        usercourse.setText(course1);
        String desc1=description[position];
        userdesc.setText(desc1);
        String image1=dp[position];
        Log.d("desc",desc1);
        Log.d("age",age1);

        if(image1.equals("blank")) {

        }

        else  if(image1.equals("male"))
        {
            Drawable drawable=context.getResources().getDrawable(ic_businessman);
            photo.setImageDrawable(drawable);
        }
        else  if(image1.equals("female"))
        {
            Drawable drawable=context.getResources().getDrawable(R.drawable.ic_woman);
            photo.setImageDrawable(drawable);

        }
        else {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(image1).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            photo.setImageBitmap(bitmap);
        }
       extras(position);

        container.addView(itemView,0);
        return itemView;
    }

    private void extras(final int position) {

       cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                mydialog.setContentView(R.layout.user_popup);
                TextView dialogName = (TextView) mydialog.findViewById(R.id.name);
                final Button follow = (Button) mydialog.findViewById(R.id.follow);
                Button open = (Button) mydialog.findViewById(R.id.open);
                TextView addr = (TextView) mydialog.findViewById(R.id.addr);
                TextView following = (TextView) mydialog.findViewById(R.id.following);
                TextView follower = (TextView) mydialog.findViewById(R.id.follower);
                TextView post = (TextView) mydialog.findViewById(R.id.posts);
                CircleImageView dialogdp = (CircleImageView) mydialog.findViewById(R.id.dp);
                try {
                    fetch(username1[position]);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Button cancel = (Button) mydialog.findViewById(R.id.cancel);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydialog.dismiss();
                    }
                });
                dialogName.setText(name[position]);
                addr.setText(address);
                following.setText(followings);
                follower.setText(followers);
                post.setText(posts);
                Log.d("dp",dp[position]);
                if(dp[position].equals("blank")) {

                }

                else  if(dp[position].equals("male"))
                {
                    Drawable drawable=context.getResources().getDrawable(R.drawable.ic_businessman);
                    dialogdp.setImageDrawable(drawable);
                }
                else  if(dp[position].equals("female"))
                {
                    Drawable drawable=context.getResources().getDrawable(R.drawable.ic_woman);
                    dialogdp.setImageDrawable(drawable);

                } else {
                    try {
                        bitmap = BitmapFactory.decodeStream((InputStream) new URL(dp[position]).getContent());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialogdp.setImageBitmap(bitmap);
                }

                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, openUser.class);
                        intent.putExtra("bio", description[position]);
                        intent.putExtra("image", dp[position]);
                        intent.putExtra("name", name[position]);
                        intent.putExtra("username", username1[position]);
                        intent.putExtra("followings", followings);
                        intent.putExtra("followers", followers);
                        intent.putExtra("hobbies", hobbies);
                        intent.putExtra("posts", posts);
                        mydialog.dismiss();
                        context.startActivity(intent);


                    }
                });
                follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            follow(username1[position]);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        follow.setText("unfollow");
                    }
                });

                mydialog.show();



            }
        });

    }
    void follow(String username) throws UnsupportedEncodingException {
        String data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+
                "&"+URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(usernameOLD,"UTF-8")+
                "&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("following","UTF-8");
        Log.d("username",username+usernameOLD);
        try
        {
            HttpURLConnection conn= Connection.createConnection("addFriends.php");
            OutputStream outputStream=conn.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();
            InputStream inputStream=conn.getInputStream();
            conn.disconnect();
            Helper.createLog("DATA","data Insert");

        }
        catch (Exception e)
        {
            Helper.createLog("DATA","DATA"+e.toString());
        }
    }
    void fetch(String username) throws IOException, JSONException {
        Data obj = new Data();
        data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
        String data2 = obj.selectData(context, "fetchUserProfile.php", data);
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

        followings=rec[0];
        followers=rec[1];
        posts=rec[2];
        address=rec[3];
        hobbies=rec[4];

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}

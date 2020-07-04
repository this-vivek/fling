package com.example.fling;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> {
    private String name[];
    private String course[],description[],age[],dp[],username[];
    String usernameOLD;
    Bitmap bitmap;
    String username2;
    Context context;
    String data;
    String followers,address,followings,posts,hobbies;
    String rec[]=new String[5];


    public RecycleAdapter(String[] name, String[] course, String[] description, String[] age, String[] dp, String[] username,String usernameOLD,Context context) {
        this.name = name;
        this.course = course;
        this.description = description;
        this.age = age;
        this.dp = dp;
        this.username=username;
        this.usernameOLD=usernameOLD;
        this.context=context;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycle_list_data,parent,false);

        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewHolder holder, final int position) {
        username2=username[position];
        Log.d("position",username[position]);
        holder.swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                try {
                    follow(username[position]);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        String name1=name[position];
        holder.username.setText(name1);
        String age1=age[position];
        holder.userage.setText(age1);
        String course1=course[position];
        holder.usercourse.setText(course1);
        String desc1=description[position];
        holder.userdesc.setText(desc1);
        String image1=dp[position];

        if(image1.equals("blank"))
        {

        }
        else {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(image1).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.photo.setImageBitmap(bitmap);
        }
        if(holder.progressDialog.isShowing())
        {
            holder.progressDialog.dismiss();
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mydialog.setContentView(R.layout.user_popup);
                TextView dialogName=(TextView)holder.mydialog.findViewById(R.id.name);
                final Button follow=(Button) holder.mydialog.findViewById(R.id.follow);
                Button open=(Button) holder.mydialog.findViewById(R.id.open);
                TextView addr=(TextView)holder.mydialog.findViewById(R.id.addr);
                TextView following=(TextView)holder.mydialog.findViewById(R.id.following);
                TextView follower=(TextView)holder.mydialog.findViewById(R.id.follower);
                TextView post=(TextView)holder.mydialog.findViewById(R.id.posts);
                CircleImageView dialogdp=(CircleImageView) holder.mydialog.findViewById(R.id.dp);
                try {
                    fetch(username[position]);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Button cancel=(Button)holder.mydialog.findViewById(R.id.cancel);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.mydialog.dismiss();
                    }
                });
                dialogName.setText(name[position]);
                addr.setText(address);
                following.setText(followings);
                follower.setText(followers);
                post.setText(posts);
                if(dp[position].equals("blank"))
                {

                }
                else {
                    try {
                        bitmap = BitmapFactory.decodeStream((InputStream) new URL(dp[position]).getContent());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                dialogdp.setImageBitmap(bitmap);
                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.progressDialog.show();
                        holder.progressDialog.setContentView(R.layout.progress_bar);
                        holder.progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Intent intent=new Intent(context,openUser.class);
                intent.putExtra("bio",description[position]);
                intent.putExtra("image",dp[position]);
                intent.putExtra("name",name[position]);
                intent.putExtra("username",username[position]);
                intent.putExtra("followings",followings);
                intent.putExtra("followers",followers);
                intent.putExtra("hobbies",hobbies);
                intent.putExtra("posts",posts);
                        holder.mydialog.dismiss();
                context.startActivity(intent);


                    }
                });
                follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            follow(username[position]);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        follow.setText("unfollow");
                    }
                });

                holder.mydialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        Dialog mydialog;
        TextView username,userage,userdesc,usercourse;
        LinearLayout linearLayout;
        ProgressDialog progressDialog;
        SwipeButton swipeButton;
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=(ImageView)itemView.findViewById(R.id.photo);
            username=(TextView)itemView.findViewById(R.id.mainName);
            userage=(TextView)itemView.findViewById(R.id.mainAge);
            mydialog=new Dialog(context);
            usercourse=(TextView)itemView.findViewById(R.id.mainCourse);
            progressDialog=new ProgressDialog(context);
            userdesc=(TextView)itemView.findViewById(R.id.mainDesc);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.singleton);
            swipeButton=(SwipeButton)itemView.findViewById(R.id.swipeToFollow);
        }
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

    public class Adapter {
    }
}

package com.example.fling;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fling.R.drawable.ic_businessman;

class RecyclerChatsAdapter extends RecyclerView.Adapter<RecyclerChatsAdapter.RecycleViewHolder> {
    private String name[],dp[];
    Context context;
    String msg[];
    String username[];
    String usernameOLD;
Bitmap bitmap;
    public RecyclerChatsAdapter(String[] name, String[] dp, Context context,String[] username) {
        this.name = name;
        this.dp = dp;
      //  this.msg=msg;

        this.context=context;
        this.username=username;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycle_chats_data,parent,false);

        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewHolder holder, final int position) {
        String image1=dp[position];
        if(image1.equals("blank")) {

        }

        else  if(image1.equals("male"))
        {
            Drawable drawable=context.getResources().getDrawable(ic_businessman);
            holder.chatPic.setImageDrawable(drawable);
        }
        else  if(image1.equals("female"))
        {
            Drawable drawable=context.getResources().getDrawable(R.drawable.ic_woman);
            holder.chatPic.setImageDrawable(drawable);

        }
        else {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(image1).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.chatPic.setImageBitmap(bitmap);
        }
        String title=name[position];
        holder.chatUser.setText(title);
        try {
            if (!msg[position].isEmpty()) {
                holder.lastMsg.setText(msg[position]);
            }
        }
        catch (Exception e)
        {
            holder.lastMsg.setText("no last message");
        }
        if(holder.progressDialog.isShowing())
        {
            holder.progressDialog.dismiss();
        }



       // String last=lastMsg[position];
       // holder.lastMsg.setText(last);

        holder.chatHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.progressDialog.show();
                holder.progressDialog.setContentView(R.layout.progress_bar);
                holder.progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Intent intent=new Intent(context,InsideChats.class);
                intent.putExtra("user_id",username[position]);
                intent.putExtra("image",dp[position]);
                intent.putExtra("name",name[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{
        CircleImageView chatPic;
        TextView chatUser;
        TextView lastMsg;
        LinearLayout chatHead;
        ProgressDialog progressDialog;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            lastMsg=(TextView)itemView.findViewById(R.id.chatSub);
            chatPic= (CircleImageView) itemView.findViewById(R.id.chatPic);
            chatUser=(TextView)itemView.findViewById(R.id.chatUser);
            progressDialog=new ProgressDialog(context);
            chatHead=(LinearLayout)itemView.findViewById(R.id.chatHead);

        }
    }
    public void sharedpref() {
        SharedPreferences sharedPreferences=context.getSharedPreferences("username",0);



        usernameOLD=sharedPreferences.getString("username","NA");


    }
}

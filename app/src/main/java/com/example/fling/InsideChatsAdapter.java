package com.example.fling;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class InsideChatsAdapter extends RecyclerView.Adapter<InsideChatsAdapter.InsideChatViewHolder> {
    String username[],userid[],message[],status[];
    Context context;
    String usernameOLD;
    public InsideChatsAdapter(String[] username, String[] userid, String[] message, String[] status, Context context) {
        this.username = username;
        this.userid = userid;
        this.message = message;
        this.status=status;
        this.context=context;
    }

    @NonNull
    @Override
    public InsideChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.inside_chat_adapter,parent,false);

        return new InsideChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsideChatViewHolder holder, int position) {
        sharedpref();
        String msg=message[position];
        Log.d("status",userid[position]);
        if(status[position].equals(usernameOLD)) {
            holder.sendLayout.setVisibility(View.VISIBLE);
            holder.send.setText(msg);
            holder.recieveLayout.setVisibility(View.GONE);
        }
        else {
            holder.recieveLayout.setVisibility(View.VISIBLE);
            holder.recieve.setText(msg);
            holder.send.setText("");
            holder.sendLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return userid.length;
    }

    public class InsideChatViewHolder extends RecyclerView.ViewHolder {
        TextView recieve,send;
        LinearLayout recieveLayout,sendLayout;
        public InsideChatViewHolder(@NonNull View itemView) {
            super(itemView);
            recieve=(TextView)itemView.findViewById(R.id.recieve);
            recieveLayout=(LinearLayout)itemView.findViewById(R.id.recieveLayout);
            sendLayout=(LinearLayout)itemView.findViewById(R.id.sendLayout);
            send=(TextView)itemView.findViewById(R.id.send);
        }
    }
    public void sharedpref() {
        SharedPreferences sharedPreferences=context.getSharedPreferences("username",0);



        usernameOLD=sharedPreferences.getString("username","NA");


    }
}

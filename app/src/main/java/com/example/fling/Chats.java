package com.example.fling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

public class Chats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        String data[]={"vivek","raju","shailesh","praveen","pankaj","sanjay"};
        RecyclerView chatList=(RecyclerView)findViewById(R.id.recycleChats);
        chatList.setLayoutManager(new LinearLayoutManager(this));
        chatList.setAdapter(new RecyclerChatsAdapter(data));

    }
}

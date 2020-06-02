package com.example.fling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Home extends AppCompatActivity {
    LinearLayout constraintLayout;
    LinearLayout layout2,layout3;
    Button home,chats,profile,search;
    static boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        constraintLayout=(LinearLayout) findViewById(R.id.layout1);
        layout2=(LinearLayout) findViewById(R.id.layout2);
        layout3=(LinearLayout) findViewById(R.id.layout3);
        home=(Button)findViewById(R.id.home);
        profile=(Button)findViewById(R.id.profile);
        chats=(Button)findViewById(R.id.chats);
        search=(Button)findViewById(R.id.search);
        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Chats.class);
                startActivity(intent);
            }
        });
        RecyclerView dataList=(RecyclerView)findViewById(R.id.dataList);
        dataList.setLayoutManager(new LinearLayoutManager(this));
        String data[]={"cats","dogs","horse","monkey","sheer","billi","chidiya","meow","bhaw"};
        dataList.setAdapter(new RecycleAdapter(data));
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Profile.class);
                startActivity(intent);
            }
        });
    }
}

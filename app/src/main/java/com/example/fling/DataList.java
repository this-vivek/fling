package com.example.fling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class DataList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        RecyclerView dataList=(RecyclerView)findViewById(R.id.dataList);
        dataList.setLayoutManager(new LinearLayoutManager(this));
        String data[]={"cats","dogs","horse","monkey","sheer","billi","chidiya","meow","bhaw"};
        dataList.setAdapter(new RecycleAdapter(data));

    }
}

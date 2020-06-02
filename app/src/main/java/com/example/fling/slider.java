package com.example.fling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class slider extends AppCompatActivity {

    ViewPager viewPager;
    int images[]={R.drawable.ic_newbg,R.drawable.ic_black_bg,R.drawable.ic_bmen};
    String names[]={"pankaj","raju","vivek"};
    CustomPager customPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        viewPager=(ViewPager)findViewById(R.id.viewPage);
        customPager=new CustomPager(slider.this,images,names);
        viewPager.setAdapter(customPager);
    }
}

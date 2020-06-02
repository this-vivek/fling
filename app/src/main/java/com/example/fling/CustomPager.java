package com.example.fling;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class CustomPager extends PagerAdapter {
    Context context;
    int images[];
    String names[];
    LayoutInflater layoutInflater;

    public CustomPager(Context context, int[] images, String[] names) {
        this.context = context;
        this.images = images;
        this.names = names;
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==((LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemview=layoutInflater.inflate(R.layout.custom,container,false);
        //here we use item layout class and create view

        //we have to find imageview that present on item layout file
        ImageView imageView=(ImageView)itemview.findViewById(R.id.image1);
        TextView textView=(TextView)itemview.findViewById(R.id.text2);
        //adding images to imageView
        imageView.setImageResource(images[position]);
        container.addView(itemview);
        textView.setText(names[position]);
        container.addView(textView);
        //click listener for image position
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "youc clicked"+position, Toast.LENGTH_SHORT).show();
            }
        });
        return itemview;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}

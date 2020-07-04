package com.example.fling;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Gallery_Adapter extends RecyclerView.Adapter<Gallery_Adapter.RecycleViewHolder>{
    String images1[];
    String images2[];
    Bitmap bitmap;
    String username;
    Context context;

    public Gallery_Adapter(String[] images1, String[] images2, String username, Context context) {
        this.images1 = images1;
        this.images2 = images2;
        this.username = username;
        this.context = context;
    }

    @NonNull

    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext() );
        View view=inflater.inflate(R.layout.userprofile_adapter,parent,false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
String first=images1[position];
if(!first.equals("")) {
    try {
        bitmap = BitmapFactory.decodeStream((InputStream) new URL(images1[position]).getContent());
    } catch (IOException e) {
        e.printStackTrace();
    }
    holder.firstImg.setImageBitmap(bitmap);
}
        String second=images2[position];
        if(!second.equals("empty")) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(images2[position]).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.secondImg.setImageBitmap(bitmap);
        }
    }



    @Override
    public int getItemCount() {
        return images1.length;
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        ImageView firstImg,secondImg;
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            firstImg=(ImageView)itemView.findViewById(R.id.firstImg);
            secondImg=(ImageView)itemView.findViewById(R.id.secondImg);
        }
    }
}

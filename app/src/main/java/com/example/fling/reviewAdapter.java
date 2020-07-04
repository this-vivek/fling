package com.example.fling;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fling.R.drawable.ic_businessman;

public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.RecycleViewHolder> {
Context context;
String name[],rating[],comment[],dp[];
    Bitmap bitmap;

    public reviewAdapter(Context context, String[] name, String[] rating, String[] comment, String[] dp) {
        this.context = context;
        this.name = name;

        this.rating = rating;
        this.comment = comment;
        this.dp = dp;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.reviews_recycle,parent,false);

        return new reviewAdapter.RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        if(dp[position].equals("blank")) {

        }

        else  if(dp[position].equals("male"))
        {

            Drawable drawable= context.getResources().getDrawable(ic_businessman);
            holder.viewDp.setImageDrawable(drawable);
        }
        else  if(dp[position].equals("female"))
        {
            Drawable drawable=context.getResources().getDrawable(R.drawable.ic_woman);
            holder.viewDp.setImageDrawable(drawable);
        }
        else
        {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(dp[position]).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.viewDp.setImageBitmap(bitmap);
        }
        holder.viewName.setText(name[position]);
        holder.viewComment.setText(comment[position]);
        holder.viewRating.setRating(Float.parseFloat(rating[position]));


    }



    @Override
    public int getItemCount() {
        return name.length;
    }


    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView viewName,viewComment;
        RatingBar viewRating;
        CircleImageView viewDp;
        public RecycleViewHolder(@NonNull View holder) {
            super(holder);
            viewName=(TextView)holder.findViewById(R.id.name);
            viewComment=(TextView)holder.findViewById(R.id.review);
            viewRating=(RatingBar) holder.findViewById(R.id.rating);
            viewDp=(CircleImageView) holder.findViewById(R.id.dp);


        }
    }
}

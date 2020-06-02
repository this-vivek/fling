package com.example.fling;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> {
    private String data[];

    public RecycleAdapter(String[] data)
    {
        this.data=data;
    }
    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycle_list_data,parent,false);

        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewHolder holder, int position) {
        String title=data[position];
        holder.myText.setText(title);
        holder.swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                return;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView myText;
        SwipeButton swipeButton;
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=(ImageView)itemView.findViewById(R.id.photo);
            myText=(TextView)itemView.findViewById(R.id.mainName);
            swipeButton=(SwipeButton)itemView.findViewById(R.id.swipeToFollow);
        }
    }
}

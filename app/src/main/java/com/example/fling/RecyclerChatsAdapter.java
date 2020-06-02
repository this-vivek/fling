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

class RecyclerChatsAdapter extends RecyclerView.Adapter<RecyclerChatsAdapter.RecycleViewHolder> {
    private String data[];

    public RecyclerChatsAdapter(String[] data)
    {
        this.data=data;
    }
    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycle_chats_data,parent,false);

        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewHolder holder, int position) {
        String title=data[position];
        holder.chatUser.setText(title);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{
        ImageView chatPic;
        TextView chatUser;
        SwipeButton swipeButton;
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            chatPic=(ImageView)itemView.findViewById(R.id.chatPic);
            chatUser=(TextView)itemView.findViewById(R.id.chatUser);

        }
    }
}

package com.example.parcial2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parcial2.Activitys.ChatActivity;
import com.example.parcial2.Entity.Chat;
import com.example.parcial2.R;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    List<Chat> chatList;
    Context context;

    public ChatListAdapter(List<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_template, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.tv_name.setText(chat.getSenderID().equals(context.getSharedPreferences("currentUser", Context.MODE_PRIVATE).getString("currentUserId", "")) ? chat.getReceiverID() : chat.getSenderID());
        holder.tv_LastMessage.setText(chat.getMessage());
        holder.tv_time.setText(chat.getTimestamp());
        // Assuming the first user in the list is the chat partner
        Glide.with(this.context).load(R.drawable.person_icon).into(holder.imgv_pfp); // Placeholder image
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", chat.getSenderID().equals(context.getSharedPreferences("currentUser", Context.MODE_PRIVATE).getString("currentUserId", "")) ? chat.getReceiverID() : chat.getSenderID());
                bundle.putString("name", holder.tv_name.getText().toString());
                bundle.putString("number", ""); // Add logic to get the number if available
                bundle.putString("pfp", ""); // Add logic to get the profile picture if available
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void updateChatList(List<Chat> newChatList) {
        this.chatList = newChatList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgv_pfp;
        TextView tv_name, tv_LastMessage, tv_time;
        ConstraintLayout parentLayout;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgv_pfp = itemView.findViewById(R.id.imgv_pfp);
            tv_name = itemView.findViewById(R.id.tvName);
            tv_LastMessage = itemView.findViewById(R.id.tvLastMessage);
            tv_time = itemView.findViewById(R.id.tvTime);
            parentLayout = itemView.findViewById(R.id.contactTemplate);
        }
    }
}






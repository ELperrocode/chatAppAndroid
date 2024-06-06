package com.example.parcial2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parcial2.Activitys.ChatActivity;
import com.example.parcial2.Activitys.MainActivity;
import com.example.parcial2.Entity.Chat;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;

import java.util.List;

public class ChatLisadapter extends RecyclerView.Adapter<ChatLisadapter.MyViewHolder> implements ListAdapter {

    List<Chat> chatList;
    Context context;
    List<User> users;

    public ChatLisadapter(List<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
        this.users = users;
    }

    public ChatLisadapter(MainActivity mainActivity, List<Chat> chatList, List<User> users) {
        this.chatList = chatList;
        this.context = mainActivity;
        this.users = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        String currentUserId = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE).getString("currentUserId", "");
        String contactId = chat.getSenderID().equals(currentUserId) ? chat.getReceiverID() : chat.getSenderID();

        // Recuperar los detalles del usuario por ID
        User contact = getUserById(contactId);

        holder.tv_name.setText(contact.getName());
        holder.tv_LastMessage.setText(chat.getMessage());
        holder.tv_time.setText(chat.getTimestamp());
        Glide.with(this.context).load(contact.getPfp()).into(holder.imgv_pfp);

        holder.parentLayout.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", contactId);
            bundle.putString("name", contact.getName());
            bundle.putString("number", contact.getNumber());
            bundle.putString("pfp", contact.getPfp());
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
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

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
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

    private User getUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}





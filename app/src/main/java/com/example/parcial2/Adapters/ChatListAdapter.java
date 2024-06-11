package com.example.parcial2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parcial2.Activitys.ChatActivity;
import com.example.parcial2.Entity.Chat;
import com.example.parcial2.R;

import java.util.List;

public class ChatListAdapter extends ArrayAdapter<Chat> {

    private List<Chat> chatList;
    private Context context;

    public ChatListAdapter(Context context, List<Chat> chatList) {
        super(context, R.layout.contact_template, chatList);
        this.context = context;
        this.chatList = chatList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.contact_template, parent, false);
        }

        Chat chat = chatList.get(position);

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvLastMessage = convertView.findViewById(R.id.tvLastMessage);
        TextView tvId = convertView.findViewById(R.id.tvTime);
        ImageView imgvPfp = convertView.findViewById(R.id.imgv_pfp);

        tvName.setText(chat.getReceiverName());
        tvLastMessage.setText(chat.getMessage());
        tvId.setText(chat.getReceiverID());
        Glide.with(context).load(chat.getReceiverPfp()).into(imgvPfp);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("receiverId", chat.getReceiverID());
            intent.putExtra("receiverName", chat.getReceiverName());
            intent.putExtra("receiverPfp", chat.getReceiverPfp());
            context.startActivity(intent);
        });

        return convertView;
    }

    public void refreshList() {
        notifyDataSetChanged();
    }
}







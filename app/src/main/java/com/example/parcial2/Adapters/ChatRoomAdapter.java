package com.example.parcial2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial2.Entity.Chat;
import com.example.parcial2.R;

import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatModelViewHolder> {

    private List<Chat> chatList;
    private Context context;
    private String currentUserId;

    public ChatRoomAdapter(List<Chat> chatList, Context context, String currentUserId) {
        this.chatList = chatList;
        this.context = context;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row, parent, false);
        return new ChatModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position) {
        Chat chat = chatList.get(position);

        if (chat.getSenderID().equals(currentUserId)) {
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            holder.rightChatTextview.setText(chat.getMessage());
        } else  {
            holder.rightChatLayout.setVisibility(View.GONE);
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            holder.leftChatTextview.setText(chat.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void updateChatList(List<Chat> newChatList) {
        this.chatList = newChatList;
        notifyDataSetChanged();
    }

    class ChatModelViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftChatLayout, rightChatLayout;
        TextView leftChatTextview, rightChatTextview;

        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);

            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatTextview = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextview = itemView.findViewById(R.id.right_chat_textview);
        }
    }
}


package com.example.parcial2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parcial2.Activitys.ChatActivity;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;

import java.util.List;

public class ContactListAdapter extends BaseAdapter {

    private List<User> userList;
    private Context context;

    public ContactListAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.contact_template, parent, false);
        }

        User user = userList.get(position);

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvLastMessage = convertView.findViewById(R.id.tvLastMessage);
        ImageView imgvPfp = convertView.findViewById(R.id.imgv_pfp);

        tvName.setText(user.getName());
        tvLastMessage.setText(user.getNumber());
        Glide.with(context).load(user.getPfp()).into(imgvPfp);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", user.getId());
            bundle.putString("name", user.getName());
            bundle.putString("pfp", user.getPfp());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        return convertView;
    }
}



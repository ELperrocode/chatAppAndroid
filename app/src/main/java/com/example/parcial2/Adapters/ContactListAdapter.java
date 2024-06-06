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
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter <ContactListAdapter.MyViewHolder> {

    List<User> users;
    Context context;

    public ContactListAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_template,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_name.setText(users.get(position).getName());
        holder.tv_LastMessage.setText(users.get(position).getNumber());
        holder.tv_time.setText(users.get(position).getId());
        Glide.with(this.context).load(users.get(position).getPfp()).into(holder.imgv_pfp);
        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name",users.get(position).getName());
                bundle.putString("number",users.get(position).getNumber());
                bundle.putString("id",users.get(position).getId());
                bundle.putString("pfp",users.get(position).getPfp());
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
        }//end intent
        });//end of setOnClickListener
    }//end of onBindViewHolder

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgv_pfp;
        TextView tv_name, tv_LastMessage,tv_time;
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

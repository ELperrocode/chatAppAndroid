package com.example.parcial2.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.parcial2.Adapters.ChatLisadapter;
import com.example.parcial2.Entity.Chat;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listViewChats;
    FloatingActionButton addChatBtn;
    ImageButton goProfileBtn;
    ChatLisadapter chatLisadapter;
    List<Chat> chatList = new ArrayList<>();
    List<User> users = new ArrayList<>();
    ImageView MainPfp;
    TextView CUName;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeControls();
        addBurnContacts();

        loadUserProfile();

        addChatBtn.setOnClickListener(v -> goContacts());
        goProfileBtn.setOnClickListener(v -> goProfile());

    }

    void InitializeControls() {
        MainPfp = findViewById(R.id.MainPfp);
        CUName = findViewById(R.id.TvCurrUser);
        addChatBtn = findViewById(R.id.addChatBtn);
        goProfileBtn = findViewById(R.id.PfBtn);


    }

    private void goContacts() {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivity(intent);
    }

    private void goProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void loadUserProfile() {
        SharedPreferences preferences = getSharedPreferences("currentUser", MODE_PRIVATE);
        String userName = preferences.getString("currentUserName", "Nombre");
        String userPfp = preferences.getString("currentUserPfp", "");

        CUName.setText(userName);
        if (!userPfp.isEmpty()) {
            Glide.with(this).load(userPfp).into(MainPfp);
        } else {
            MainPfp.setImageResource(R.drawable.person_icon); // Placeholder image
        }
    }

    public void addBurnContacts() {
        users.add(new User("Henry", "68234800", "3", ""));
        users.add(new User("Michael", "67845321", "4", ""));
        users.add(new User("Lucia", "64440000", "5", ""));
        users.add(new User("Jose", "9999999999", "6", ""));
        users.add(new User("Carlos", "66666666", "7", ""));
    }




}








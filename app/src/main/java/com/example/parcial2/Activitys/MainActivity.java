package com.example.parcial2.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parcial2.Adapters.ChatListAdapter;
import com.example.parcial2.Entity.Chat;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewChats;
    FloatingActionButton addChatBtn;
    ImageButton goProfileBtn;
    ChatListAdapter chatListAdapter;
    List<Chat> chatList = new ArrayList<>();
    ImageView MainPfp;
    TextView CUName;
    String currentUserId;

    public void crearfile() {
        File file = new File(getFilesDir(), "chats.txt");
        if (!file.exists()) {
            try {
                OutputStreamWriter fout = new OutputStreamWriter(openFileOutput("chats.txt", Context.MODE_PRIVATE));
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crearfile();
        InitializeControls();

        loadUserProfile();

        FileToList();

        addChatBtn.setOnClickListener(v -> goContacts());
        goProfileBtn.setOnClickListener(v -> goProfile());
    }

    void InitializeControls() {
        MainPfp = findViewById(R.id.MainPfp);
        CUName = findViewById(R.id.TvCurrUser);
        addChatBtn = findViewById(R.id.addChatBtn);
        goProfileBtn = findViewById(R.id.PfBtn);

        recyclerViewChats = findViewById(R.id.ChatsRecyclerView);
        recyclerViewChats.setLayoutManager(new LinearLayoutManager(this));

        chatListAdapter = new ChatListAdapter(chatList, this);
        recyclerViewChats.setAdapter(chatListAdapter);
    }

    private void goContacts() {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.putExtra("currentUser", currentUserId);
        startActivity(intent);
    }

    private void goProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("currentUser", currentUserId);
        startActivity(intent);
    }

    private void loadUserProfile() {
        currentUserId = getCurrentUserId();
        User currentUser = getUserDetailsById(currentUserId);
        if (currentUser != null) {
            CUName.setText(currentUser.getName());
            Glide.with(this).load(currentUser.getPfp()).into(MainPfp);
        }
    }

    private String getCurrentUserId() {
        SharedPreferences preferences = getSharedPreferences("currentUser", MODE_PRIVATE);
        return preferences.getString("currentUserId", null);
    }

    private User getUserDetailsById(String userId) {
        SharedPreferences preferences;
        if (userId.equals("1")) {
            preferences = getSharedPreferences("user1", MODE_PRIVATE);
            return new User(
                    preferences.getString("User1Name", ""),
                    preferences.getString("User1Number", ""),
                    preferences.getString("IdUser1", ""),
                    preferences.getString("User1Pfp", "")
            );
        } else if (userId.equals("2")) {
            preferences = getSharedPreferences("user2", MODE_PRIVATE);
            return new User(
                    preferences.getString("User2Name", ""),
                    preferences.getString("User2Number", ""),
                    preferences.getString("IdUser2", ""),
                    preferences.getString("User2Pfp", "")
            );
        }
        return null;
    }

    public void FileToList() {}
}






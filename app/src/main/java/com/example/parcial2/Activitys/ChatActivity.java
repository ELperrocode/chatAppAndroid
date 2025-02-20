package com.example.parcial2.Activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parcial2.Adapters.ChatRoomAdapter;
import com.example.parcial2.Entity.Chat;
import com.example.parcial2.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
     RecyclerView recyclerViewChats;
     ChatRoomAdapter chatRecyclerAdapter;
     EditText chatInput;
     String currentUserId, receiverId, receiverName, receiverPfp;
     List<Chat> chats = new ArrayList<>();
     TextView receiverUser;
     ImageView chatPfp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle bundle = getIntent().getExtras();
        receiverId = bundle.getString("id");
        receiverName = bundle.getString("name");
        receiverPfp = bundle.getString("pfp");

        currentUserId = getCurrentUserId();

        InitializeControls();
        loadUserProfile();
        setupRecyclerView();
        FileToList();
    }

    public String getCurrentUserId() {
        SharedPreferences preferences = getSharedPreferences("currentUser", MODE_PRIVATE);
        return preferences.getString("currentUserId", null);
    }

    public void InitializeControls() {
        recyclerViewChats = findViewById(R.id.recyclerViewChats);
        chatInput = findViewById(R.id.chat_input);
        receiverUser = findViewById(R.id.RecieverUser);
        chatPfp = findViewById(R.id.chatPfp);
        findViewById(R.id.btnSend).setOnClickListener(v -> sendMessage());
        findViewById(R.id.btnBack).setOnClickListener(v -> {sendResult();
        });
    }

    private void loadUserProfile() {
        receiverUser.setText(receiverName);
        Glide.with(this).load(Uri.parse(receiverPfp)).into(chatPfp);
    }


    private void setupRecyclerView() {
        chatRecyclerAdapter = new ChatRoomAdapter(chats, this, currentUserId);
        recyclerViewChats.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChats.setAdapter(chatRecyclerAdapter);
    }

    public void FileToList() {
        try {
            File chatFile = new File(getFilesDir(), getChatFileName());
            if (!chatFile.exists()) {
                chatFile.createNewFile();
            }
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(
                            openFileInput(getChatFileName())
                    )
            );
            String rawData;
            chats.clear();
            while ((rawData = bf.readLine()) != null) {
                String[] splitData = rawData.split("~");

                for (String row : splitData) {
                    String[] fields = row.split("\\|");
                    if (fields.length == 4) {
                        String message = fields[0];
                        String senderId = fields[1];
                        String receiverId = fields[2];
                        String timestamp = fields[3];

                        if ((senderId.equals("1") && receiverId.equals(this.receiverId)) ||
                                (senderId.equals("2") && receiverId.equals(receiverId))) {
                            chats.add(new Chat(message, senderId, receiverId, timestamp, receiverPfp));
                        }
                    }
                }
            }
            bf.close();
            chatRecyclerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(this, "Error al obtener chats: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveMessageToFile(String message, String senderId, String receiverId, String timestamp) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(getChatFileName(), Context.MODE_APPEND));
            String data = message + "|" + senderId + "|" + receiverId + "|" + timestamp + "~";
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar el mensaje: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void sendMessage() {
        String message = chatInput.getText().toString().trim();
        if (!message.isEmpty()) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            saveMessageToFile(message, currentUserId, receiverId, timestamp);
            chatInput.setText("");

            chats.add(new Chat(message, currentUserId, receiverId, timestamp, receiverPfp));
            chatRecyclerAdapter.notifyDataSetChanged();
            recyclerViewChats.scrollToPosition(chatRecyclerAdapter.getItemCount() - 1);
        }
    }

    private String getChatFileName() {
        return "chat_" + receiverId + ".txt";
    }

    public void sendResult() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("lastMessage", getLastMessage());
        intent.putExtra("receiverId", receiverId);
        intent.putExtra("receiverName", receiverName);
        intent.putExtra("receiverPfp", receiverPfp);
        startActivity(intent);
    }

    public String getLastMessage() {
        if (!chats.isEmpty()) {

            return chats.get(chats.size() - 1).getMessage();

        }
        return null;
    }
    @SuppressLint("MissingSuperCall")
    public void onBackPressed() {
      sendResult();
    }
}


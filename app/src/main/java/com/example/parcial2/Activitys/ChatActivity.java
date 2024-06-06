package com.example.parcial2.Activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    List<Chat> chats = new ArrayList<>();
    String currentUserId, receiverId, receiverNumber, receiverName, receiverPfp;
    private RecyclerView recyclerViewChats;
    private ChatRoomAdapter chatRoomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle bundle = getIntent().getExtras();
        receiverId = bundle.getString("id");
        receiverName = bundle.getString("name");
        receiverNumber = bundle.getString("number");
        receiverPfp = bundle.getString("pfp");
        currentUserId = getCurrentUserId();

        InitializeControls();
        loadUserProfile();
        FileToList();
    }

    public String getCurrentUserId() {
        SharedPreferences preferences = getSharedPreferences("currentUser", MODE_PRIVATE);
        return preferences.getString("currentUser", null);
    }

    public void InitializeControls() {
        recyclerViewChats = findViewById(R.id.ChatroomRecyclerView);
        recyclerViewChats.setLayoutManager(new LinearLayoutManager(this));
        chatRoomAdapter = new ChatRoomAdapter(chats, this, currentUserId);
        recyclerViewChats.setAdapter(chatRoomAdapter);

        findViewById(R.id.btnSend).setOnClickListener(v -> sendMessage());
    }

    private void loadUserProfile() {
        TextView receiverUser = findViewById(R.id.RecieverUser);
        ImageView chatPfp = findViewById(R.id.chatPfp);

        receiverUser.setText(receiverName);
        Glide.with(this).load(receiverPfp).into(chatPfp);
    }

    public void FileToList() {
        try {
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(
                            openFileInput("chats.txt")
                    )
            );
            String rawData;
            while ((rawData = bf.readLine()) != null) {
                String[] splitData = rawData.split("~");

                for (String row : splitData) {
                    String[] fields = row.split("\\|");
                    if (fields.length == 4) {
                        String message = fields[0];
                        String senderId = fields[1];
                        String receiverId = fields[2];
                        String timestamp = fields[3];

                        // Filtrar mensajes que pertenecen a la conversación entre currentUser y receiverId
                        if ((senderId.equals(currentUserId) && receiverId.equals(this.receiverId)) ||
                                (senderId.equals(this.receiverId) && receiverId.equals(currentUserId))) {
                            chats.add(new Chat(message, senderId, receiverId, timestamp));
                        }
                    }
                }
            }
            bf.close();
            chatRoomAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(this, "Error al obtener chats: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveMessageToFile(String message, String senderId, String receiverId, String timestamp) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("chats.txt", Context.MODE_APPEND));
            String data = message + "|" + senderId + "|" + receiverId + "|" + timestamp + "~";
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar el mensaje: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void sendMessage() {
        EditText chatInput = findViewById(R.id.chat_input);
        String message = chatInput.getText().toString().trim();
        if (!message.isEmpty()) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            saveMessageToFile(message, currentUserId, receiverId, timestamp);
            chatInput.setText("");
            // Optionally, update the RecyclerView with the new message
            chats.add(new Chat(message, currentUserId, receiverId, timestamp));
            chatRoomAdapter.notifyDataSetChanged();
        }
    }
}

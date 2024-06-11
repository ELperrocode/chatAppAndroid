package com.example.parcial2.Activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.parcial2.Adapters.ChatListAdapter;
import com.example.parcial2.Entity.Chat;
import com.example.parcial2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listViewChats;
    ImageButton goProfileBtn, addChatBtn;
    ChatListAdapter chatListAdapter;
    List<Chat> chatList = new ArrayList<>();
    ImageView MainPfp;
    TextView CUName, noChatsTextView;
    String  receiverId, receiverName, receiverPfp, lastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeControls();
        loadUserProfile();
        crearFile();
        FileToList();
        loadChats();

        addChatBtn.setOnClickListener(v -> goContacts());
        goProfileBtn.setOnClickListener(v -> goProfile());

    }

    public void crearFile(){
        try {
            File file = new File(getFilesDir(), "chats.txt");
            if (!file.exists()) {
                file.createNewFile();

            } else {

            }
        } catch (IOException e) {
            Toast.makeText(this, "Error al crear el archivo: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void loadChats() {
        Intent intent = getIntent();
        receiverId = intent.getStringExtra("receiverId");
        receiverName = intent.getStringExtra("receiverName");
        receiverPfp = intent.getStringExtra("receiverPfp");
        lastMessage = intent.getStringExtra("lastMessage");

        if (receiverId != null && receiverName != null && receiverPfp != null && lastMessage != null) {

            saveChat();
        } else {
            Toast.makeText(this, "Datos incompletos recibidos", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("WrongViewCast")
    void InitializeControls() {
        MainPfp = findViewById(R.id.MainPfp);
        CUName = findViewById(R.id.TvCurrUser);
        addChatBtn = findViewById(R.id.addChatBtn);
        goProfileBtn = findViewById(R.id.PfBtn);

        listViewChats = findViewById(R.id.ChatsListView);
        noChatsTextView = findViewById(R.id.noChatsTextView);
        listViewChats.setEmptyView(noChatsTextView);

        chatListAdapter = new ChatListAdapter(this, chatList);
        listViewChats.setAdapter(chatListAdapter);
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
            MainPfp.setImageResource(R.drawable.person_icon);
        }
    }

    private void saveChat() {
        try {
            boolean chatExists = false;
            for (Chat chat : chatList) {
                if (chat.getReceiverID().equals(receiverId)) {
                    chatExists = true;
                    chat.setMessage(lastMessage);
                    chat.setReceiverPfp(receiverPfp);
                    break;
                }
            }
            if (!chatExists) {
                chatList.add(new Chat(receiverId, receiverName, lastMessage, receiverPfp));
            }
            updateChatFile();

        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar el chat: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void updateChatFile() {
        try {
            StringBuilder datos = new StringBuilder();
            for (Chat chat : chatList) {
                datos.append(chat.getReceiverID()).append("|");
                datos.append(chat.getReceiverName()).append("|");
                datos.append(chat.getMessage()).append("|");
                datos.append(chat.getReceiverPfp()).append("~");
            }
            OutputStreamWriter fout = new OutputStreamWriter(openFileOutput("chats.txt", Context.MODE_PRIVATE));
            fout.write(datos.toString());
            fout.close();
            chatListAdapter.refreshList();
        } catch (Exception e) {
            Toast.makeText(this, "Error al actualizar el archivo: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void FileToList() {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput("chats.txt")));
            String rawData = bf.readLine();
            bf.close();

            if (rawData != null && !rawData.isEmpty()) {
                String[] splitData = rawData.split("~");

                for (String row : splitData) {
                    String[] fields = row.split("\\|");
                    if (fields.length == 4) {
                        chatList.add(new Chat(
                                fields[0],
                                fields[1],
                                fields[2],
                                fields[3]
                        ));
                    } else {
                        Toast.makeText(this, "Formato de datos incorrecto en el archivo", Toast.LENGTH_LONG).show();
                    }
                }
                chatListAdapter.notifyDataSetChanged();
            } else {

            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Archivo no encontrado: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error al leer el archivo: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}









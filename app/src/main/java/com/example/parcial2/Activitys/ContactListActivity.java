package com.example.parcial2.Activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parcial2.Adapters.ContactListAdapter;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    List<User> users = new ArrayList<>();
    ImageButton addContactBtn,BtnReturn;
     ListView listView;
     ContactListAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);
        FileToList();
        InitializeControls();


        addContactBtn.setOnClickListener(v -> addContact());
        BtnReturn.setOnClickListener(v -> {
            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void InitializeControls() {
        listView = findViewById(R.id.ContactsListView);
        adapter = new ContactListAdapter(users, this);
        listView.setAdapter(adapter);
        addContactBtn = findViewById(R.id.addContactButton);
        BtnReturn = findViewById(R.id.btnReturn);

    }

    public void FileToList() {
        BufferedReader bf = null;
        try {
            FileInputStream fileInputStream = openFileInput("contacts.txt");
            bf = new BufferedReader(new InputStreamReader(fileInputStream));
            String rawData = bf.readLine();

            if (rawData != null && !rawData.isEmpty()) {
                String[] splitData = rawData.split("~");

                for (String row : splitData) {
                    String[] fields = row.split("\\|");
                    if (fields.length == 4) {
                        users.add(new User(fields[0], fields[1], fields[2], fields[3]));
                    }
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            Toast.makeText(this, "Error al leer el archivo: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    Toast.makeText(this, "Error al cerrar el archivo: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void addContact() {
        Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
        startActivity(intent);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

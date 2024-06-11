package com.example.parcial2.Activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    FloatingActionButton addContactBtn;
    private ListView listView;
    private ContactListAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);
        FileToList();
        InitializeControls();

        addContactBtn = findViewById(R.id.addContactButton);
        addContactBtn.setOnClickListener(v -> addContact());
    }

    private void InitializeControls() {
        listView = findViewById(R.id.ContactsListView);
        adapter = new ContactListAdapter(users, this);
        listView.setAdapter(adapter);
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

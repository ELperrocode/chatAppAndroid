package com.example.parcial2.Activitys;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parcial2.Adapters.ContactListAdapter;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    List<User> users = new ArrayList<>();
    private ListView listView;
    private ContactListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);

        addBurnContacts();
        InitializeControls();
    }

    private void InitializeControls() {
        listView = findViewById(R.id.ContactsListView);
        adapter = new ContactListAdapter(users, this);
        listView.setAdapter(adapter);
    }

    public void addBurnContacts() {
        users.add(new User("Henry", "68234800", "3", "@drawable/person_icon"));
        users.add(new User("Michael", "67845321", "4", "@drawable/person_icon"));
        users.add(new User("Lucia", "64440000", "5", "@drawable/person_icon"));
        users.add(new User("Jose", "9999999999", "6", "@drawable/person_icon"));
        users.add(new User("Carlos", "66666666", "7", "@drawable/person_icon"));
    }
}



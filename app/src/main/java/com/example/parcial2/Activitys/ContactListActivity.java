package com.example.parcial2.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial2.Adapters.ContactListAdapter;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    List<User> users = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);

        InitializeControls();
        addBurnContacts();
    }//end onCreate

    private void InitializeControls() {

        recyclerView = findViewById(R.id.ContactsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new androidx.recyclerview.widget.LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ContactListAdapter(users,ContactListActivity.this);
        recyclerView.setAdapter(adapter);

    }
    public void addBurnContacts(){
        users.add(new User("Henry","68234800","3","https://source.boomplaymusic.com/group10/M00/07/20/2649763ce38a4613b248aeed797d07d6_320_320.jpg"));
        users.add(new User("Michael","67845321","4","https://imgb.ifunny.co/images/4fcef0d82a2c32a385ca2239639267c01bdec5f066f179e95d24c3675a1d1830_1.jpg"));
        users.add(new User("Lucia","64440000","5","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSQYPEhcOpa5vPudMNFdN6IeTY2D3xexyNaQ&s"));
        users.add(new User("Jose","9999999999","6","https://imgb.ifunny.co/images/d32825c0d6058b2ee8a61879c5c1c305bf9ed43c3a38dc34d9862673b0f19469_1.jpg"));
        users.add(new User("Carlos","66666666","7","https://cdn.pfps.gg/pfps/5124-silly-cat-pfp.png"));
    }

}
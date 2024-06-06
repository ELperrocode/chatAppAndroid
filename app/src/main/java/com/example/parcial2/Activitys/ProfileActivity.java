package com.example.parcial2.Activitys;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresExtension;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class ProfileActivity extends AppCompatActivity {

    EditText PfName;
    TextView PfPhone, PfId;
    ImageView Pfp;
    Button btnchangeUser, btnSaveChanges;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchUser;
    String currentUserId= "1";
    ActivityResultLauncher<Intent> resultLauncher;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ShareUser1();
        ShareUser2();
        ShareCurrentUser();

        InitializeControls();
        currentUserId = getIntent().getStringExtra("id");

        loadProfile();
        btnchangeUser.setOnClickListener(v -> changeUser());
        btnSaveChanges.setOnClickListener(v -> saveChanges());

    }

    private void loadProfile() {
        currentUser = getUserDetailsById(currentUserId);
        PfName.setText(currentUser.getName());
        PfPhone.setText(currentUser.getNumber());
        PfId.setText(currentUser.getId());
        Glide.with(this).load(currentUser.getPfp()).into(Pfp);
    }

    private void InitializeControls() {
        PfName = findViewById(R.id.EtContactName);
        PfPhone = findViewById(R.id.TvContactPhone);
        Pfp = findViewById(R.id.ImvPfp);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        switchUser = findViewById(R.id.swChangeUser);
    }

    private void saveChanges() {
        String newName = PfName.getText().toString();
        if (currentUserId.equals("1")) {
            SharedPreferences preferences = getSharedPreferences("user1", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("User1Name", newName); // Guarda el nuevo nombre del usuario 1
            editor.apply();
        } else if (currentUserId.equals("2")) {
            SharedPreferences preferences = getSharedPreferences("user2", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("User2Name", newName); // Guarda el nuevo nombre del usuario 2
            editor.apply();
        }
        Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
    }

    private void changeUser() {
        if (switchUser.isChecked()) {
            currentUserId = "2";
        } else {
            currentUserId = "1";
        }
        loadProfile();
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
    public void ShareCurrentUser(){
        SharedPreferences preferences= getSharedPreferences("currentUser", MODE_PRIVATE);
        User currentUser = getUserDetailsById(currentUserId);
        String id = currentUser.getId();
        String name = currentUser.getName();
        String number = currentUser.getNumber();
        String Pfp = currentUser.getPfp();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Id", id)
                .putString("Name", name)
                .putString("Number", number)
                .putString("Pfp", Pfp)
        ;
        editor.apply();
    }
    public void ShareUser1(){
        SharedPreferences preferences= getSharedPreferences("user1", MODE_PRIVATE);

        String idUser1="1";
        String nameUser1=PfName.getText().toString();
        String numberUser1="123456789";
        String PfpUser1="https://preview.redd.it/which-meme-image-of-joker-is-going-to-be-turned-into-a-v0-qgt2ljdpsbzc1.jpg?width=640&crop=smart&auto=webp&s=58b0fbeed2d91a608cf2507d5575f7dd8ea65e19";

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("IdUser1", idUser1)
                .putString("User1Name", nameUser1)
                .putString("User1Number", numberUser1)
                .putString("User1Pfp", PfpUser1)
        ;
        editor.apply();
    }
    public void ShareUser2(){
        SharedPreferences preferences= getSharedPreferences("user1", MODE_PRIVATE);

        String idUser2="2";
        String nameUser2="El yeyo";
        String numberUser2="123456789";
        String PfpUser2="https://onerpm.com/wp-content/uploads/sites/4/2023/06/289827518_445418097396776_5531551399036382896_n.jpg";

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("IdUser2", idUser2)
                .putString("User2Name", nameUser2)
                .putString("User2Number", numberUser2)
                .putString("User2Pfp", PfpUser2)
        ;
        editor.apply();
    }


}

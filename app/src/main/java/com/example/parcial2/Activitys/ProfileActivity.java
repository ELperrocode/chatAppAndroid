package com.example.parcial2.Activitys;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST = 1;
     Uri imageUri;
    EditText PfName;
    TextView PfPhone;
    ImageView Pfp;
    Button btnSaveChanges,btnPfp;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchUser;
    User currentUser, user1, user2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        InitializeControls();
        addBurnContacts();
        loadCurrentUserFromPreferences();
        loadProfile();

        switchUser.setOnCheckedChangeListener((buttonView, isChecked) -> {
            changeUser();
            loadProfile();
        });

        btnPfp.setOnClickListener(v -> openImageSelector());

        btnSaveChanges.setOnClickListener(v -> saveChanges());
    }

    private void loadProfile() {
        if (currentUser == null) {
            currentUser = user1; // Asignar user1 como  predeterminado si currentUser es nulo
        }
        PfName.setText(currentUser.getName());
        PfPhone.setText(currentUser.getNumber());
        Glide.with(this).load(currentUser.getPfp()).into(Pfp);

        // Guardar las preferencias del usuario actual
        saveCurrentUserToPreferences();
    }

    private void InitializeControls() {
        PfName = findViewById(R.id.EtContactName);
        PfPhone = findViewById(R.id.TvContactPhone);
        Pfp = findViewById(R.id.ImvPfp);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        switchUser = findViewById(R.id.swChangeUser);
        btnPfp = findViewById(R.id.btnpfp);
    }

    private void saveChanges() {
        String newName = PfName.getText().toString();
        currentUser.setName(newName);
        saveCurrentUserToPreferences();
        loadProfile();
    }

    private void changeUser() {
        if (switchUser.isChecked()) {
            currentUser = user2;
        } else {
            currentUser = user1;
        }
    }

    private void loadCurrentUserFromPreferences() {
        SharedPreferences preferences = getSharedPreferences("currentUser", MODE_PRIVATE);
        String currentUserId = preferences.getString("currentUserId", "1");
        String currentUserName = preferences.getString("currentUserName", "");
        String currentUserNumber = preferences.getString("currentUserNumber", "");
        String currentUserPfp = preferences.getString("currentUserPfp", "person_icon");

        if (currentUserId.equals("2")) {
            currentUser = user2;
            switchUser.setChecked(true);
        } else {
            currentUser = user1;
            switchUser.setChecked(false);
        }

        currentUser.setName(currentUserName);
        currentUser.setNumber(currentUserNumber);
        currentUser.setPfp(currentUserPfp);
    }

    private void saveCurrentUserToPreferences() {
        SharedPreferences preferences = getSharedPreferences("currentUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("currentUserId", currentUser.getId());
        editor.putString("currentUserName", currentUser.getName());
        editor.putString("currentUserNumber", currentUser.getNumber());
        editor.putString("currentUserPfp", currentUser.getPfp());
        editor.apply();
    }

    public void addBurnContacts() {
        user1 = new User("Patacon", "68234800", "1", "https://media.istockphoto.com/id/183358870/es/foto/tostones.jpg?s=612x612&w=0&k=20&c=yyh2_ZDw4ojJ-5SUsr4epF3LH0qQvyF7a15z_8IAgF0=");
        user2 = new User("yo", "67845321", "2", "https://cdn.pfps.gg/pfps/5124-silly-cat-pfp.png");
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }




        private void openImageSelector() {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!= null && data.getData()!= null) {
                imageUri = data.getData();
                currentUser.setPfp(imageUri.toString());
            }
        }


}





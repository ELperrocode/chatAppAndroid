package com.example.parcial2.Activitys;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;

public class ProfileActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST = 1;
     Uri imageUri;
    EditText PfName;
    TextView PfPhone;
    ImageView Pfp;
    Button btnSaveChanges;
    ImageButton btnPfp, BtnReturn;
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
        BtnReturn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadProfile() {
        if (currentUser == null) {
            currentUser = user1;
        }
        PfName.setText(currentUser.getName());
        PfPhone.setText(currentUser.getNumber());
        Glide.with(this).load(currentUser.getPfp()).into(Pfp);

        saveCurrentUserToPreferences();
    }

    private void InitializeControls() {
        PfName = findViewById(R.id.EtContactName);
        PfPhone = findViewById(R.id.TvContactPhone);
        Pfp = findViewById(R.id.ImvPfp);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        switchUser = findViewById(R.id.swChangeUser);
        btnPfp = findViewById(R.id.btnpfp);
        BtnReturn = findViewById(R.id.btnReturn);
    }

    private void saveChanges() {
        String newName = PfName.getText().toString();
        currentUser.setName(newName);
        if (currentUser.getId().equals("1")) {
            user1.setName(newName);
            user1.setPfp(currentUser.getPfp());
            saveUserToPreferences(user1, "user1");
        } else {
            user2.setName(newName);
            user2.setPfp(currentUser.getPfp());
            saveUserToPreferences(user2, "user2");
        }

        saveCurrentUserToPreferences();
        loadProfile();
        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
    }
    private void saveUserToPreferences(User user, String key) {
        SharedPreferences preferences = getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("name", user.getName());
        editor.putString("number", user.getNumber());
        editor.putString("id", user.getId());
        editor.putString("pfp", user.getPfp());
        editor.apply();
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
        user1 = loadUserFromPreferences("user1");
        if (user1 == null) {
            user1 = new User("Patacon", "68234800", "1", "https://media.istockphoto.com/id/183358870/es/foto/tostones.jpg?s=612x612&w=0&k=20&c=yyh2_ZDw4ojJ-5SUsr4epF3LH0qQvyF7a15z_8IAgF0=");
        }

        user2 = loadUserFromPreferences("user2");
        if (user2 == null) {
            user2 = new User("yo", "67845321", "2", "https://cdn.pfps.gg/pfps/5124-silly-cat-pfp.png");
        }
    }

    private User loadUserFromPreferences(String key) {
        SharedPreferences preferences = getSharedPreferences(key, MODE_PRIVATE);

        String name = preferences.getString("name", null);
        String number = preferences.getString("number", null);
        String id = preferences.getString("id", null);
        String pfp = preferences.getString("pfp", null);

        if (name != null && number != null && id != null && pfp != null) {
            return new User(name, number, id, pfp);
        }

        return null;
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
                Glide.with(this)
                        .load(imageUri)
                        .into(Pfp);
            }
        }
}





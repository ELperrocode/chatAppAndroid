package com.example.parcial2.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parcial2.Entity.User;
import com.example.parcial2.R;

public class ProfileActivity extends AppCompatActivity {

    EditText PfName;
    TextView PfPhone;
    ImageView Pfp;
    Button btnSaveChanges;
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

        btnSaveChanges.setOnClickListener(v -> saveChanges());
    }

    private void loadProfile() {
        if (currentUser == null) {
            currentUser = user1; // Asignar user1 como  predeterminado si currentUser es nulo
        }
        PfName.setText(currentUser.getName());
        PfPhone.setText(currentUser.getNumber());
        int resId = getResources().getIdentifier(currentUser.getPfp(), "drawable", getPackageName());
        Pfp.setImageResource(resId);

        // Guardar las preferencias del usuario actual
        saveCurrentUserToPreferences();
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
        currentUser.setName(newName);
        saveCurrentUserToPreferences();
        loadProfile();
        Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
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
        user1 = new User("El yeyo", "68234800", "1", "person_icon");
        user2 = new User("Jhon Doe", "67845321", "2", "person_icon");
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }
}





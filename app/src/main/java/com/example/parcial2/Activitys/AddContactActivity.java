package com.example.parcial2.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.parcial2.Entity.User;
import com.example.parcial2.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    static final int PICK_IMAGE_REQUEST = 1;

     EditText etName, etPhoneNumber;
     ImageView ivProfileImage;
     Button  btnSaveContact;
    ImageButton btnSelectImage, BtnReturn;
     Uri imageUri;

     List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        InitializeControls();
        FileToList();

        btnSelectImage.setOnClickListener(v -> openImageSelector());
        BtnReturn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactListActivity.class);
            startActivity(intent);
            finish();
        });
        btnSaveContact.setOnClickListener(v -> {
            try {
                saveContact();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void InitializeControls() {
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        BtnReturn = findViewById(R.id.btnReturn);
        btnSaveContact = findViewById(R.id.btnSaveContact);
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

            Glide.with(this)
                    .load(imageUri)
                    .into(ivProfileImage);
        }
    }


    private void saveContact() throws IOException {
        int idNuevo = users.size() + 3;
        String name = etName.getText().toString().trim();
        String phoneNumber = "+507 " + etPhoneNumber.getText().toString().trim();

        if (name.isEmpty() || etPhoneNumber.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageUri == null) {
            imageUri = Uri.parse("https://i.pinimg.com/474x/25/1c/e1/251ce139d8c07cbcc9daeca832851719.jpg");
        }

        users.add(new User(name, phoneNumber, String.valueOf(idNuevo), imageUri.toString()));

        try (FileWriter fw = new FileWriter(getFilesDir() + "/contacts.txt", true)) {
            fw.write(name + "|" + phoneNumber + "|" + idNuevo + "|" + imageUri.toString() + "~");
            Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContactListActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error al actualizar el archivo: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
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

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivity(intent);
        finish();
    }
}

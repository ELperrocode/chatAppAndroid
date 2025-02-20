package com.example.parcial2.Entity;

import android.net.Uri;

public class User {
    String name;
    String number;
    String id;
    String pfp;


    public User(String name, String number, String id, String pfp) {
        this.name = name;
        this.number = number;
        this.id = id;
        this.pfp = pfp;
    }

    public User(String name, String phoneNumber, String id, Uri imageUri) {
        this.name = name;
        this.number = phoneNumber;
        this.id = id;
        this.pfp = imageUri.toString();
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

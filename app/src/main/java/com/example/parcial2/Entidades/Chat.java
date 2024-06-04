package com.example.parcial2.Entidades;

import java.time.Instant;

public class Chat {
    String message;
    String idUsers;
    Instant timestamp;

    public Chat(String message, String sender, Instant timestamp) {
        this.message = message;
        this.idUsers = sender;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(String idUsers) {
        this.idUsers = idUsers;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}

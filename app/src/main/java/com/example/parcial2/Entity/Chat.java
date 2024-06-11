package com.example.parcial2.Entity;

import java.time.Instant;

public class Chat {
    private String message;
    private String senderID;
    private String receiverID;
    private Instant timestamp;
    private String receiverName;
    private String receiverPfp;

    public Chat(String receiverID, String receiverName, String message, String receiverPfp) {
        this.receiverID = receiverID;
        this.receiverName = receiverName;
        this.message = message;
        this.receiverPfp = receiverPfp;
    }


    public Chat(String lastMessage, String currentUserId, String receiverId, String receiverName, String receiverPfp) {
        this.message = lastMessage;
        this.senderID = currentUserId;
        this.receiverID = receiverId;
        this.receiverName = receiverName;
        this.receiverPfp = receiverPfp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public Instant getTimestamp() {
        return timestamp;
    }


    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPfp() {
        return receiverPfp;
    }

    public void setReceiverPfp(String receiverPfp) {
        this.receiverPfp = receiverPfp;
    }
}


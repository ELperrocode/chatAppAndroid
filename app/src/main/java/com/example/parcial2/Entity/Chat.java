package com.example.parcial2.Entity;

public class Chat {
    private String message;
    private String senderID;
    private String receiverID;
    private String timestamp;
    private String receiverName;
    private String receiverPfp;

    public Chat(String message, String senderID, String receiverID, String timestamp, String receiverName, String receiverPfp) {
        this.message = message;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.timestamp = timestamp;
        this.receiverName = receiverName;
        this.receiverPfp = receiverPfp;
    }

    public Chat(String message, String senderID, String receiverID, String timestamp) {
        this.message = message;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.timestamp = timestamp;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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


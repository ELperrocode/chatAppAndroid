package com.example.parcial2.Entity;

import java.time.Instant;

public class Chat {
    String message;
    String SenderID;
    String ReceiverID;
    String timestamp;

    public Chat(String message, String sender, String receiver, String timestamp) {
        this.message = message;
        this.SenderID = sender;
        this.ReceiverID = receiver;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderID() {
        return SenderID;
    }

    public void setSenderID(String senderID) {
        this.SenderID = senderID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = String.valueOf(timestamp);
    }

    public String getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(String receiverID) {
        ReceiverID = receiverID;
    }
}

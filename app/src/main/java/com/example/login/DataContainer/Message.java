package com.example.login.DataContainer;

public class Message {
    private final String sender;
    private final String receiver;
    private final boolean isRead;
    private final String content;


    public Message(String sender, String receiver, boolean isRead, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.isRead = isRead;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getContent() {
        return content;
    }
}

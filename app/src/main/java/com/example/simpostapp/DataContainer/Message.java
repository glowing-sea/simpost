package com.example.simpostapp.DataContainer;

import java.util.ArrayList;

public class Message {
    private final String sender;
    private final String receiver;
    private final String date;
    private boolean isRead;
    private final String content;


    public Message(String sender, String receiver, String date, boolean isRead, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.isRead = isRead;
        this.content = content;
    }

    public Message(String encode) {
        String[] result = encode.split("`");
        this.sender = result[0];
        this.receiver = result[1];
        this.date = result[2];
        this.content = result[3];
        this.isRead = result[4].equals("1");
    }

    @Override
    public String toString(){
        char isReadChar;
        if (isRead){
            isReadChar = '1';
        } else {
            isReadChar = '0';
        }
        return sender + '`' + receiver + '`' + date + '`' + content + '`' + isReadChar;
    }


    // Encode a list of message
    public static String messagesEncode (ArrayList<Message> messages){
        StringBuilder stringBuilder= new StringBuilder();
        for (Message message : messages){
            stringBuilder.append(message);
            stringBuilder.append('~');
        }
        return stringBuilder.toString();
    }
    // Decode a list of comments
    public static ArrayList<Message> messagesDecode (String string){
        ArrayList<Message> messagesList = new ArrayList<>();
        if (string.isEmpty())
            return messagesList;
        String[] messageStringsArray = string.split("~");
        for (String messageString : messageStringsArray){
            Message message = new Message(messageString);
            messagesList.add(message);
        }
        return messagesList;
    }


    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getDate() {
        return date;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getContent() {
        return content;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}

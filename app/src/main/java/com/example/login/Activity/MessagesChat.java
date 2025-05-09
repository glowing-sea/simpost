package com.example.login.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Message;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

import java.util.ArrayList;

public class MessagesChat extends AppCompatActivity {

    RecyclerView recyclerView;
    UserDAO db;
    ArrayList<Message> allMessages;
    ArrayList<Message> someMessages;
    MessagesChatAdapter messagesChatAdapter;
    Me me = Me.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //old
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_chat);

        recyclerView = findViewById(R.id.message_chat_recycle_view);

        db = new UserDAOImpl(getApplicationContext());
        messages = new ArrayList<>();

        Bundle fromCreate = getIntent().getExtras();
        if (fromCreate != null){
            String name = (String) getIntent().getExtras().getSerializable("NAME");
            getIntent().removeExtra("NAME");
            databaseToMessageChatBox(name);
        }

        messagesChatAdapter = new MessagesChatAdapter(MessagesChat.this, this,messages);
        recyclerView.setAdapter(messagesChatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));*/

        //new
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_chat);

        recyclerView = findViewById(R.id.message_chat_recycle_view);

        db = new UserDAOImpl(getApplicationContext());
        allMessages = db.getMessages(me.username);
        someMessages = new ArrayList<>();

        String name = (String) getIntent().getExtras().getSerializable("NAME");
        setTitle(name);


        Bundle fromCreate = getIntent().getExtras();
        if (fromCreate != null){
            getIntent().removeExtra("NAME");

            // I sent to myself
            if (name.equals(me.username)){
                for (Message m : allMessages) {
                    if (m.getReceiver().equals(me.username) && m.getSender().equals(me.username))
                        someMessages.add(m);
                }
            } else {
                // I sent to someone
                for (Message m: allMessages){
                    if (m.getSender().equals(name) || m.getReceiver().equals(name))
                        someMessages.add(m);
                }
            }

//            for (Message m: allMessages) {
//                if (m.getReceiver().equals(name)) {
//                    someMessages.add(m);
//                    // ChatBox chatBox1 = new ChatBox(m.getSender() + " " + m.getDate(),m.getContent());
//                    // messages.add(chatBox1);
//                } else if (m.getSender().equals(name)) {
//                    someMessages.add(m);
//                    // ChatBox chatBox2 = new ChatBox(m.getSender() + " " + m.getDate(), m.getContent());
//                    // messages.add(chatBox2);
//                }
//            }
        }

        messagesChatAdapter = new MessagesChatAdapter(MessagesChat.this, this,someMessages);
        recyclerView.setAdapter(messagesChatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            recreate();
        }
    }

    //old
    /*void databaseToMessageChatBox(String targetName){
        Cursor cursor = db.getCursor(new String[]{"username, messages"}, "user");
        if(cursor.getCount() == 0){
            Toast.makeText(this, this.getText(R.string.no_data), Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                String username = cursor.getString(0);
                String message = cursor.getString(1);
                if(message != null)
                    if(username.equals(me.username)){
                        int length = message.length();
                        for (int i = 0; i < length; i++) {
                            if (message.charAt(i) == '[') {
                                for (int j = i + 1; j < length; j++) {
                                    if (message.charAt(j) == '|') {
                                        for (int k = j + 1; k < length; k++) {
                                            if (message.charAt(k) == '|') {
                                                if(message.substring(j + 1, k).equals(targetName)) {
                                                    String str1 = "";
                                                    String str2 = "";
                                                    for (int p = i + 1; p < j; p++) {
                                                        if (message.charAt(p) == '@') {
                                                            str1 = str1 + message.substring(p + 1, j) + "@" + message.substring(i + 1, p);
                                                            break;
                                                        }
                                                    }
                                                    for (int q = k + 1; q < length; q++) {
                                                        if (message.charAt(q) == ']') {
                                                            str2 = message.substring(k + 1, q);
                                                            break;
                                                        }
                                                    }
                                                    ChatBox chatBox1 = new ChatBox(str1,str2);
                                                    messages.add(chatBox1);
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (username.equals(targetName)){

                    }
            }
        }
    }*/


}
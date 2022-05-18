package com.example.simpostapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.simpostapp.DataContainer.Me;
import com.example.simpostapp.DataContainer.Message;
import com.example.simpostapp.Database.UserDAO;
import com.example.simpostapp.Database.UserDAOImpl;
import com.example.simpostapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

public class Messages extends AppCompatActivity {
    RecyclerView recyclerView;
    UserDAO db;
    HashSet<String> contacts;
    MessageAdapter messageAdapter;
    FloatingActionButton addNewMessageButton;
    Me me = Me.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //old
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        recyclerView = findViewById(R.id.message_list);
        addNewMessageButton = findViewById(R.id.addNewMessageButton);

        db = new UserDAOImpl(getApplicationContext());
        users = new ArrayList<>();
        databaseToUsersArrays();

        messageAdapter = new MessageAdapter(Messages.this, this, users);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));

        addNewMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessagesAddPage.class);
                startActivity(intent);
            }
        });*/


        //new
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        setTitle("Contacts");

        recyclerView = findViewById(R.id.message_list);
        addNewMessageButton = findViewById(R.id.addNewMessageButton);

        db = new UserDAOImpl(getApplicationContext());
        ArrayList<Message> messages = db.getMessages(me.username);
        contacts = new HashSet<>();

        boolean containSelfMessage = false;

        for (Message m : messages){
            String sender = m.getSender();
            String receiver = m.getReceiver();
            contacts.add(receiver);
            contacts.add(sender);

            // I sent to myself
            if (sender.equals(receiver))
                containSelfMessage = true;
        }
        // If there is no self messages, me should not be in the contact
        if (!containSelfMessage)
            contacts.remove(me.username);

        ArrayList<String> contactFinal = new ArrayList<>(contacts);


        messageAdapter = new MessageAdapter(Messages.this, this, contactFinal);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));

        addNewMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessagesAddPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.message_manu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_all_messages){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Are you sure to delete all messages?");
            alertDialog.setCancelable(true);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), GeneralDeleting.class);
                    intent.putExtra("DELETE", "AllMessages");
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    finish();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alertDialog.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    //old
    /*void databaseToUsersArrays(){
        Cursor cursor = db.getCursor(new String[]{"username, messages"}, "user");
        if(cursor.getCount() == 0){
            Toast.makeText(this, this.getText(R.string.no_data), Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                String username = cursor.getString(0);
                String messages = cursor.getString(1);
                if (messages != null) {
                    if (username.equals(me.username)) {
                        int length = messages.length();
                        for (int i = 0; i < length; i++) {
                            if (messages.charAt(i) == '[') {
                                for (int j = i + 1; j < length; j++) {
                                    if (messages.charAt(j) == '|') {
                                        for (int k = j + 1; k < length; k++) {
                                            if (messages.charAt(k) == '|') {
                                                users.add(messages.substring(j + 1, k));
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        int length = messages.length();
                        for (int i = 0; i < length; i++) {
                            if (messages.charAt(i) == '[') {
                                for (int j = i + 1; j < length; j++) {
                                    if (messages.charAt(j) == '|') {
                                        for (int k = j + 1; k < length; k++) {
                                            if (messages.charAt(k) == '|') {
                                                if(me.username.equals(messages.substring(j + 1, k))) {
                                                    for(int p = i+1; p < j; p++)
                                                        if(messages.charAt(p) == '@')
                                                            users.add(messages.substring(p + 1, j));
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/


}
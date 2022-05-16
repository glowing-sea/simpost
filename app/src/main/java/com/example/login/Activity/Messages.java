package com.example.login.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.login.DataContainer.Me;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Messages extends AppCompatActivity {
    RecyclerView recyclerView;
    UserDAO db;
    ArrayList<String> users;
    MessageAdapter messageAdapter;
    FloatingActionButton addNewMessageButton;
    Me me = Me.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            recreate();
        }
    }


    void databaseToUsersArrays(){
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
    }
}
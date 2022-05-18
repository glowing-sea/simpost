package com.example.simpostapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simpostapp.DataContainer.Me;
import com.example.simpostapp.DataContainer.Message;
import com.example.simpostapp.Database.UserDAO;
import com.example.simpostapp.Database.UserDAOImpl;
import com.example.simpostapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessagesAddPage extends AppCompatActivity {

    EditText userMessage,contentMessageSentTextView;
    Button messageSentButton;
    UserDAO db;
    Me me = Me.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_add_page);

        userMessage = findViewById(R.id.userMessageSent);
        contentMessageSentTextView = findViewById(R.id.contentMessageSentTextView);
        messageSentButton = findViewById(R.id.messageSentButton);

        String from = getIntent().getStringExtra("Receiver");
        if (from != null){
            userMessage.setText(from);
        }
        getIntent().removeExtra("USER");

        //old
        /*messageSentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = contentMessageSentTextView.getText().toString();
                //check message not contains '|' or '[' or ']'
                if(checkMessageValid(message)) {
                    db = new UserDAOImpl(getApplicationContext());
                    String name = userMessage.getText().toString();
                    if (isUserNameExisted(name)) {
                        String mess = contentMessageSentTextView.getText().toString();
                        Date curDate =  new Date(System.currentTimeMillis());
                        //获取当前时间
                        String time =  formatter.format(curDate);
                        String newMess = insertMessages(time,name,mess);
                        db.setMessage(me.username, newMess);
                        Toast.makeText(getApplicationContext(), "Message Sent!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Username not existed!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Message can not contain '|' or '[' or ']'", Toast.LENGTH_LONG).show();
                }
            }
        });*/


            /*
    return 0 success
    return -1 sender not found
    return -2 receiver not found
    return -3 store message fail
    return -4 receiver has blocked sender
    return -5 invalid characters used in the content
     */

        messageSentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = contentMessageSentTextView.getText().toString();
                db = new UserDAOImpl(getApplicationContext());
                String name = userMessage.getText().toString();

                Date curDate =  new Date(System.currentTimeMillis());
                //Get Current Time
                String time =  formatter.format(curDate);
                Message mess = new Message(me.username, name, time, false, message);
                int result = db.sendMessages(mess);

                switch (result){
                    case 0: Toast.makeText(getApplicationContext(), "Message Sent!", Toast.LENGTH_SHORT).show(); break;
                    case -1: Toast.makeText(getApplicationContext(), "Sender Not Found!", Toast.LENGTH_SHORT).show(); break;
                    case -2: Toast.makeText(getApplicationContext(), "Receiver Not Found!", Toast.LENGTH_SHORT).show(); break;
                    case -3: Toast.makeText(getApplicationContext(), "Message Store Failed!", Toast.LENGTH_SHORT).show(); break;
                    case -4: Toast.makeText(getApplicationContext(), "You are blocked by " + name + "!", Toast.LENGTH_SHORT).show(); break;
                    case -5: Toast.makeText(getApplicationContext(), "Message can not contain '~' or '`'", Toast.LENGTH_SHORT).show(); break;
                }
            }
        });

    }

    boolean isUserNameExisted(String name){
        Cursor cursor = db.getCursor(new String[]{"username"}, "user");
        if(cursor.getCount() == 0){
            Toast.makeText(this, this.getText(R.string.no_data), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            while (cursor.moveToNext()){
                String username = cursor.getString(0);
                if (username.equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }

    //old
    /*public String insertMessages(String time,String name,String mess) {
        Cursor cursor = db.getCursor(new String[]{"username,messages"}, "user");
        if(cursor.getCount() == 0){
            Toast.makeText(this, this.getText(R.string.no_data), Toast.LENGTH_SHORT).show();
            return null;
        } else {
            while (cursor.moveToNext()){
                String username = cursor.getString(0);
                if (username.equals(name)) {
                    String messages = cursor.getString(1);
                    messages = messages + "[" + time + "@" + me.username + "|" + name + "|" + mess + "]";
                    return messages;
                }
            }
        }
        return null;
    }*/


    //old
    /*boolean checkMessageValid(String message) {
        if (message == null)
            return false;
        if (message.length() > 2000)
            return false;
        for (int i = 0; i < message.length(); i++){
            char c = message.charAt(i);
            if (c == '|' || c == '[' || c == ']' || c == '@')
                return false;
        }
        return true;
    }*/

    //new
    boolean checkMessageValid(String message) {
        if (message == null)
            return false;
        if (message.length() > 2000)
            return false;
        for (int i = 0; i < message.length(); i++){
            char c = message.charAt(i);
            if (c == '~' || c == '`')
                return false;
        }
        return true;
    }
}
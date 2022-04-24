package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatePost extends AppCompatActivity {
    Button posting;
    EditText userInput;
    Intent commingIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //basic set up
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        //getIntent
        commingIn = this.getIntent();

        //Posting
        userInput = (EditText) findViewById(R.id.multxt_CreatePost_userInput);
        posting = (Button) findViewById(R.id.btn_CreatePost_posting);
        posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                String postContent = userInput.getText().toString();
                intent.putExtra("contentOfPost",postContent);
                startActivity(intent);

            }
        });
    }
}
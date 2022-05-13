package com.example.login.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.DataContainer.User;
import com.example.login.FileIO.FileRW;
import com.example.login.DataContainer.Post;
import com.example.login.R;

import java.io.File;

public class CreatePost extends AppCompatActivity {
    private static final String TAG = "Activity_CreatPost";
    private String PRIVATE_DIR;
    Button posting;
    EditText userInput, title;
    User current;
    Intent commingIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //basic set up

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        //getIntent
        commingIn = this.getIntent();

        //Posting
        title = findViewById(R.id.postTitle);
        userInput = (EditText) findViewById(R.id.multxt_CreatePost_userInput);
        posting = (Button) findViewById(R.id.btn_CreatePost_posting);
        posting.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewPost.class);
                String head = title.getText().toString();
                String postContent = userInput.getText().toString();
                //store the post as json file in directory
                //creating file
                Post current = new Post(head, postContent);
                intent.putExtra("POST", current);
                current.savePost(getApplicationContext());
                // and then we can store the post in sqlite here
                // erase content in create post
                title.setText("");
                userInput.setText("");
                startActivity(intent);
            }
        });
    }
}
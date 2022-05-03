package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CreatePost extends AppCompatActivity {
    private static final String TAG = "Activity_CreatPost";
    Button posting;
    EditText userInput, title;
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
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewPost.class);
                String head = title.getText().toString();
                String postContent = userInput.getText().toString();
                intent.putExtra("titleOfPost", head);
                intent.putExtra("contentOfPost",postContent);
                //create simple post id

                //store the post as json file in directory
                //creating file
                Post current = new Post(head, postContent);
                String postFileName = current.postID + ".json";
                String jsonString = current.toJson();
                //create file that can write in
                File postFile = new File(getApplicationContext().getFilesDir(),postFileName);
                if(!postFile.exists()){
                    try {
                        postFile.createNewFile();
                    } catch (IOException e) {
                        Log.e(TAG,"uncable to create file");
                        e.printStackTrace();
                    }
                }else {
                    postFile.delete();
                    try {
                        postFile.createNewFile();
                    } catch (IOException e) {
                        Log.e(TAG,"uncable to create file");
                        e.printStackTrace();
                    }
                }
                //write json stirng to file in the priveate file dir
                String fileContents = current.toJson();
                try {
                    FileOutputStream fos =  getApplicationContext().openFileOutput(postFileName,MODE_PRIVATE);
                    fos.write(jsonString.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    Log.e(TAG,"uncable to write file");
                    e.printStackTrace();
                }

                // and then we can store the post in sqlite here
                // erase content in create post
                title.setText("");
                userInput.setText("");
                startActivity(intent);
            }
        });
    }
}
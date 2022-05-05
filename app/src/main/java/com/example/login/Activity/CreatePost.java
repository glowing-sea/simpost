package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.FileIO.FileRW;
import com.example.login.DataContainer.Post;
import com.example.login.R;

import java.io.File;

public class CreatePost extends AppCompatActivity {
    private static final String TAG = "Activity_CreatPost";
    private String PRIVATE_DIR;
    Button posting;
    EditText userInput, title;
    Intent commingIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //basic set up

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        PRIVATE_DIR = getApplicationContext().getFilesDir().getPath();
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
                String postFileName = current.getPostID() + ".json";
                String jsonString = current.toJson();
                FileRW fileRW = new FileRW(getApplicationContext());

                boolean folderExist = new File(PRIVATE_DIR,"posts").exists();
                boolean savedFile = false;
                if (!folderExist){
                    fileRW.makDir(PRIVATE_DIR,"posts");
                }
                savedFile =  fileRW.savingString("posts",postFileName,jsonString);
                if(!savedFile){
                    Toast.makeText(getApplicationContext(), "unable to save", Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"file not saved");
                }
                //create file that can write in
                Log.i(TAG,"file created");


                // and then we can store the post in sqlite here
                // erase content in create post
                title.setText("");
                userInput.setText("");
                startActivity(intent);
            }
        });
    }
}
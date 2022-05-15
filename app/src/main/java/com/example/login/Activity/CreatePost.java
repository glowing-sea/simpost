package com.example.login.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.login.DataContainer.UserAdmin;
import com.example.login.DataContainer.Post;
import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URI;

public class CreatePost extends AppCompatActivity {
    private static final String TAG = "Activity_CreatPost";
    private String PRIVATE_DIR;
    Button posting;
    EditText userInput, title;
    ImageView image1, image2, image3;
    FloatingActionButton add1, add2, add3;
    UserAdmin current;
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
        image1 = findViewById(R.id.post_image_1);
        image2 = findViewById(R.id.post_image_2);
        image3 = findViewById(R.id.post_image_3);
        add1 = findViewById(R.id.add_post_image_1);
        add2 = findViewById(R.id.add_post_image_2);
        add3 = findViewById(R.id.add_post_image_3);
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
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 101);
            }
        });
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 102);
            }
        });
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100){
            Uri imageURI = data.getData();
            image1.setImageURI(imageURI);
        }
        else if (resultCode == RESULT_OK && requestCode == 101){
            Uri imageURI = data.getData();
            image2.setImageURI(imageURI);
        }
        else if (resultCode == RESULT_OK && requestCode == 102){
            Uri imageURI = data.getData();
            image3.setImageURI(imageURI);
        }}}
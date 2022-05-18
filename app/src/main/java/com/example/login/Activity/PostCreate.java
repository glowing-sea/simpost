package com.example.login.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login.DataContainer.Me;
import com.example.login.Database.HelperMethods;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PostCreate extends AppCompatActivity {
    private static final String TAG = "Activity_CreatPost";
    String poster = Me.getInstance().getUsername();
    Button posting;
    EditText userInput, title;
    ImageView image1, image2, image3;
    FloatingActionButton add1, add2, add3;
    Bitmap i1, i2, i3;
    UserDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //basic set up

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);
        //getIntent
        db = new UserDAOImpl(getApplicationContext());

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
                Intent intent = new Intent(getApplicationContext(), Post.class);
                String head = title.getText().toString();
                String postContent = userInput.getText().toString();
                //creating file
                com.example.login.DataContainer.Post current = new com.example.login.DataContainer.Post(poster, head, postContent, i1, i2, i3, TAG, getApplicationContext());
                boolean ind = db.addPost(current);
                if (head.equals("")){
                    Toast.makeText(PostCreate.this, "post title can`t be empty", Toast.LENGTH_SHORT).show();
                }
                else if (postContent.equals("")){
                    Toast.makeText(PostCreate.this, "post content can`t be empty", Toast.LENGTH_SHORT).show();
                }
                else if (ind) {
                    title.setText("");
                    userInput.setText("");
                    Toast.makeText(PostCreate.this, "post created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);}
                else {
                    Toast.makeText(PostCreate.this, "post creation failed", Toast.LENGTH_SHORT).show();
                }
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
        Uri imageUri = data.getData();
        if (resultCode == RESULT_OK && requestCode == 100){
            Uri imageURI = data.getData();
            image1.setImageURI(imageURI);
            i1 = HelperMethods.uri2bitmap(imageUri, getApplicationContext());
        }
        else if (resultCode == RESULT_OK && requestCode == 101){
            Uri imageURI = data.getData();
            image2.setImageURI(imageURI);
            i2 = HelperMethods.uri2bitmap(imageUri, getApplicationContext());
        }
        else if (resultCode == RESULT_OK && requestCode == 102){
            Uri imageURI = data.getData();
            image3.setImageURI(imageURI);
            i3 = HelperMethods.uri2bitmap(imageUri, getApplicationContext());
        }}}
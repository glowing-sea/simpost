package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends AppCompatActivity {

    Button newPost;
    TextView firstPost,secondPost,thirdPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        newPost = findViewById(R.id.newPostButton);
        firstPost = findViewById(R.id.firstPost);
        secondPost = findViewById(R.id.secondPost);
        thirdPost = findViewById(R.id.thirdPost);


        firstPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPage.this, ViewPost.class);
                startActivity(i);
            }
        });
        secondPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPage.this, ViewPost.class);
                startActivity(i);
            }
        });
        thirdPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPage.this, ViewPost.class);
                startActivity(i);
            }
        });
    }

    public void newPostButton(View v) {
        Intent i = new Intent(this, ViewPost.class);
        startActivity(i);
    }



}
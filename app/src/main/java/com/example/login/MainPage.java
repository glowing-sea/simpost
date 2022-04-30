package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends AppCompatActivity {

    Button newPost, go;
    EditText search;
    TextView firstPost,secondPost,thirdPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        newPost = findViewById(R.id.newPostButton);
        firstPost = findViewById(R.id.firstPost);
        secondPost = findViewById(R.id.secondPost);
        thirdPost = findViewById(R.id.thirdPost);
        search = findViewById(R.id.searchText);
        go = findViewById(R.id.confirmSearch);


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
        //make the search
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这个部分我想的我们可以把关键词pass到result page，然后在result page再根据关键词从数据库里找结果
                Intent toResult = new Intent(MainPage.this, SearchResultPage.class);
                toResult.putExtra("keyword", search.getText().toString());
                startActivity(toResult);
            }
        });
    }

    public void newPostButton(View v) {
        Intent i = new Intent(this, CreatePost.class);
        startActivity(i);
    }



}
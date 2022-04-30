package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewPost extends AppCompatActivity {
    Post current;
    TextView title, content;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        // create post by intent
        Bundle fromCreate = getIntent().getExtras();
        String t = "";
        String c = "";
        if (fromCreate != null){
            t = fromCreate.getString("titleOfPost");
            c = fromCreate.getString("contentOfPost");
        }
        title = findViewById(R.id.postTitleText);
        content = findViewById(R.id.postContentText);
        back = findViewById(R.id.returnKey);
        title.setText(t);
        content.setText(c);
        // button going back to main page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewPost.this, MainPage.class);
                startActivity(intent);
            }
        });
    }
}
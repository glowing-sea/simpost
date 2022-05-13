package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.login.DataContainer.Post;
import com.example.login.R;

public class ViewPost extends AppCompatActivity {
    Post current;
    TextView title, content, likeCount;
    Button back, like, dislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        this.setTitle("Here is the detail of the post");
        // create post by intent
        Bundle fromCreate = getIntent().getExtras();
        String t = "";
        String c = "";
        if (fromCreate != null){
            current = (Post) getIntent().getExtras().getSerializable("POST");
        }
        t = current.getTitle();
        c = current.getContent();
        String l = String.valueOf(current.getLike());
        title = findViewById(R.id.postTitleText);
        content = findViewById(R.id.postContentText);
        back = findViewById(R.id.returnKey);
        like = findViewById(R.id.like);
        dislike = findViewById(R.id.dislike);
        likeCount = findViewById(R.id.like_count);
        title.setText(t);
        content.setText(c);
        l = "Current likes:" + " " + l;
        likeCount.setText(l);
        getIntent().removeExtra("POST");
        // button going back to main page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewPost.this, PostsPage.class);
                startActivity(intent);
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里最后改成改变数据库
                current.changeLike(1);
                like.setEnabled(false);
                String LA = "Current likes:" + " " + String.valueOf(current.getLike());
                likeCount.setText(LA);
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里最后改成改变数据库
                current.changeLike(-1);
                dislike.setEnabled(false);
                String LA = "Current likes:" + " " + String.valueOf(current.getLike());
                likeCount.setText(LA);
            }
        });
    }
}
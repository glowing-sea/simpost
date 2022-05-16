package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.DataContainer.Post;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

public class ViewPost extends AppCompatActivity {
    Post current;
    ImageView image1, image2, image3;
    TextView title, content, likeCount, viewCount, postTime;
    Button back, like, dislike;
    UserDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        this.setTitle("Here is the detail of the post");
        // create post by intent
        Bundle fromCreate = getIntent().getExtras();
        String t = "";
        String c = "";
        db = new UserDAOImpl(getApplicationContext());
        if (fromCreate != null){
            current = db.getPost(fromCreate.getInt("POST"));
        }
        getIntent().removeExtra("POST");

        //Set up
        t = current.getTitle();
        c = current.getContent();
        String l = String.valueOf(current.getLikes());
        title = findViewById(R.id.postTitleText);
        content = findViewById(R.id.postContentText);
        back = findViewById(R.id.returnKey);
        like = findViewById(R.id.like);
        dislike = findViewById(R.id.dislike);
        likeCount = findViewById(R.id.like_count);
        viewCount = findViewById(R.id.view_count);
        postTime = findViewById(R.id.post_publish_time);
        image1 = findViewById(R.id.view_image_1);
        image2 = findViewById(R.id.view_image_2);
        image3 = findViewById(R.id.view_image_3);

        //Set images
        Bitmap i1 = current.image1;
        if (i1 != null){
            image1.setImageBitmap(i1);
        }
        Bitmap i2 = current.image1;
        if (i2 != null){
            image1.setImageBitmap(i2);
        }
        Bitmap i3 = current.image1;
        if (i3 != null){
            image1.setImageBitmap(i3);
        }
        //Set date and likes
        String date = current.getDate();
        String lString = "Current likes: " + String.valueOf(current.getLikes().size());
        String vString = "Current Views: " + String.valueOf(current.getViews().size());
        likeCount.setText(lString);
        viewCount.setText(vString);
        date = "Post published by - "+ date;
        postTime.setText(date);
        // Set title and content
        title.setText(t);
        content.setText(c);
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
                like.setEnabled(false);
                String LA = "Current likes:" + " " + String.valueOf(current.getLikes().size() + 1);
                boolean b = current.addLikes(current.creator);
                if (b){
                   likeCount.setText(LA);}
                else {
                    Toast.makeText(ViewPost.this, "You`ve already liked this post", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dislike.setEnabled(false);
                String LA = "Current likes:" + " " + String.valueOf(current.getLikes().size() - 1);
                likeCount.setText(LA);
            }
        });
    }
}
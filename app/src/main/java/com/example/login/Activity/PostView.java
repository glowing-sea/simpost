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

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Post;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

import java.util.HashSet;

public class PostView extends AppCompatActivity {
    Post current;
    ImageView image1, image2, image3;
    TextView title, content, likeCount, viewCount, postTime;
    Button back, like, dislike;
    UserDAO db;
    Me me = Me.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        this.setTitle("Here is the detail of the post");


        int id = getIntent().getIntExtra("postID", 0);


        db = new UserDAOImpl(getApplicationContext());
        current = db.getPost(id);

        current = db.getPost(id);
        getIntent().removeExtra("postID");

        //Set up
        String t = current.getTitle();
        String c = current.getContent();
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
        Bitmap i2 = current.image2;
        if (i2 != null){
            image2.setImageBitmap(i2);
        }
        Bitmap i3 = current.image3;
        if (i3 != null){
            image3.setImageBitmap(i3);
        }
        //Set date and likes
        String date = current.getDate();
        HashSet<String> viewers = current.getViews();
        HashSet<String> likes = current.getLikes();
        viewers.add(me.getUsername());
        String lString = "Current likes: " + String.valueOf(likes.size());
        String vString = "Current Views: " + String.valueOf(viewers.size());
        current.setViews(viewers);
        likeCount.setText(lString);
        viewCount.setText(vString);
        date = "Post published by - "+ date;
        postTime.setText(date);
        // Set title and content
        title.setText(t);
        content.setText(c);
        // Check liked or not
        if (likes.contains(me.username)){
            like.setEnabled(false);
            dislike.setEnabled(true);
        }
        else {
            like.setEnabled(true);
            dislike.setEnabled(false);
        }
        // button going back to main page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostView.this, Post.class);
                startActivity(intent);
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                like.setEnabled(false);
                String LA = "Current likes:" + " " + String.valueOf(current.getLikes().size() + 1);
                boolean b = current.addLikes(me.getUsername());
                if (b){
                   likeCount.setText(LA);}
                else {
                    Toast.makeText(PostView.this, "You`ve already liked this post", Toast.LENGTH_SHORT).show();
                }
                dislike.setEnabled(true);
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dislike.setEnabled(false);
                HashSet<String> likers = current.getLikes();
                if (likers.contains(me.username)){
                    likers.remove(me.username);
                    current.setLikes(likers);
                    String LA = "Current likes:" + " " + String.valueOf(likers.size());
                    likeCount.setText(LA);}
                else {
                    Toast.makeText(PostView.this, "You haven`t liked the post yet", Toast.LENGTH_SHORT).show();
                }
                like.setEnabled(true);
            }

        });
    }
}
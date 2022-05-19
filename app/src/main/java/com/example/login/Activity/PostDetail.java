package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Post;
import com.example.login.Database.HelperMethods;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PostDetail extends AppCompatActivity {
    SoundPool soundPool;
    List<Integer> soundIds;
    Post current;
    ImageView image1, image2, image3;
    TextView title, content, likeCount, viewCount, postTime;
    Button commentButton, likeButton;
    UserDAO db;
    Me me = Me.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        setTitle("Detail");

        // Set up Sounds
        soundPool = new SoundPool.Builder().setMaxStreams(1).build();
        soundIds = new ArrayList<>();
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.welcome,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.dislike_post,1));

        // Retrieve Post ID
        int id = getIntent().getIntExtra("postID", 0);
        getIntent().removeExtra("postID");

        // Access Database
        db = new UserDAOImpl(getApplicationContext());
        current = db.getPost(id);

        // IMPORTANT ADD ME TO THE VIEWER SET OF THE POST
        current.addViews(me.username);

        // IMPORTANT ADD POST TO MY HISTORY
        HashSet<Integer> history = me.getHistory();
        history.add(id);
        me.setHistory(history);

        // Link views IDs
        title = findViewById(R.id.postTitleText);
        content = findViewById(R.id.postContentText);
        likeCount = findViewById(R.id.like_count);
        viewCount = findViewById(R.id.view_count);
        image1 = findViewById(R.id.view_image_1);
        image2 = findViewById(R.id.view_image_2);
        image3 = findViewById(R.id.view_image_3);
        postTime = findViewById(R.id.post_publish_time);
        likeButton = findViewById(R.id.like);
        commentButton = findViewById(R.id.to_comments);

        // Extract post information
        String t = current.getTitle();
        String c = current.getContent();
        HashSet<String> l = current.getLikes();
        HashSet<String> v = current.getViews();
        Bitmap i1 = current.image1;
        Bitmap i2 = current.image2;
        Bitmap i3 = current.image3;

        //Set views

        // Set title and content (filter abusive language if needed)
        if (me.getPrivacySettings().get(5)){
            title.setText(HelperMethods.getCensored(t));
            content.setText(HelperMethods.getCensored(c)); }
        else {
            title.setText(t);
            content.setText(c); }

        // Set images
        if (i1 != null){ image1.setImageBitmap(i1); }
        if (i2 != null){ image2.setImageBitmap(i2); }
        if (i3 != null){ image3.setImageBitmap(i3); }


        //Set date and likes
        String lString = "Current likes: " + l.size();
        String vString = "Current Views: " + v.size();
        String date = "Post published by <" + current.creator + "> on " + current.getDate();
        likeCount.setText(lString);
        viewCount.setText(vString);
        postTime.setText(date);

        // Check liked or not
        if (l.contains(me.username)){
            likeButton.setText("Unlike");
        }
        else {
            likeButton.setText("Like");
        }

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (likeButton.getText().toString().equals("Unlike")){
                    soundPool.play(soundIds.get(1),1,1,1,0,1);
                    l.remove(me.username);
                    boolean result = current.setLikes(l);
                    if (result){
                        Toast.makeText(PostDetail.this, "You unliked this post!", Toast.LENGTH_SHORT).show();
                        String lString = "Current likes: " + l.size();
                        likeCount.setText(lString);
                        likeButton.setText("Like");
                    }
                } else {
                    soundPool.play(soundIds.get(0),1,1,1,0,1);
                    boolean result = current.addLikes(me.getUsername());
                    if (result){
                        Toast.makeText(PostDetail.this, "You liked this post!", Toast.LENGTH_SHORT).show();
                        String lString = "Current likes: " + l.size();
                        likeCount.setText(lString);
                        likeButton.setText("Unlike");
                    }
                }
            }
        });


        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostDetail.this, Comments.class);
                intent.putExtra("postID",id);
                startActivity(intent);
                // finish();
            }
        });
    }
}

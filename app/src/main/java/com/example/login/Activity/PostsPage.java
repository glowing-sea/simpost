package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.UserAdmin;
import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PostsPage extends AppCompatActivity {
    UserAdmin currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_page);
        this.setTitle(this.getText(R.string.posts));
        // initiate the current User
        Bundle fromCreate = getIntent().getExtras();
        if (fromCreate != null){
            currentUser = (UserAdmin) getIntent().getExtras().getSerializable("USER");
        }
        // Page transfer method of the bottom navigator
        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_ico_posts:
                    break;
                case R.id.nav_ico_search:
                    startActivity(new Intent(getApplicationContext(), SearchPage.class));
                    this.overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.nav_ico_home:
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    intent.putExtra("USER", currentUser);
                    startActivity(intent);
                    this.overridePendingTransition(0, 0);
                    finish();
                    break; }
            return false;
        });

        ImageView newPost = findViewById(R.id.newPost);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostsPage.this, CreatePost.class);
                startActivity(i);
            }
        });

        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.rv_posts);

        //仅测试，最后将allPosts改成数据库中需要显示的post即可
        Post p1 = new Post("Post A", "This is content A.");
        Post p2 = new Post("Post B", "This is content B.");
        Post p3 = new Post("Post C", "This is content C.");
        Post p4 = new Post("Post D", "This is content D.");
        Post p5 = new Post("Post E", "This is content E.");

        List<Post> allPosts = new ArrayList<>();
        allPosts.add(p1);
        allPosts.add(p2);
        allPosts.add(p3);
        allPosts.add(p4);
        allPosts.add(p5);

        PostAdapter postAdapter = new PostAdapter(PostsPage.this,allPosts);
        rvPosts.setAdapter(postAdapter);
        // rvPosts.setLayoutManager(new LinearLayoutManager((this)));
        rvPosts.setLayoutManager(new GridLayoutManager(this, 2));

    }
}
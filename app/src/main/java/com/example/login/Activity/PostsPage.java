package com.example.login.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Me;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PostsPage extends AppCompatActivity {
    Me me;
    UserDAO db;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_page);
        this.setTitle(this.getText(R.string.posts));
        me = Me.getInstance();
        db = new UserDAOImpl(getApplicationContext());
        // initiate the current User
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
                    Intent intent = new Intent(getApplicationContext(), Home.class);
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

//        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.rv_posts);
//
//        allPosts = db.getRecentPost();
//
//        PostAdapter postAdapter = new PostAdapter(PostsPage.this,allPosts);
//        rvPosts.setAdapter(postAdapter);
//        // rvPosts.setLayoutManager(new LinearLayoutManager((this)));
//        rvPosts.setLayoutManager(new GridLayoutManager(this, 2));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_show_recent){
            Toast.makeText(PostsPage.this, "recent posts shown", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
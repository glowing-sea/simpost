package com.example.login.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.PostPreview;
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

public class Post extends AppCompatActivity {
    Me me;
    UserDAO db;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
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
                Intent i = new Intent(Post.this, PostCreate.class);
                startActivityForResult(i, 100);
            }
        });

        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.rv_posts);

        ArrayList<PostPreview> allPosts = db.getAllPosts();


        if (allPosts == null) {
            Toast.makeText(Post.this, "No Post", Toast.LENGTH_LONG).show();
        } else {
            PostAdapter postAdapter = new PostAdapter(Post.this,allPosts);
            rvPosts.setAdapter(postAdapter);
            rvPosts.setLayoutManager(new GridLayoutManager(this, 2));
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.to_find_users){
            Toast.makeText(Post.this, "recent posts shown", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100){
            recreate();
        }
    }
}
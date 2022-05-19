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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostFollowing extends AppCompatActivity {
    Me me;
    UserDAO db;
    RecyclerView rvPosts;
    TextView noPostsFound;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        this.setTitle("Following Posts");
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

        FloatingActionButton newPost = findViewById(R.id.newPost);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostFollowing.this, PostCreate.class);
                startActivityForResult(i, 100);
            }
        });

        rvPosts = (RecyclerView) findViewById(R.id.rv_posts);

        ArrayList<PostPreview> allPosts = db.getFollowingPosts(100, me.getFollowing());


        noPostsFound = findViewById(R.id.no_post_found);
        if (allPosts == null || allPosts.isEmpty())  {
            noPostsFound.setText("No Posts Found");
        } else {
            PostAdapter postAdapter = new PostAdapter(PostFollowing.this,allPosts);
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
        if (id == R.id.get_recent_posts){
            Intent intent = new Intent(PostFollowing.this, PostRecent.class);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
            finish();
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
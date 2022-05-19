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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class Post extends AppCompatActivity {
    Me me;
    UserDAO db;
    RecyclerView rvPosts;
    TextView noPostsFound;
    ArrayList<PostPreview> posts;
    FloatingActionButton newPost, deletePost;
    int deleteOption;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // initiate the current User
        me = Me.getInstance();
        db = new UserDAOImpl(getApplicationContext());
        newPost = findViewById(R.id.newPost);
        deletePost = findViewById(R.id.deletePosts);
        rvPosts = (RecyclerView) findViewById(R.id.rv_posts);


        if (me.currentPage.equals("recent")){
            posts = db.getAllPosts(100);
            this.setTitle("Recent Posts");
            deletePost.hide();
        }
        if (me.currentPage.equals("following")){
            posts = db.getPeoplePosts(100, me.getFollowing());
            this.setTitle("Following Posts");
            deletePost.hide();
        }
        if (me.currentPage.equals("history")){
            posts = db.getSelectPosts(Integer.MAX_VALUE, me.getHistory());
            this.setTitle("History");
            newPost.hide();
            deleteOption = 0;
        }
        if (me.currentPage.equals("myPosts")){
            HashSet<String> single = new HashSet<>();
            single.add(me.username);
            posts = db.getPeoplePosts(Integer.MAX_VALUE, single);
            this.setTitle("My Posts");
            newPost.hide();
            deleteOption = 1;
        }

// =================================== BUTTONS ================================================== //

        noPostsFound = findViewById(R.id.no_post_found);
        if (posts == null || posts.isEmpty()) {
            noPostsFound.setText("No Posts Found");
        } else {
            PostAdapter postAdapter = new PostAdapter(Post.this,posts);
            rvPosts.setAdapter(postAdapter);
            rvPosts.setLayoutManager(new GridLayoutManager(this, 2));
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
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    this.overridePendingTransition(0, 0);
                    finish();
                    break; }
            return false;
        });

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Post.this, PostCreate.class);
                startActivityForResult(i, 100);
            }
        });

        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteOption == 0){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Post.this);
                    alertDialog.setMessage("Do you want to clear your history?");
                    alertDialog.setCancelable(true);
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            HashSet<Integer> his = new HashSet<>();
                            me.setHistory(his);
                            recreate();
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.create().show();
                }
                if (deleteOption == 1){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Post.this);
                    alertDialog.setMessage("Are you sure you want to delete all your posts?");
                    alertDialog.setCancelable(true);
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteSomeonePosts(me.username);
                            recreate();
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.create().show();
                }
            }
        });

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
            me.currentPage = "recent";
            recreate();
        }
        if (id == R.id.get_following_posts){
            me.currentPage = "following";
            recreate();
        }
        if (id == R.id.get_history_posts){
            me.currentPage = "history";
            recreate();
        }
        if (id == R.id.get_my_posts){
            me.currentPage = "myPosts";
            recreate();
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
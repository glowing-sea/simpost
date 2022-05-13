package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.login.DataContainer.Post;
import com.example.login.R;

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);
        Bundle fromCreate = getIntent().getExtras();
        String t = "";
        if (fromCreate != null){
            t = fromCreate.getString("keyword");
        }
        getIntent().removeExtra("keyword");
        RecyclerView rvPosts = findViewById(R.id.result_posts);
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
        SearchResultAdapter searchResultAdapter = new SearchResultAdapter(SearchResultPage.this,allPosts);
        rvPosts.setAdapter(searchResultAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager((this)));

    }
}
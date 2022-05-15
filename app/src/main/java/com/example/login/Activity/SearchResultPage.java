package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.login.DataContainer.PostOld;
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
        PostOld p1 = new PostOld("Post A", "This is content A.");
        PostOld p2 = new PostOld("Post B", "This is content B.");
        PostOld p3 = new PostOld("Post C", "This is content C.");
        PostOld p4 = new PostOld("Post D", "This is content D.");
        PostOld p5 = new PostOld("Post E", "This is content E.");

        List<PostOld> allPosts = new ArrayList<>();
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
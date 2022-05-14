package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.login.DataContainer.Post;
import com.example.login.R;
import com.example.login.parserAndTokenizer.Token;
import com.example.login.parserAndTokenizer.Tokenizier;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchResultPage extends AppCompatActivity {
    private final  String TAG = "SearchResultPage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //basic setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);

        //get extra
        Bundle fromCreate = getIntent().getExtras();
        String searchedText = "";
        if (fromCreate != null){
            searchedText = fromCreate.getString("keyword");
        }
        getIntent().removeExtra("keyword");

        Log.i(TAG,"tokenizing:" + searchedText);

        //tokenize the searched text
        Tokenizier tokenizier = new Tokenizier(searchedText);
        List<Token> tokensContained = tokenizier.tokenize();

        //catorgise the tokens we have for optimising search
        List<Token> userNamtToken = new ArrayList<>();
        List<Token> titleToken = new ArrayList<>();
        List<Token> wordToken = new ArrayList<>();
        //debuging code
        String debugString = "";
        int index = 0;
        while(index < tokensContained.size()){
            debugString += tokensContained.get(index).show();
            debugString += "|";
            int type = tokensContained.get(index).returnType();
            if (type == 0){
                userNamtToken.add(tokensContained.get(index));
            }else if(type == 2){
                titleToken.add(tokensContained.get(index));
            }else {
                wordToken.add(tokensContained.get(index));
            }
            index ++;
        }
        Log.i(TAG,"tokens are:" + debugString);

        //select things form data base according to the token


        //adding things to the result
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
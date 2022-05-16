package com.example.login.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ServiceStartNotAllowedException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.PostOld;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.example.login.parserAndTokenizer.Parser;
import com.example.login.parserAndTokenizer.Token;
import com.example.login.parserAndTokenizer.Tokenizier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SearchResultPage extends AppCompatActivity {
    private final  String TAG = "SearchResultPage";
    @RequiresApi(api = Build.VERSION_CODES.N)
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

        Log.i(TAG,"Parsing:" + searchedText);
        Parser parser = new Parser(searchedText);
        String matchingString = parser.parsedString();
        UserDAOImpl userDAO = new UserDAOImpl(getApplicationContext());
        Set<Post> result = userDAO.postMathchFTS4(matchingString);

        //select things form data base according to the token
        List<Post> allPosts = new ArrayList<>();
        if (result != null){
            Iterator iterator = result.iterator();
            while (iterator.hasNext()){
                allPosts.add((Post)iterator.next());
            }
        }
        //adding things to the result
        RecyclerView rvPosts = findViewById(R.id.result_posts);
        PostOld p1 = new PostOld("Post A", "This is content A.");
        PostOld p2 = new PostOld("Post B", "This is content B.");
        PostOld p3 = new PostOld("Post C", "This is content C.");
        PostOld p4 = new PostOld("Post D", "This is content D.");
        PostOld p5 = new PostOld("Post E", "This is content E.");


        SearchResultAdapter searchResultAdapter = new SearchResultAdapter(getApplicationContext(),allPosts);
        rvPosts.setAdapter(searchResultAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager((this)));

    }
}
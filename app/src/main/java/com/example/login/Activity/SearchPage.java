package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DataContainer.Post;
import com.example.login.R;
import com.example.login.parserAndTokenizer.Token;
import com.example.login.parserAndTokenizer.Tokenizier;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SearchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        this.setTitle(this.getText(R.string.search));

        // Page transfer method of the bottom navigator
        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_ico_posts:
                    startActivity(new Intent(getApplicationContext(), PostsPage.class));
                    this.overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.nav_ico_search:
                    break;
                case R.id.nav_ico_home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    this.overridePendingTransition(0, 0);
                    finish();
                    break; }
            return false;
        });


        Button searchButton;
        EditText searchText;

        // Set up search text
        searchText = findViewById(R.id.editText_search_page_searcheContent);

        // Set up search button
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView input = findViewById(R.id.editText_search_page_searcheContent);
                String searchedText = input.getText().toString();
                Toast debug = Toast.makeText(getApplicationContext(),"Searching: " + searchedText,Toast.LENGTH_LONG);
                debug.show();
                if (!searchedText.equals("")){
                    Intent toResult = new Intent(getApplicationContext(), SearchResultPage.class);
                    toResult.putExtra("keyword", searchText.getText().toString());
                    startActivity(toResult);}
                else {
                    Toast.makeText(SearchPage.this, "keyword can`t be empty", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}
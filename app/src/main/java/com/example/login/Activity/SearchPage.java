package com.example.login.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.example.login.gamePage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
                    startActivity(new Intent(getApplicationContext(), Post.class));
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
        Button game = (Button) findViewById(R.id.btn_search_game);

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

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =(new Intent(getApplicationContext(), gamePage.class));
                startActivity(intent);
            }
        });

//        Button findUsers;
//        findUsers = findViewById(R.id.go_to_find_users);
//        findUsers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(SearchPage.this, SearchUsers.class));
//                overridePendingTransition(0, 0);
//                finish();
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.to_search_users, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.to_find_users){
            startActivity(new Intent(SearchPage.this, SearchUsers.class));
            overridePendingTransition(0, 0);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
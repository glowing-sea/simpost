package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        searchText = findViewById(R.id.searchText);

        // Set up search button
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这个部分我想的我们可以把关键词pass到result page，然后在result page再根据关键词从数据库里找结果
                Intent toResult = new Intent(getApplicationContext(), SearchResultPage.class);
                toResult.putExtra("keyword", searchText.getText().toString());
                startActivity(toResult);
            }
        });
    }

}
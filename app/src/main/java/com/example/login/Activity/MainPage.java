package com.example.login.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Activity.fragments.HomeFragment;
import com.example.login.Activity.fragments.PostsFragment;
import com.example.login.Activity.fragments.SearchFragment;
import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPage extends AppCompatActivity {

    // ActivityMainPageBinding binding;

    private ActionBar toolbar;

//    Button newPost, go;
//    EditText search;
//    TextView firstPost,secondPost,thirdPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        toolbar = getSupportActionBar();
        toolbar.setTitle(this.getText(R.string.home));
        changeFragment(new HomeFragment());


        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);

        nav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.nav_ico_posts:
                    toolbar.setTitle(this.getText(R.string.posts));
                    changeFragment(new PostsFragment());
                    break;
                case R.id.nav_ico_search:
                    toolbar.setTitle(this.getText(R.string.search));
                    changeFragment(new SearchFragment());
                    break;
                case R.id.nav_ico_home:
                    toolbar.setTitle(this.getText(R.string.home));
                    changeFragment(new HomeFragment());
                    break;
            }
            return false;
        });

        // binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        // setContentView(binding.getRoot());

//        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
//
//            switch (item.getItemId()){
//
//                case R.id.nav_ico_posts:
//                    //changeFragment(new PostsFragment());
//                    break;
//                case R.id.nav_ico_search:
//                    //changeFragment(new SearchFragment());
//                    break;
//                case R.id.nav_ico_home:
//                    //changeFragment(new HomeFragment());
//                    break;
//            }
//
//
//            return true;
//        });


//        newPost = findViewById(R.id.newPostButton);
//        firstPost = findViewById(R.id.firstPost);
//        secondPost = findViewById(R.id.secondPost);
//        thirdPost = findViewById(R.id.thirdPost);
//        search = findViewById(R.id.searchText);
//        go = findViewById(R.id.confirmSearch);
//
//
//        firstPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainPage.this, ViewPost.class);
//                startActivity(i);
//            }
//        });
//        secondPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainPage.this, ViewPost.class);
//                startActivity(i);
//            }
//        });
//        thirdPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainPage.this, ViewPost.class);
//                startActivity(i);
//            }
//        });
//        //make the search
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                search.setText("");
//            }
//        });
//        go.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //这个部分我想的我们可以把关键词pass到result page，然后在result page再根据关键词从数据库里找结果
//                Intent toResult = new Intent(MainPage.this, SearchResultPage.class);
//                toResult.putExtra("keyword", search.getText().toString());
//                startActivity(toResult);
//            }
//        });
//    }
//
//    public void newPostButton(View v) {
//        Intent i = new Intent(this, CreatePost.class);
//        startActivity(i);

    }

    private void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

//    public void buttonSearch(){
//        // Toast.makeText(MainPage.this, "Now Searching", Toast.LENGTH_SHORT).show();
//        TextView findPost = findViewById(R.id.find_post);
//        findPost.setText("123");
//    }
}
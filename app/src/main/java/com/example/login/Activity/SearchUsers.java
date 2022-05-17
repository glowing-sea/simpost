package com.example.login.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Someone;
import com.example.login.DataContainer.UserAdmin;
import com.example.login.DataContainer.UserPreview;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchUsers extends AppCompatActivity {

    UserDAO db;
    ArrayList<UserPreview> users;
    UserAdapter userAdapter;
    RecyclerView recyclerView;
    Button searchUser;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);
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

        recyclerView = findViewById(R.id.users_list);
        db = new UserDAOImpl(getApplicationContext());
        users = db.getAllUsers();
        userAdapter = new UserAdapter(SearchUsers.this, this, users, null);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        // rvPosts.setLayoutManager(new GridLayoutManager(this, 2));

        searchUser = findViewById(R.id.user_search_button);
        username = findViewById(R.id.username_search);
        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String person = username.getText().toString();
                Someone someone = db.getSomeoneData(person);
                if (someone == null){
                    Toast.makeText(getApplicationContext(), "The username does not exist", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SearchUsers.this, HomeSomeone.class);
                    intent.putExtra("USER", person);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.to_search_posts, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.to_find_posts){
            startActivity(new Intent(SearchUsers.this, SearchPage.class));
            overridePendingTransition(0, 0);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
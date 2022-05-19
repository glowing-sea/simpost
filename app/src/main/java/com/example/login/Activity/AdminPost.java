package com.example.login.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.PostPreview;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class AdminPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post);



        // Set up recycle view
        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.admin_rv);
        UserDAO db = new UserDAOImpl(this);
        ArrayList<PostPreview> allPosts = db.getAllPosts(Integer.MAX_VALUE);
        AdminPostAdapter adminPostAdapter = new AdminPostAdapter(AdminPost.this, this,allPosts);
        rvPosts.setAdapter(adminPostAdapter);
        rvPosts.setLayoutManager(new GridLayoutManager(this, 2));



        FloatingActionButton delete = findViewById(R.id.delete_select_post_1);
        FloatingActionButton reset = findViewById(R.id.reset_select_post_1);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminPostDelete.class);
                startActivityForResult(intent, 100);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminPostReset.class);
                startActivityForResult(intent, 100);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.admin_user_management){
            startActivity(new Intent(AdminPost.this, AdminUser.class));
            overridePendingTransition(0, 0);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 100){
                recreate();
            }
        }
    }
}
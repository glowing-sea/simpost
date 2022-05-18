package com.example.login.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.UserAdmin;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class AdminPage extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton,updateButton,deleteButton;

    UserDAO db;
    ArrayList<UserAdmin> users;
    HomeUserListAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.admin_user_list);
        addButton = findViewById(R.id.admin_add_button);
        updateButton = findViewById(R.id.admin_refresh_button);
        deleteButton = findViewById(R.id.delete_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddUser.class);
                startActivityForResult(intent, 100);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminUpdateUser.class);
                startActivityForResult(intent, 100);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminDeleteUser.class);
                startActivityForResult(intent, 100);
            }
        });


        db = new UserDAOImpl(getApplicationContext());
        users = db.getAllUsersAdmin();


        userAdapter = new HomeUserListAdapter(AdminPage.this, this,null, users);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        // rvPosts.setLayoutManager(new GridLayoutManager(this, 2));
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
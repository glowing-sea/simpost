package com.example.simpostapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpostapp.DataContainer.UserAdmin;
import com.example.simpostapp.Database.UserDAO;
import com.example.simpostapp.Database.UserDAOImpl;
import com.example.simpostapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class AdminUser extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton,updateButton,deleteButton;

    UserDAO db;
    ArrayList<UserAdmin> users;
    AdminUserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Admin Users");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.admin_user_list);
        addButton = findViewById(R.id.admin_add_button);
        updateButton = findViewById(R.id.admin_refresh_button);
        deleteButton = findViewById(R.id.delete_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminUserAdd.class);
                startActivityForResult(intent, 100);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminUserUpdate.class);
                startActivityForResult(intent, 100);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminUserDelete.class);
                startActivityForResult(intent, 100);
            }
        });


        db = new UserDAOImpl(getApplicationContext());
        users = db.getAllUsersAdmin();


        userAdapter = new AdminUserAdapter(AdminUser.this, this, users);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.admin_post_management){
            startActivity(new Intent(AdminUser.this, AdminPost.class));
            overridePendingTransition(0, 0);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.User;
import com.example.login.Database.DBHelper;
import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminPage extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton,updateButton,deleteButton;

    DBHelper db;
    ArrayList<User> users;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        recyclerView = findViewById(R.id.admin_user_list);
        addButton = findViewById(R.id.admin_add_button);
        updateButton = findViewById(R.id.admin_refresh_button);
        deleteButton = findViewById(R.id.delete_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddUserPage.class);
                startActivity(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminUpdateUserPage.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminDeleteUserPage.class);
                startActivity(intent);
            }
        });

        db = new DBHelper(getApplicationContext());
        users = new ArrayList<>();

        // Extra all information from the database and store it into the users list.
        databaseToArrays();

        userAdapter = new UserAdapter(AdminPage.this,users);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        // rvPosts.setLayoutManager(new GridLayoutManager(this, 2));

    }

    void databaseToArrays(){
        Cursor cursor = db.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, this.getText(R.string.no_data), Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                String username = cursor.getString(0);
                String password = cursor.getString(1);
                User user = new User(username, password);
                users.add(user);
            }
        }
    }
}
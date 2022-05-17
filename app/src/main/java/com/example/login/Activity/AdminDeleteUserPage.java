package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

public class AdminDeleteUserPage extends AppCompatActivity {

    EditText username;
    Button deleteButton, deleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_user_page);

        username = findViewById(R.id.admin_username_input2);
        deleteButton = findViewById(R.id.admin_delete_button);
        deleteAll = findViewById(R.id.delete_all_users);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDAO db = new UserDAOImpl(getApplicationContext());
                db.deleteUser(username.getText().toString().trim());
                setResult(RESULT_OK);
                finish();
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDAO db = new UserDAOImpl(getApplicationContext());
                db.truncateUsers();
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
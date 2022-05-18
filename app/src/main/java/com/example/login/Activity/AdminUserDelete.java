package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

public class AdminUserDelete extends AppCompatActivity {

    EditText username;
    Button deleteButton, deleteAll;
    String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Delete User");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_user);

        username = findViewById(R.id.admin_username_input2);
        deleteButton = findViewById(R.id.admin_delete_button);
        deleteAll = findViewById(R.id.delete_all_users);

        Bundle fromCreate = getIntent().getExtras();
        if (fromCreate != null){
            current = getIntent().getStringExtra("USER");
            username.setText(current);
            getIntent().removeExtra("USER");
        }

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
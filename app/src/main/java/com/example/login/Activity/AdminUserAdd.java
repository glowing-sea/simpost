package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminUserAdd extends AppCompatActivity {

    EditText username, password;
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Add User");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_user);

        username = findViewById(R.id.admin_username_input2);
        password = findViewById(R.id.admin_password_input2);
        addButton = findViewById(R.id.admin_add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDAOImpl db = new UserDAOImpl(getApplicationContext());
                db.addUser(username.getText().toString().trim(), password.getText().toString().trim());
                setResult(RESULT_OK);
            }
        });
    }
}
package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.login.Database.DBHelper;
import com.example.login.R;

public class AdminDeleteUserPage extends AppCompatActivity {

    EditText username;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_user_page);

        username = findViewById(R.id.admin_username_input2);
        deleteButton = findViewById(R.id.admin_delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(getApplicationContext());
                db.deleteUser(username.getText().toString().trim());
            }
        });
    }
}
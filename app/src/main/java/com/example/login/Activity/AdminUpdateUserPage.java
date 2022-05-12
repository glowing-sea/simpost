package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.Database.DBHelper;
import com.example.login.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminUpdateUserPage extends AppCompatActivity {

    EditText username, password;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_user_page);

        username = findViewById(R.id.admin_username_input2);
        password = findViewById(R.id.admin_password_input2);
        updateButton = findViewById(R.id.admin_update_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(getApplicationContext());
                db.updatePassword(username.getText().toString().trim(), password.getText().toString().trim());
            }
        });
    }
}
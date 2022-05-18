package com.example.simpostapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpostapp.Database.UserDAOImpl;
import com.example.simpostapp.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminUserUpdate extends AppCompatActivity {

    EditText username, password;
    Button updateButton;
    String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Update User");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_user);

        username = findViewById(R.id.admin_username_input2);
        password = findViewById(R.id.admin_password_input2);
        updateButton = findViewById(R.id.admin_update_button);
        Bundle fromCreate = getIntent().getExtras();
        if (fromCreate != null){
            current = getIntent().getStringExtra("USER");
            username.setText(current);
            getIntent().removeExtra("USER");
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDAOImpl db = new UserDAOImpl(getApplicationContext());
                db.setPassword(username.getText().toString().trim(), password.getText().toString().trim());
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
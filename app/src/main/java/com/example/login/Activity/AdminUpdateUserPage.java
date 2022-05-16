package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DataContainer.UserOld;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminUpdateUserPage extends AppCompatActivity {

    EditText username, password;
    Button updateButton;
    UserOld current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_user_page);

        username = findViewById(R.id.admin_username_input2);
        password = findViewById(R.id.admin_password_input2);
        updateButton = findViewById(R.id.admin_update_button);
        Bundle fromCreate = getIntent().getExtras();
        if (fromCreate != null){
            current = (UserOld) getIntent().getExtras().getSerializable("USER");
            String name = current.getUsername();
            username.setText(name);
            getIntent().removeExtra("USER");
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDAOImpl db = new UserDAOImpl(getApplicationContext());
                db.setPassword(username.getText().toString().trim(), password.getText().toString().trim());
                Intent i = new Intent(AdminUpdateUserPage.this, AdminPage.class);
                startActivity(i);
            }
        });
    }
}
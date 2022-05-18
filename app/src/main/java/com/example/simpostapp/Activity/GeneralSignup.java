package com.example.simpostapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simpostapp.Database.UserDAOImpl;
import com.example.simpostapp.R;

public class GeneralSignup extends AppCompatActivity {
    EditText name, password;
    Button confirm;
    UserDAOImpl db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_signup);
        db = new UserDAOImpl(getApplicationContext());

        name = findViewById(R.id.signupname);
        password = findViewById(R.id.signuppassword);
        confirm = findViewById(R.id.confirmSignup);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int re;
                String n = name.getText().toString().trim();
                String p = password.getText().toString().trim();
                re = db.addUser(n,p);
                switch (re) {
                    case 0: {
                        Toast.makeText(GeneralSignup.this, "You have successfully signed up", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    }
                    case -2: {
                        Toast.makeText(GeneralSignup.this,
                                "Username can only contains numbers, letter, '_', or '-', and at most 20 character", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case -1: {
                        Toast.makeText(GeneralSignup.this, "Username already existed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}






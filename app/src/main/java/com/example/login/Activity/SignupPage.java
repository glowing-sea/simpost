package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.DataContainer.User;
import com.example.login.Database.DBHelper;
import com.example.login.R;

public class SignupPage extends AppCompatActivity {
    EditText name, password;
    Button confirm;
    SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        name = findViewById(R.id.signupname);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirmSignup);
        userInfo = getSharedPreferences("info", Context.MODE_PRIVATE);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*DBHelper db = new DBHelper(SignupPage.this);
                String n = name.getText().toString();
                String p = password.getText().toString();
                db.addUser(n,p);*/
                confirm.setEnabled(false);
                String n = name.getText().toString();
                String p = password.getText().toString();
                SharedPreferences.Editor save = userInfo.edit();
                save.putString(n, p);
                save.commit();
                Toast.makeText(SignupPage.this,"You have successfully signed up", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SignupPage.this, LoginPage.class);
                startActivity(i);
            }
        });
    }
}



//    String n = name.getText().toString();
//    String p = password.getText().toString();
//这个就是signup的 等sqlmethod完全弄好了应该就可以，到时候在这里改
//                if (SqlMethod.existUser(n))
//                    Toast.makeText(SignupPage.this, "Username already exists", Toast.LENGTH_LONG).show();
//                else {
//                    User signup = new User(n, p);
//                    SqlMethod.save(signup);
//                    Toast.makeText(SignupPage.this,"You have successfully signed up", Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(SignupPage.this, LoginPage.class);
//                    startActivity(i);



package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

import java.util.ArrayList;

public class AdminGetterTestPage extends AppCompatActivity {

    UserDAO db;
    Button get;
    EditText input;
    TextView output;
    ImageView outputImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_getter_test_page);

        get = findViewById(R.id.getter_confirm);
        input = findViewById(R.id.getter_input);
        output = findViewById(R.id.getter_result);
        outputImage = findViewById(R.id.getter_image);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               db = new UserDAOImpl(getApplicationContext());

               ArrayList<String> following = new ArrayList<>();
               following.add("Henry");
               following.add("Amy");
               following.add("Ben");
               db.setFollowing("Jack", following);


               ArrayList<String> result = db.getFollowing("Jack");
               if (result == null)
                   output.setText("Null");
               else
                   output.setText(result.toString().length() + "");
            }
        });

    }
}
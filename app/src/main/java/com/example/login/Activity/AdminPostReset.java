package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;

public class AdminPostReset extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post_reset);

        int current = 0;
        UserDAO db = new UserDAOImpl(this);
        Bundle fromCreate = getIntent().getExtras();
        if (fromCreate != null){
            current = getIntent().getIntExtra("postID", 0);
            getIntent().removeExtra("postID");
        }
        Button reset = findViewById(R.id.post_reset_button);
        EditText posID = findViewById(R.id.post_reset_enter_box);
        posID.setText(String.valueOf(current));

        int finalCurrent = current;
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.setComments(finalCurrent, new ArrayList<>());
                db.setLikes(finalCurrent, new HashSet<>());
                db.setViews(finalCurrent, new HashSet<>());
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
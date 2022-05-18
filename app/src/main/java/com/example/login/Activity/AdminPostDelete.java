package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminPostDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post_delete);

        int current = 0;
        UserDAO db = new UserDAOImpl(this);
        Bundle fromCreate = getIntent().getExtras();
        if (fromCreate != null){
            current = getIntent().getIntExtra("postID", 0);
            getIntent().removeExtra("postID");
        }
        Button delete = findViewById(R.id.admin_post_delete_button);
        Button deleteAll = findViewById(R.id.admin_delete_all_posts_button);
        EditText posID = findViewById(R.id.admin_postid_delete);
        posID.setText(String.valueOf(current));


        int finalCurrent = current;

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deletePost(finalCurrent);
                setResult(RESULT_OK);
                finish();
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.truncatePosts();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
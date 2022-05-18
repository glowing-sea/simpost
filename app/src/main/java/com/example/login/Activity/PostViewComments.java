package com.example.login.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.Comment;
import com.example.login.Database.HelperMethods;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class PostViewComments extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view_comments);
        setTitle("Comments");

        int id = getIntent().getIntExtra("postID", 0);
        UserDAO db = new UserDAOImpl(getApplicationContext());
        Post p = db.getPost(id);
        ArrayList<Comment> comments = p.getComments();

        // Get instance of me
        Me me = Me.getInstance();

        // Set up recycler view
        RecyclerView recyclerView = findViewById(R.id.comment_list);
        CommentAdapter commentAdapter;
        FloatingActionButton addNewMessageButton;
        commentAdapter = new CommentAdapter(PostViewComments.this, this, comments);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));

        // Link text
        Button submit = findViewById(R.id.submit_comment);
        Button back = findViewById(R.id.back_to_post);
        EditText enterComment = findViewById(R.id.add_comment_textbox);

        // Set buttons
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String writer = me.username;
                String content = enterComment.getText().toString();
                String date = HelperMethods.getDateTime();
                Comment c = new Comment(writer, content, date);
                p.addComments(c);
                setResult(RESULT_OK);
                recreate();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
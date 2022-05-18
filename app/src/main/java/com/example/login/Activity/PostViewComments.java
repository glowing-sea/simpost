package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Message;
import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.Comment;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class PostViewComments extends AppCompatActivity {

    ArrayList<Comment> comments;
    RecyclerView recyclerView;
    PostCommentsAdapter postCommentsAdapter;
    UserDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view_comments);

        int id = getIntent().getIntExtra("postID", 0);
        db = new UserDAOImpl(getApplicationContext());
        Post p = db.getPost(id);





        comments = new ArrayList<>();

        comments.add(new Comment("a", "b","sfjsjdkfksldjfkljsdklf"));
        comments.add(new Comment("a", "b","sfjsjdkfksldjfkljsdklf"));
        comments.add(new Comment("a", "b","sfjsjdkfksldjfkljsdklf"));
        comments.add(new Comment("a", "b","sfjsjdkfksldjfkljsdklf"));
        comments.add(new Comment("a", "b","sfjsjdkfksldjfkljsdklf"));

        ArrayList<Message> ms = new ArrayList<>();
        ms.add(new Message("dd","ddd","dfdfdf",true, "dfdfd"));
        ms.add(new Message("dd","ddd","dfdfdf",true, "dfdfd"));
        ms.add(new Message("dd","ddd","dfdfdf",true, "dfdfd"));
        ms.add(new Message("dd","ddd","dfdfdf",true, "dfdfd"));
        ms.add(new Message("dd","ddd","dfdfdf",true, "dfdfd"));


        recyclerView = findViewById(R.id.comment_list);
        // UserDAO db;
        // HashSet<String> contacts;
        CommentAdapter commentAdapter;
        FloatingActionButton addNewMessageButton;
        // Me me = Me.getInstance();

        // addNewMessageButton = findViewById(R.id.addNewMessageButton);

        commentAdapter = new CommentAdapter(PostViewComments.this, this, ms);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
    }
}
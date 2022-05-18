package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Message;
import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.Comment;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class PostViewComments extends AppCompatActivity {

    ArrayList<Comment> comments;
    RecyclerView recyclerView;
    MessagesChatAdapter postCommentsAdapter;
    UserDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view_comments);

        int id = getIntent().getIntExtra("postID", 0);
        db = new UserDAOImpl(getApplicationContext());
        Post p = db.getPost(id);
        comments = p.getComments();


        comments = p.getComments();

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




        recyclerView.findViewById(R.id.comment_list);
        postCommentsAdapter = new MessagesChatAdapter(PostViewComments.this, this,ms);
        recyclerView.setAdapter(postCommentsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
    }
}
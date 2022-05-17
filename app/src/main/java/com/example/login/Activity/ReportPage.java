package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.login.DataContainer.Post;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

import java.util.Iterator;
import java.util.Set;

public class ReportPage extends AppCompatActivity {
    TextView postCount;
    TextView likeCount;
    TextView viewCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_activiry);

        postCount = findViewById(R.id.textView_report_postCount);
        likeCount = findViewById(R.id.textView_report_likesCount);
        viewCount = findViewById(R.id.textView_report_totalViewCount);

        int numberOfPost = 0;
        int numberOfLikes = 0;
        int numberOfDislikes=0;
        int numberOfViews = 0;

        Intent from = getIntent();
        String userNmae = from.getExtras().getString("userName");
        Log.i("reportPage","current user is "+userNmae);
        UserDAOImpl userDAO = new UserDAOImpl(getApplicationContext());
        Set<Post> allPost = userDAO.postAuthorMatch(userNmae);

        if (allPost != null) {
            numberOfPost = allPost.size();
            Iterator iterator = allPost.iterator();
            while (iterator.hasNext()){
                Post post = (Post) iterator.next();
                numberOfLikes =numberOfLikes + post.getLikes().size();
                numberOfViews = numberOfViews + post.getViews().size();

            }
        }
        likeCount.setText(Integer.toString(numberOfLikes));
        postCount.setText(Integer.toString(numberOfPost) );
        viewCount.setText(Integer.toString(numberOfViews));
    }
}
package com.example.login.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Comment;
import com.example.login.R;

import java.util.ArrayList;

public class PostCommentsAdapter extends RecyclerView.Adapter<PostCommentsAdapter.PostCommentsViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList<Comment> comments;

    PostCommentsAdapter(Activity activity, Context context, ArrayList<Comment> comments){
        this.activity = activity;
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public PostCommentsAdapter.PostCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_comments, parent, false);
        return new PostCommentsAdapter.PostCommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCommentsAdapter.PostCommentsViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.writer.setText(comment.username);
        holder.context.setText(comment.content);
        holder.date.setText(comment.date);
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class PostCommentsViewHolder extends RecyclerView.ViewHolder{

        TextView writer,context, date;
        ConstraintLayout commentRow;

        public PostCommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            writer = (TextView) itemView.findViewById(R.id.comment_writer);
            context = (TextView) itemView.findViewById(R.id.comment_content);
            date = (TextView) itemView.findViewById(R.id.comment_date);
            commentRow = itemView.findViewById(R.id.comment_box_row);
        }
    }
}
package com.example.login.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Comment;
import com.example.login.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList<Comment> comments;

    CommentAdapter(Activity activity, Context context, ArrayList<Comment> comments){
        this.activity = activity;
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_comments, parent, false);
        return new CommentAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.username.setText(comment.username);
        holder.content.setText(comment.content);
        holder.date.setText(comment.date);
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        TextView content;
        TextView date;
        ConstraintLayout commentBox;

        public
        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.comment_writer);
            content = (TextView)  itemView.findViewById(R.id.comment_content);
            date = (TextView)  itemView.findViewById(R.id.comment_date);
            commentBox = (ConstraintLayout) itemView.findViewById(R.id.comment_box);
        }
    }
}
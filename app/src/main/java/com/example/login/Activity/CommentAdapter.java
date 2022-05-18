package com.example.login.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Message;
import com.example.login.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList<Message> users;

    CommentAdapter(Activity activity, Context context, ArrayList users){
        this.activity = activity;
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_comments, parent, false);
        return new CommentAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        Message user = users.get(position);
        holder.username.setText(user.getSender());
        holder.content.setText(user.getContent());
        holder.date.setText(user.getDate());

    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        TextView content;
        TextView date;

        public
        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.comment_writer);
            content = (TextView)  itemView.findViewById(R.id.comment_content);
            date = (TextView)  itemView.findViewById(R.id.comment_date);
        }
    }
}
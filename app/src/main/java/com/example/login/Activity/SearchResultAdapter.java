package com.example.login.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.PostOld;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ResultHolder> {
    private final Context ctx;
    private final List<Post> dataset;
    UserDAO db;
    public SearchResultAdapter(Context ctx, List<Post> dataset){
        this.ctx = ctx;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_view_result, parent, false);
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        Integer current = dataset.get(position).postID;
        Post post = dataset.get(position);
        holder.getPoster().setText(post.getPoster());
        holder.getTitle().setText(post.getTitle());
        holder.getSingleResult().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, ViewPost.class);
                i.putExtra("POST", current);
                ctx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public class ResultHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView poster;
        private final ConstraintLayout singleResult;
        public ResultHolder(@NonNull View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.result_title);
            poster = (TextView) itemView.findViewById(R.id.result_poster);
            singleResult = (ConstraintLayout) itemView.findViewById(R.id.single_result);
        }
        public TextView getTitle(){return this.title;}
        public TextView getPoster(){return this.poster;}
        public ConstraintLayout getSingleResult(){return this.singleResult;}
    }
}

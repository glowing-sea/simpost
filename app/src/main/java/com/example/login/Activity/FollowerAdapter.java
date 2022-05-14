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

import com.example.login.DataContainer.UserAdmin;
import com.example.login.R;

import java.util.List;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder> {

    private final Context ctx;
    private final List<UserAdmin> dataset;
    public FollowerAdapter(Context ctx, List<UserAdmin> dataset){
        this.ctx = ctx;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public FollowerAdapter.FollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.follower_view, parent, false);
        return new FollowerAdapter.FollowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerAdapter.FollowerViewHolder holder, int position) {
        UserAdmin current = dataset.get(position);

        holder.getFollower().setText(current.getUsername());


        holder.getSingleFollower().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(ctx, "Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ctx, HomePage.class);
                intent.putExtra("USER", current);
                // intent.putExtra("username", String.valueOf(users.get(position)));
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class FollowerViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout singleFollower;
        private final TextView follower;


        public FollowerViewHolder(@NonNull View itemView) {
            super(itemView);
            follower = itemView.findViewById(R.id.follower_name);
            singleFollower = itemView.findViewById(R.id.single_Follower);
        }

        public TextView getFollower() {
            return follower;
        }
        public ConstraintLayout getSingleFollower(){
            return this.singleFollower;
        }
    }

}

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

public class SubscribeAdapter extends RecyclerView.Adapter<SubscribeAdapter.SubscribeViewHolder> {

    private final Context ctx;
    private final List<UserAdmin> dataset;
    public SubscribeAdapter(Context ctx, List<UserAdmin> dataset){
        this.ctx = ctx;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public SubscribeAdapter.SubscribeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.follower_view, parent, false);
        return new SubscribeAdapter.SubscribeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribeAdapter.SubscribeViewHolder holder, int position) {
        UserAdmin current = dataset.get(position);

        holder.getSubscribeName().setText(current.getUsername());


        holder.getSingleSubscribe().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(ctx, "Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ctx, Home.class);
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

    public class SubscribeViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout singleSubscribe;
        private final TextView subscribeName;


        public SubscribeViewHolder(@NonNull View itemView) {
            super(itemView);
            subscribeName = itemView.findViewById(R.id.follower_name);
            singleSubscribe = itemView.findViewById(R.id.single_Follower);
        }

        public TextView getSubscribeName() {
            return subscribeName;
        }
        public ConstraintLayout getSingleSubscribe(){
            return this.singleSubscribe;
        }
    }

}


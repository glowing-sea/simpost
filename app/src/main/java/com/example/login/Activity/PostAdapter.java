package com.example.login.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.login.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final Context ctx;
    private final List<Post> dataset;
    public PostAdapter(Context ctx, List<Post> dataset){
        this.ctx = ctx;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_post_preview, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        int max =100;
        int min =0;
        int id = (int) (Math.random()*((max-min) + 1) + min);

        holder.getPostUser().setText(dataset.get(position).getPoster());
        holder.getPostContent().setText(dataset.get(position).getContent());

        Glide.with(ctx).load("https://picsum.photos/id/" + id + "/300/200").apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true))
                .into(holder.getPostImage());

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        private final ImageView postImage;
        private final TextView postUser;
        private final TextView postContent;

        public PostViewHolder(@NonNull View itemView){
            super(itemView);
            postImage = (ImageView) itemView.findViewById(R.id.post_image);
            postUser = (TextView) itemView.findViewById(R.id.post_user);
            postContent = (TextView) itemView.findViewById(R.id.post_content);

        }

        public ImageView getPostImage() { return postImage; }
        public TextView getPostUser() { return postUser; }
        public TextView getPostContent() { return postContent; }

    }

}

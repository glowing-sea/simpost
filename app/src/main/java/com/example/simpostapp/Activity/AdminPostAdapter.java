package com.example.simpostapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpostapp.DataContainer.PostPreview;
import com.example.simpostapp.R;

import java.util.ArrayList;

public class AdminPostAdapter extends RecyclerView.Adapter<AdminPostAdapter.AdminPostViewHolder> {

    private final Context ctx;
    private final ArrayList<PostPreview> dataset;
    Activity activity;
    public AdminPostAdapter(Activity activity, Context ctx, ArrayList<PostPreview> dataset){
        this.ctx = ctx;
        this.dataset = dataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdminPostAdapter.AdminPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_post, parent, false);
        return new AdminPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPostAdapter.AdminPostViewHolder holder, int position) {
        PostPreview current = dataset.get(position);

        String title = current.title;
        if (title.length() > 15) {
            title = title.substring(0, 12);
            title = title + "...";
        }
        String content = current.content;
        if (content.length() > 43) {
            content = content.substring(0, 40);
            content = content + "...";
        }

        holder.getPostTitle().setText(title);
        holder.getPostContent().setText(content);
        holder.getPostCreator().setText(current.creator);
        holder.getPostTag().setText(current.getTag());


        if (current.image != null){
            holder.getPostImage().setImageBitmap(current.image);
        }


        holder.getSinglePost().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(ctx, AdminPostDelete.class);
                intent.putExtra("postID", current.postID);
                activity.startActivityForResult(intent, 100);
                return false;
            }
        });

        holder.getSinglePost().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, AdminPostReset.class);
                intent.putExtra("postID", current.postID);
                activity.startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class AdminPostViewHolder extends RecyclerView.ViewHolder{
        private final ImageView postImage;
        private final TextView postTitle, postContent, postTag, postCreator;
        private final ConstraintLayout singlePost;

        public AdminPostViewHolder(@NonNull View itemView){
            super(itemView);
            postImage = (ImageView) itemView.findViewById(R.id.post_image);
            postTitle = (TextView) itemView.findViewById(R.id.post_title);
            postContent = (TextView) itemView.findViewById(R.id.post_content);
            postTag = (TextView) itemView.findViewById(R.id.post_tag);
            postCreator = (TextView) itemView.findViewById(R.id.post_creator);
            singlePost = (ConstraintLayout) itemView.findViewById(R.id.single_post);
        }

        public ImageView getPostImage() {
            return postImage;
        }

        public TextView getPostTitle() {
            return postTitle;
        }

        public TextView getPostContent() {
            return postContent;
        }

        public TextView getPostTag() {
            return postTag;
        }

        public TextView getPostCreator() {
            return postCreator;
        }

        public ConstraintLayout getSinglePost() {
            return singlePost;
        }
    }

}

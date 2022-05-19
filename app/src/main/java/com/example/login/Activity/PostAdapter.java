package com.example.login.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.PostPreview;
import com.example.login.Database.HelperMethods;
import com.example.login.R;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final Context ctx;
    private final ArrayList<PostPreview> dataset;
    public PostAdapter(Context ctx, ArrayList<PostPreview> dataset){
        this.ctx = ctx;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        int max =100;
        int min =0;
        int id = (int) (Math.random()*((max-min) + 1) + min);
        PostPreview current = dataset.get(position);

        String title = current.title;
        String content = current.content;

        Me me = Me.getInstance();
        if (me.getPrivacy().get(5)){
            title = (HelperMethods.getCensored(title));
            content = (HelperMethods.getCensored(content)); }


        if (title.length() > 15) {
            title = title.substring(0, 12);
            title = title + "...";
        }
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
//        if (current.image1 == null && current.image2 == null && current.image3 == null){
//            Glide.with(ctx).load("https://picsum.photos/id/" + id + "/300/200").apply(new RequestOptions()
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true))
//                    .into(holder.getPostImage());
//        }
//        else if (current.image1 != null){
//            holder.getPostImage().setImageBitmap(current.image1);
//        }
//        else if (current.image2 != null){
//            holder.getPostImage().setImageBitmap(current.image2);
//        }
//        else if (current.image3 != null){
//            holder.getPostImage().setImageBitmap(current.image3);
//        }


        holder.getSinglePost().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(ctx, "Clicked", Toast.LENGTH_LONG).show();
                 Intent intent = new Intent(ctx, PostDetail.class);
                 intent.putExtra("postID", current.postID);
                 // intent.putExtra("username", String.valueOf(users.get(position)));
                 ctx.startActivity(intent);

//                Intent intent = new Intent(ctx,PostView.class);
//                intent.putExtra("POST", current);
//                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        private final ImageView postImage;
        private final TextView postTitle, postContent, postTag, postCreator;
        private final ConstraintLayout singlePost;

        public PostViewHolder(@NonNull View itemView){
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

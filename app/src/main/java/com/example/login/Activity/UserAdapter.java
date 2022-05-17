package com.example.login.Activity;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.UserAdmin;
import com.example.login.DataContainer.UserPreview;
import com.example.login.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList<UserPreview> usersPreview;
    private ArrayList<UserAdmin> usersAdmin;


    UserAdapter(Activity activity, Context context,  ArrayList<UserPreview> usersPreview, ArrayList<UserAdmin> usersAdmin){
        this.activity = activity;
        this.context = context;
        this.usersPreview = usersPreview;
        this.usersAdmin = usersAdmin;
    }


    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_user, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        String extra = null;

        if (usersAdmin == null) {
            UserPreview userPreview = usersPreview.get(position);
            holder.username.setText(userPreview.getUsername());
            holder.something.setText(userPreview.getSignature());
            holder.somethingText.setText(context.getText(R.string.signature));
            extra = userPreview.getUsername();
        }
        if (usersPreview == null) {
            UserAdmin userAdmin = usersAdmin.get(position);
            holder.username.setText(userAdmin.getUsername());
            holder.something.setText(userAdmin.getPassword());
            holder.somethingText.setText(context.getText(R.string.password));
            extra = userAdmin.getUsername();
        }


        String finalExtra = extra;
        holder.adminUserRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeSomeone.class);
                intent.putExtra("USER", finalExtra);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersAdmin == null ? usersPreview.size() : usersAdmin.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        TextView username, something, somethingText ;
        LinearLayout adminUserRow;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.preview_username);
            something = (TextView) itemView.findViewById(R.id.preview_something);
            somethingText = (TextView) itemView.findViewById(R.id.preview_something_text);
            adminUserRow = itemView.findViewById(R.id.user_preview_row);
        }
    }
}

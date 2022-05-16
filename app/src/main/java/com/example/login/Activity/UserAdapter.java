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
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.UserOld;
import com.example.login.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList<UserOld> users;

    UserAdapter(Activity activity, Context context, ArrayList users){
        this.activity = activity;
        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_user, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserOld user = users.get(position);
        holder.username.setText(user.getUsername());
        holder.password.setText(user.getPassword());
        holder.adminUserRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
                 Intent intent = new Intent(context, AdminUpdateUserPage.class);
                 intent.putExtra("USER", user);
                 // intent.putExtra("username", String.valueOf(users.get(position)));
                 activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        TextView username, password;
        LinearLayout adminUserRow;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.admin_username);
            password = (TextView) itemView.findViewById(R.id.admin_password);
            adminUserRow = itemView.findViewById(R.id.admin_user_row);
        }
    }
}

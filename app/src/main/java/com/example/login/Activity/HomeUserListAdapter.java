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

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.UserPreview;
import com.example.login.Database.HelperMethods;
import com.example.login.R;

import java.util.ArrayList;

public class HomeUserListAdapter extends RecyclerView.Adapter<HomeUserListAdapter.UserViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList<UserPreview> usersPreview;


    HomeUserListAdapter(Activity activity, Context context, ArrayList<UserPreview> usersPreview){
        this.activity = activity;
        this.context = context;
        this.usersPreview = usersPreview;
    }


    @NonNull
    @Override
    public HomeUserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_user, parent, false);
        return new HomeUserListAdapter.UserViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        String extra = null;

        UserPreview userPreview = usersPreview.get(position);

        Me me = Me.getInstance();

        String sig = userPreview.getSignature();
        if (me.getPrivacy().get(5))
            sig = (HelperMethods.getCensored(sig));

        holder.username.setText(userPreview.getUsername());
        holder.something.setText(sig);
        holder.somethingText.setText(context.getText(R.string.signature));
        extra = userPreview.getUsername();

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
        return usersPreview.size();
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

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

import com.example.login.R;

import java.util.ArrayList;

public class MessagesChatAdapter extends RecyclerView.Adapter<MessagesChatAdapter.MessageChatViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList<String> users;

    MessagesChatAdapter(Activity activity, Context context, ArrayList users){
        this.activity = activity;
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MessagesChatAdapter.MessageChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_messages_chat, parent, false);
        return new MessagesChatAdapter.MessageChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesChatAdapter.MessageChatViewHolder holder, int position) {
        String user = users.get(position);
        holder.username.setText(user);
        holder.messageRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MessagesAddPage.class);
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

    public class MessageChatViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        LinearLayout messageRow;

        public MessageChatViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.message_adapter_user_name);
            messageRow = itemView.findViewById(R.id.message_row);
        }
    }
}

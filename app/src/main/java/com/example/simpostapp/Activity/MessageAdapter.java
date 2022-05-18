package com.example.simpostapp.Activity;

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

import com.example.simpostapp.DataContainer.Message;
import com.example.simpostapp.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList<Message> users;

    MessageAdapter(Activity activity, Context context, ArrayList users){
        this.activity = activity;
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_messages, parent, false);
        return new MessageAdapter.MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Message user = users.get(position);
        holder.username.setText(user.getSender());
        holder.messageRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessagesChat.class);
                intent.putExtra("NAME", user.getSender());
                // intent.putExtra("username", String.valueOf(users.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        LinearLayout messageRow;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.message_adapter_user_name);
            messageRow = itemView.findViewById(R.id.message_row);
        }
    }
}
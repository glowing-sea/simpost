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

import com.example.login.DataContainer.ChatBox;
import com.example.login.R;

import java.util.ArrayList;

public class MessagesChatAdapter extends RecyclerView.Adapter<MessagesChatAdapter.MessageChatViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList<ChatBox> messages;

    MessagesChatAdapter(Activity activity, Context context, ArrayList message){
        this.activity = activity;
        this.context = context;
        this.messages = message;
    }

    @NonNull
    @Override
    public MessagesChatAdapter.MessageChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_messages_chat, parent, false);
        return new MessagesChatAdapter.MessageChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesChatAdapter.MessageChatViewHolder holder, int position) {
        ChatBox chatBox = messages.get(position);
        holder.username.setText(chatBox.getUsername());
        holder.context.setText(chatBox.getmessage());

    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageChatViewHolder extends RecyclerView.ViewHolder{

        TextView username,context;
        LinearLayout messageRow;

        public MessageChatViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.chat_box_username);
            context = (TextView) itemView.findViewById(R.id.chat_box_content);
            messageRow = itemView.findViewById(R.id.chat_box_row);
        }
    }
}

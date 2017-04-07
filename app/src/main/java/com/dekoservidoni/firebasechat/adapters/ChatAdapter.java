package com.dekoservidoni.firebasechat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dekoservidoni.firebasechat.R;
import com.dekoservidoni.firebasechat.models.ChatData;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible to show all the messages
 * in the chat
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    /**
     * ViewHolder to be the item of the list
     */
    static final class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView message;

        ChatViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_username);
            message = (TextView) view.findViewById(R.id.item_message);
        }
    }

    private List<ChatData> mContent = new ArrayList<>();

    public void clearData() {
        mContent.clear();
    }

    public void addData(ChatData data) {
        mContent.add(data);
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, parent, false);
        return new ChatViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatData data = mContent.get(position);

        holder.message.setText(data.getMessage());
        holder.name.setText(data.getName());
    }
}

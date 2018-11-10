package com.mdlink.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdlink.R;
import com.mdlink.chat.MessageActivity;
import com.twilio.chat.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private Context context;
    private ChatAdapter.onItemClickListener listener;
    private String mType;
    private List<MessageActivity.MessageItem> mStringList;

    public ChatAdapter(Context context,
                              ArrayList<MessageActivity.MessageItem> mStringList,
                              String mType,
                       ChatAdapter.onItemClickListener listener) {
        this.context = context;
        this.mStringList = mStringList;
        this.mType = mType;
        this.listener = listener;
    }

    public void updateData(ArrayList<MessageActivity.MessageItem> dataset) {
        mStringList.clear();
        mStringList.addAll(dataset);
        notifyDataSetChanged();
    }

    public interface onItemClickListener {
        void onItemClick(String String);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvItemNameTL);
        }
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_treatmentlist, parent, false);
        return new ChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.MyViewHolder holder, final int position) {
        Message val = mStringList.get(position).getMessage();
        holder.tvName.setText(val.getMessageBody());
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }
}


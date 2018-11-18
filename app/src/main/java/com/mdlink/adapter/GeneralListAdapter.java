package com.mdlink.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mdlink.R;
import java.util.ArrayList;
import java.util.List;

public class GeneralListAdapter extends RecyclerView.Adapter<GeneralListAdapter.MyViewHolder> {
    private Context context;
    private GeneralListAdapter.onItemClickListener listener;
    private String mType;
    private List<String> mStringList;

    public GeneralListAdapter(Context context,
                                  ArrayList<String> mStringList,
                                  String mType,
                                  GeneralListAdapter.onItemClickListener listener) {
        this.context = context;
        this.mStringList = mStringList;
        this.mType = mType;
        this.listener = listener;
    }

    public void updateData(ArrayList<String> dataset) {
        mStringList.clear();
        mStringList.addAll(dataset);
        notifyDataSetChanged();
    }

    public interface onItemClickListener {
        void onItemClick(String String);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox tvName;
        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.checkboxLabTest);
        }
    }

    @Override
    public GeneralListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_treatmentlist, parent, false);
        return new GeneralListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GeneralListAdapter.MyViewHolder holder, final int position) {
        String val = mStringList.get(position);
        holder.tvName.setText(val);
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }
}

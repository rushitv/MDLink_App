package com.mdlinkhealth.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.mdlinkhealth.R;

import java.util.ArrayList;
import java.util.List;

public class CheckboxAdapter  extends RecyclerView.Adapter<CheckboxAdapter.MyViewHolder> {
    private Context context;
    private String mType;
    private List<String> mItem;
    @NonNull
    private OnItemCheckListener onItemCheckListener;

    public interface OnItemCheckListener {
        void onItemCheck(String item);
        void onItemUncheck(String item);
    }

    public CheckboxAdapter(Context context,
                           List<String> mItem,
                           @NonNull OnItemCheckListener onItemCheckListener) {
        this.context = context;
        this.mItem = mItem;
        this.onItemCheckListener = onItemCheckListener;
    }

    public void updateData(ArrayList<String> dataset) {
        mItem.clear();
        mItem.addAll(dataset);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public CheckBox checkBoxLabTest;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBoxLabTest = itemView.findViewById(R.id.checkboxLabTest);
            checkBoxLabTest.setClickable(false);

        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            checkBoxLabTest.setChecked(true);
            itemView.setOnClickListener(onClickListener);
        }
    }
    @Override
    public CheckboxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_treatmentlist, parent, false);
        return new CheckboxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CheckboxAdapter.MyViewHolder holder, final int position) {
        mItem.get(position);
        holder.checkBoxLabTest.setText(mItem.get(position));
        holder.checkBoxLabTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                String currentItem = mItem.get(pos);
                if(holder.checkBoxLabTest.isChecked()){
                    onItemCheckListener.onItemCheck(currentItem);
                    holder.checkBoxLabTest.setChecked(true);
                }else{
                    onItemCheckListener.onItemUncheck(currentItem);
                    holder.checkBoxLabTest.setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }
}

package com.mdlink;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdlink.model.AppointmentListResponseDetails;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> {
    private Context context;
    private onItemClickListener listener;
    private String mType;
    private List<AppointmentListResponseDetails> mAppointmentListResponseDetailsList;

    public AppointmentListAdapter(Context context,
                                  ArrayList<AppointmentListResponseDetails> mAppointmentListResponseDetailsList,
                                  String mType,
                                  onItemClickListener listener) {
        this.context = context;
        this.mAppointmentListResponseDetailsList = mAppointmentListResponseDetailsList;
        this.mType = mType;
        this.listener = listener;
    }

    public void updateData(ArrayList<AppointmentListResponseDetails> dataset) {
        mAppointmentListResponseDetailsList.clear();
        mAppointmentListResponseDetailsList.addAll(dataset);
        notifyDataSetChanged();
    }

    public interface onItemClickListener {
        void onItemClick(AppointmentListResponseDetails appointmentListResponseDetails);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvReason, tvDate, tvTime, tvType, tvPaymentStatus;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.txtNameSAL);
            tvReason = view.findViewById(R.id.txtReasonSAL);
            tvDate = view.findViewById(R.id.txtDateSAL);
            tvTime = view.findViewById(R.id.txtTimeSAL);
            tvType = view.findViewById(R.id.txtTypeSAL);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AppointmentListResponseDetails appointmentListResponseDetails = mAppointmentListResponseDetailsList.get(position);
        holder.tvName.setText(appointmentListResponseDetails.getName());
        holder.tvReason.setText(appointmentListResponseDetails.getVisitPurpose());
        holder.tvDate.setText(appointmentListResponseDetails.getScheduledDate());
        holder.tvTime.setText(appointmentListResponseDetails.getScheduledTime());
    }

    @Override
    public int getItemCount() {
        return mAppointmentListResponseDetailsList.size();
    }
}

package com.mdlink.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdlink.R;
import com.mdlink.model.AppointmentListResponseDetails;
import com.mdlink.util.Constants;

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

        public RelativeLayout rlViewPatientFile;
        public TextView tvName, tvReason, tvDate, tvTime, tvType,txtLabelStatus, tvPaymentStatus, txtViewPatientProfileSAL;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.txtNameSAL);
            tvReason = view.findViewById(R.id.txtReasonSAL);
            tvDate = view.findViewById(R.id.txtDateSAL);
            tvTime = view.findViewById(R.id.txtTimeSAL);
            tvType = view.findViewById(R.id.txtTypeSAL);
            txtLabelStatus = view.findViewById(R.id.txtLabelStatusRSAL);
            tvPaymentStatus = view.findViewById(R.id.txtPaymentStatusRSAS);
            rlViewPatientFile = view.findViewById(R.id.rlViewPatientFile);
            txtViewPatientProfileSAL = view.findViewById(R.id.txtViewPatientProfileSAL);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        AppointmentListResponseDetails appointmentListResponseDetails = mAppointmentListResponseDetailsList.get(position);
        holder.tvName.setText(context.getString(R.string.name, appointmentListResponseDetails.getName()));
        holder.tvReason.setText(context.getString(R.string.reason, appointmentListResponseDetails.getVisitPurpose()));
        holder.tvDate.setText(context.getString(R.string.date,appointmentListResponseDetails.getScheduledDate()));
        holder.tvTime.setText(context.getString(R.string.time,appointmentListResponseDetails.getScheduledTime()));

        if(mType.equalsIgnoreCase("1")){ // 1 = doctor // 2 = patient
            holder.rlViewPatientFile.setVisibility(View.VISIBLE);
            holder.txtViewPatientProfileSAL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(mAppointmentListResponseDetailsList.get(position));
                }
            });
        }else {
            holder.rlViewPatientFile.setVisibility(View.GONE);
        }

        String type="";
        if(appointmentListResponseDetails.getType()==1){
            type="Audio Call";
        }else if(appointmentListResponseDetails.getType()==2){
            type="Instant Message";
        }else if(appointmentListResponseDetails.getType()==3){
            type="Video Call";
        }
        holder.tvType.setText(context.getString(R.string.type,type));
        holder.txtLabelStatus.setText(context.getString(R.string.payment_status,""));


        // Wrap the drawable so that future tinting calls work
        // on pre-v21 devices. Always use the returned drawable.
        Drawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(context.getDrawable(R.drawable.corner_low));
        }

        if(appointmentListResponseDetails.getIsPayed() == 1){
            holder.tvPaymentStatus.setText(context.getString(R.string.label_paid));
            DrawableCompat.setTint(drawable,context.getResources().getColor(R.color.colorGreenCard));
            holder.tvPaymentStatus.setBackground(drawable);
        }else {
            holder.tvPaymentStatus.setText(context.getString(R.string.label_pending));
            DrawableCompat.setTint(drawable,context.getResources().getColor(R.color.colorOrange100));
            holder.tvPaymentStatus.setBackground(drawable);
        }

    }

    @Override
    public int getItemCount() {
        return mAppointmentListResponseDetailsList.size();
    }
}

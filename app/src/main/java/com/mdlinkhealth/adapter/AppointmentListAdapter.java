package com.mdlinkhealth.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdlinkhealth.R;
import com.mdlinkhealth.model.AppointmentListResponseDetails;
import com.mdlinkhealth.util.Constants;

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

    public void addAll(List<AppointmentListResponseDetails> mAppointmentListResponseDetailsList) {
        if (mAppointmentListResponseDetailsList == null) {
            this.mAppointmentListResponseDetailsList.clear();
            notifyDataSetChanged();
            return;
        }
        this.mAppointmentListResponseDetailsList.addAll(mAppointmentListResponseDetailsList);
        notifyItemRangeInserted(this.mAppointmentListResponseDetailsList.size() - mAppointmentListResponseDetailsList.size(), mAppointmentListResponseDetailsList.size());
    }

    /**
     * Clears all the items in the adapter.
     */
    public void clear() {
        mAppointmentListResponseDetailsList.clear();
        notifyDataSetChanged();
    }

    public void update(AppointmentListResponseDetails item) {
        int position = mAppointmentListResponseDetailsList.indexOf(item);
        if (position > -1) {
            mAppointmentListResponseDetailsList.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void remove(AppointmentListResponseDetails item) {
        int position = mAppointmentListResponseDetailsList.indexOf(item);
        if (position > -1) {
            mAppointmentListResponseDetailsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public interface onItemClickListener {
        void onItemClick(String type, AppointmentListResponseDetails appointmentListResponseDetails);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout rlViewPatientFile;
        public TextView tvName, tvReason, tvDate, tvTime, tvType, tvJoin, txtCancelSAL,
                txtLabelStatus, tvPaymentStatus, txtViewPatientProfileSAL;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.txtNameSAL);
            tvReason = view.findViewById(R.id.txtReasonSAL);
            tvDate = view.findViewById(R.id.txtDateSAL);
            tvTime = view.findViewById(R.id.txtTimeSAL);
            tvType = view.findViewById(R.id.txtTypeSAL);
            tvJoin = view.findViewById(R.id.txtJoinCallSAL);
            txtCancelSAL = view.findViewById(R.id.txtCancelSAL);
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AppointmentListResponseDetails appointmentListResponseDetails = mAppointmentListResponseDetailsList.get(position);

        Log.i("Adapter", "Name>>>>" + appointmentListResponseDetails.getName());

        holder.tvName.setText(context.getString(R.string.name, appointmentListResponseDetails.getName()));
        holder.tvReason.setText(context.getString(R.string.reason, appointmentListResponseDetails.getVisitPurpose()));
        holder.tvDate.setText(context.getString(R.string.date, appointmentListResponseDetails.getScheduledDate()));
        holder.tvTime.setText(context.getString(R.string.time, appointmentListResponseDetails.getScheduledTime()));

        if (mType.equalsIgnoreCase("1")) { // 1 = doctor // 2 = patient
            holder.rlViewPatientFile.setVisibility(View.VISIBLE);
            holder.txtViewPatientProfileSAL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(Constants.PATIENT_FILE, mAppointmentListResponseDetailsList.get(position));
                }
            });

            if (appointmentListResponseDetails.getStatusId() == 0 && appointmentListResponseDetails.getIsCompleted() == 0) {
                holder.tvJoin.setText(Constants.APPROVE);
                holder.tvJoin.setTag(Constants.APPROVE);
            } else if (appointmentListResponseDetails.getStatusId() == 1) {
                holder.tvJoin.setText(Constants.JOIN);
                holder.tvJoin.setTag(Constants.JOIN);
            }

            if (appointmentListResponseDetails.getIsCompleted() == 1) {
                holder.txtCancelSAL.setVisibility(View.GONE);
                holder.tvJoin.setText("Consultation Completed");
            } else {
                holder.txtCancelSAL.setVisibility(View.VISIBLE);
            }
        } else {
            holder.rlViewPatientFile.setVisibility(View.GONE);
            holder.tvJoin.setText(Constants.JOIN);
            holder.tvJoin.setTag(Constants.JOIN);
        }

        String type = "";
        if (appointmentListResponseDetails.getType() == 1) {
            type = "Audio Call";
        } else if (appointmentListResponseDetails.getType() == 2) {
            type = "Instant Message";
        } else if (appointmentListResponseDetails.getType() == 3) {
            type = "Video Call";
        }
        holder.tvType.setText(context.getString(R.string.type, type));
        holder.txtLabelStatus.setText(context.getString(R.string.payment_status, ""));


        // Wrap the drawable so that future tinting calls work
        // on pre-v21 devices. Always use the returned drawable.
        Drawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(context.getDrawable(R.drawable.corner_low));
        }

        if (appointmentListResponseDetails.getIsPayed() == 1) {
            holder.tvPaymentStatus.setText(context.getString(R.string.label_paid));
            DrawableCompat.setTint(drawable, context.getResources().getColor(R.color.colorGreenCard));
            holder.tvPaymentStatus.setBackground(drawable);
        } else {
            holder.tvPaymentStatus.setText(context.getString(R.string.label_pending));
            DrawableCompat.setTint(drawable, context.getResources().getColor(R.color.colorOrange100));
            holder.tvPaymentStatus.setBackground(drawable);
        }

        holder.tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mType.equalsIgnoreCase("1")) {
                    listener.onItemClick("" + holder.tvJoin.getTag(), mAppointmentListResponseDetailsList.get(position));
                }else {
                    listener.onItemClick(Constants.JOIN, mAppointmentListResponseDetailsList.get(position));
                }
            }
        });

        holder.txtCancelSAL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType.equalsIgnoreCase("1")
                        && appointmentListResponseDetails.getStatusId() == 0
                        && appointmentListResponseDetails.getIsCompleted() == 0) {
                    listener.onItemClick(Constants.CANCEL, mAppointmentListResponseDetailsList.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAppointmentListResponseDetailsList.size();
    }
}

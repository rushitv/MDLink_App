package com.mdlink;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.mdlink.adapter.AppointmentListAdapter;
import com.mdlink.model.AppointmentListResponse;
import com.mdlink.model.AppointmentListResponseDetails;
import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;
import com.mdlink.util.MdlinkProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleAppointmentActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    Toolbar toolbar;
    private RecyclerView rvScheduledApptList;
    private AppointmentListAdapter appointmentListAdapter;
    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_appointment_list);
        sharedPreferenceManager = new SharedPreferenceManager(this);
        initToolbar();
        initViews();

        callToGetListScheduledAppointment(sharedPreferenceManager.getStringData(Constants.USER_ID), sharedPreferenceManager.getStringData(Constants.ROLE_ID));
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_scheduledappointment), R.color.colorAccent);
    }
    private void initViews(){
        rvScheduledApptList = findViewById(R.id.rvScheduledAppointmentList);
        ArrayList<AppointmentListResponseDetails> appointmentListResponses = new ArrayList<>();
    }

    private void callToGetListScheduledAppointment(String UserId,final String RoleId) {
        Log.i(TAG, "UserId>>>>>>" +UserId);
        MdlinkProgressBar.setProgressBar(this);
        Call<AppointmentListResponse> getPatientById = App.apiService.getScheduledAppointmentList("3");
        getPatientById.enqueue(new Callback<AppointmentListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AppointmentListResponse> call, Response<AppointmentListResponse> response) {
                Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                if (response.code() == 200) {
                    Log.i(TAG, "2>>>>>>size>>>>>>>" + response.body().getResult().size());
                    bindRVList(response.body().getResult(), RoleId);
                }
                MdlinkProgressBar.hideProgressBar(ScheduleAppointmentActivity.this);
            }

            @Override
            public void onFailure(retrofit2.Call<AppointmentListResponse> call, Throwable t) {
                t.fillInStackTrace();
                MdlinkProgressBar.hideProgressBar(ScheduleAppointmentActivity.this);
            }
        });
    }

    private void bindRVList(ArrayList<AppointmentListResponseDetails> appointmentListResponseDetailsList, String RoleId){
        appointmentListAdapter = new AppointmentListAdapter(this,
                appointmentListResponseDetailsList, RoleId, new AppointmentListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(AppointmentListResponseDetails appointmentListResponseDetails) {

            }
        });
        rvScheduledApptList.setAdapter(appointmentListAdapter);
    }
}

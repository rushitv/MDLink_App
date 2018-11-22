package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.mdlink.adapter.AppointmentListAdapter;
import com.mdlink.chat.ChatClientManager;
import com.mdlink.chat.MainChatActivity;
import com.mdlink.chat.listeners.TaskCompletionListener;
import com.mdlink.model.AppointmentListResponse;
import com.mdlink.model.AppointmentListResponseDetails;
import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;
import com.mdlink.video.VideoActivity;
import com.mdlink.voice.VoiceActivity;

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
    private ChatClientManager clientManager;

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

    private void initViews() {
        rvScheduledApptList = findViewById(R.id.rvScheduledAppointmentList);
    }

    private void callToGetListScheduledAppointment(String UserId, final String RoleId) {
        Log.i(TAG, "UserId>>>>>>" + UserId);
        showProgressDialog();
        Call<AppointmentListResponse> getById;
        if (RoleId.equalsIgnoreCase("1")) {
            getById = App.apiService.getScheduledAppointmentListDoctorSide(sharedPreferenceManager.getStringData(Constants.USER_ID));
        } else {
            getById = App.apiService.getScheduledAppointmentListPatientSide(sharedPreferenceManager.getStringData(Constants.USER_ID));
        }

        getById.enqueue(new Callback<AppointmentListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AppointmentListResponse> call, Response<AppointmentListResponse> response) {
                hideProgressDialog();
                Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                if (response.code() == 200) {
                    Log.i(TAG, "2>>>>>>size>>>>>>>" + response.body().getResult().size());
                    bindRVList(response.body().getResult(), RoleId);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<AppointmentListResponse> call, Throwable t) {
                hideProgressDialog();
                t.fillInStackTrace();
            }
        });
    }

    private void bindRVList(final ArrayList<AppointmentListResponseDetails> appointmentListResponseDetailsList, final String RoleId) {
        appointmentListAdapter = new AppointmentListAdapter(this,
                appointmentListResponseDetailsList, RoleId, new AppointmentListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(final String type, final AppointmentListResponseDetails appointmentListResponseDetails) {
                sharedPreferenceManager.saveString(Constants.APPOINTMENT_ID, "" + appointmentListResponseDetails.getId());
                if(type.equalsIgnoreCase(Constants.PATIENT_FILE)){
                   if(sharedPreferenceManager.getStringData(Constants.ROLE_ID).equalsIgnoreCase("1")){
                       Intent iViewPatientProfile = new Intent(ScheduleAppointmentActivity.this, ViewPatientFileActivity.class);
                       iViewPatientProfile.putExtra(Constants.APPOINTMENT_ID, appointmentListResponseDetails.getId());
                       startActivity(iViewPatientProfile);
                   }
                }else {
                    switch (sharedPreferenceManager.getStringData(Constants.ROLE_ID)) {
                        case "1": // Doctor
                            if (appointmentListResponseDetails.getIsPayed() == 1) {
                                if (appointmentListResponseDetails.getType() == 1) {
                                    // open audio call activity
                                    showMainVoiceActivity(sharedPreferenceManager.getStringData(Constants.ROLE_ID),
                                            "" + appointmentListResponseDetails.getId(),
                                            appointmentListResponseDetails.getName(),
                                            appointmentListResponseDetails.getDocName(),
                                            "" + appointmentListResponseDetails.getUserId());
                                }
                                if (appointmentListResponseDetails.getType() == 2) {
                                    // open chat  activity
                                    showMainChatActivity(sharedPreferenceManager.getStringData(Constants.ROLE_ID),
                                            "" + appointmentListResponseDetails.getId(),
                                            appointmentListResponseDetails.getName(),
                                            appointmentListResponseDetails.getDocName(),
                                            "" + appointmentListResponseDetails.getUserId());

                                }
                                if (appointmentListResponseDetails.getType() == 3) {
                                    // open chat activity
                                    Intent intent = new Intent(ScheduleAppointmentActivity.this, VideoActivity.class);
                                    startActivity(intent);
                                }
                            }
                            break;
                        case "2": // Patient
                            if (appointmentListResponseDetails.getIsPayed() == 1) {

                                if (appointmentListResponseDetails.getType() == 2) {
                                    // open chat call activity
                                    showMainChatActivity(sharedPreferenceManager.getStringData(Constants.ROLE_ID),
                                            "" + appointmentListResponseDetails.getId(),
                                            appointmentListResponseDetails.getName(),
                                            appointmentListResponseDetails.getDocName(),
                                            "" + appointmentListResponseDetails.getUserId());

                                }
                                if (appointmentListResponseDetails.getType() == 3) {
                                    // open video activity
                                }
                            }
                            break;
                    }
                }
            }
        });
        rvScheduledApptList.setAdapter(appointmentListAdapter);
    }

    private void showMainChatActivity(final String RoleId, final String AppointmentId, final String Name, final String DoctorName, final String PatientId) {

        showProgressDialog();
        clientManager = App.getInstance().getChatClientManager();

        clientManager.connectClient(new TaskCompletionListener<Void, String>() {
            @Override
            public void onSuccess(Void aVoid) {
                hideProgressDialog();
                Intent launchIntent = new Intent();
                launchIntent.setClass(getApplicationContext(), MainChatActivity.class);
                launchIntent.putExtra(Constants.ROLE_ID, RoleId);
                launchIntent.putExtra(Constants.NAME, Name);
                launchIntent.putExtra(Constants.DOCTOR_NAME, DoctorName);
                launchIntent.putExtra(Constants.APPOINTMENT_ID, AppointmentId);
                launchIntent.putExtra(Constants.PATIENT_ID, PatientId);
                startActivity(launchIntent);
            }

            @Override
            public void onError(String errorMessage) {
                showProgressDialog();
                Log.i(TAG, ">>>>>>>>>>>>>>" + errorMessage);
                Toast.makeText(ScheduleAppointmentActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void showMainVoiceActivity(String RoleId, String AppointmentId, String Name, String DoctorName, String PatientId) {
        Intent launchIntent = new Intent();
        launchIntent.setClass(getApplicationContext(), VoiceActivity.class);
        launchIntent.putExtra(Constants.ROLE_ID, RoleId);
        launchIntent.putExtra(Constants.NAME, Name);
        launchIntent.putExtra(Constants.DOCTOR_NAME, DoctorName);
        launchIntent.putExtra(Constants.APPOINTMENT_ID, AppointmentId);
        launchIntent.putExtra(Constants.PATIENT_ID, PatientId);
        startActivity(launchIntent);
    }
}

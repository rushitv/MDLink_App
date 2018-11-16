package com.mdlink;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
            getById = App.apiService.getScheduledAppointmentListDoctorSide("7");
        } else {
            getById = App.apiService.getScheduledAppointmentListPatientSide("3");
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
            public void onItemClick(final AppointmentListResponseDetails appointmentListResponseDetails) {
                switch (sharedPreferenceManager.getStringData(Constants.ROLE_ID)) {
                    case "1": // Doctor
                        if (appointmentListResponseDetails.getType() == 1) {
                            // open audio call activity
                            Intent intent = new Intent(ScheduleAppointmentActivity.this, VoiceActivity.class);
                            startActivity(intent);
                        }
                        if (appointmentListResponseDetails.getType() == 2) {
                            // open chat  activity
                            sharedPreferenceManager.saveString(Constants.APPOINTMENT_ID,""+appointmentListResponseDetails.getId());
                            showMainChatActivity(sharedPreferenceManager.getStringData(Constants.ROLE_ID),
                                    ""+appointmentListResponseDetails.getId(),
                                    appointmentListResponseDetails.getName(),
                                    appointmentListResponseDetails.getDocName());

                        }
                        if (appointmentListResponseDetails.getType() == 3) {
                            // open chat activity
                            Intent intent = new Intent(ScheduleAppointmentActivity.this, VideoActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case "2": // Patient
                        if (appointmentListResponseDetails.getIsPayed() == 1) {

                            if (appointmentListResponseDetails.getType() == 2) {
                                // open chat call activity
                                sharedPreferenceManager.saveString(Constants.APPOINTMENT_ID,""+appointmentListResponseDetails.getId());
                                showMainChatActivity(sharedPreferenceManager.getStringData(Constants.ROLE_ID),
                                        ""+appointmentListResponseDetails.getId(),
                                        appointmentListResponseDetails.getName(),
                                        appointmentListResponseDetails.getDocName());

                            }

                            if (appointmentListResponseDetails.getType() == 3) {
                                // open video activity
                                Intent intent = new Intent(ScheduleAppointmentActivity.this, MainChatActivity.class);
                                startActivity(intent);
                            }
                        }
                        break;
                }
            }
        });

        rvScheduledApptList.setAdapter(appointmentListAdapter);
    }


    private void initializeChatClient() {

        clientManager.connectClient(new TaskCompletionListener<Void, String>() {
            @Override
            public void onSuccess(Void aVoid) {
                //showMainChatActivity();
            }

            @Override
            public void onError(String errorMessage) {

                Log.i(TAG, ">>>>>>>>>>>>>>" + errorMessage);
            }
        });
    }


    private void showMainChatActivity(String RoleId,String AppointmentId,String Name, String DoctorName) {
        Intent launchIntent = new Intent();
        launchIntent.setClass(getApplicationContext(), MainChatActivity.class);
        launchIntent.putExtra(Constants.ROLE_ID, RoleId);
        launchIntent.putExtra(Constants.NAME,Name);
        launchIntent.putExtra(Constants.DOCTOR_NAME,DoctorName);
        launchIntent.putExtra(Constants.APPOINTMENT_ID, AppointmentId);
        startActivity(launchIntent);
    }
}

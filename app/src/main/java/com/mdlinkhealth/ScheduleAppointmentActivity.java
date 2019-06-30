package com.mdlinkhealth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mdlinkhealth.adapter.AppointmentListAdapter;
import com.mdlinkhealth.chat.ChatClientManager;
import com.mdlinkhealth.chat.MainChatActivity;
import com.mdlinkhealth.chat.listeners.TaskCompletionListener;
import com.mdlinkhealth.helper.StringHelper;
import com.mdlinkhealth.model.AppointmentListResponse;
import com.mdlinkhealth.model.AppointmentListResponseDetails;
import com.mdlinkhealth.model.GetApptList;
import com.mdlinkhealth.model.PatientProfileRequest;
import com.mdlinkhealth.preferences.SharedPreferenceManager;
import com.mdlinkhealth.util.Constants;
import com.mdlinkhealth.util.SearchTextWatcher;
import com.mdlinkhealth.video.VideoActivity;
import com.mdlinkhealth.voice.VoiceActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleAppointmentActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    private Toolbar toolbar;
    private AppCompatImageView imgCloseFilter;
    private AppCompatEditText edtFilter;
    private RecyclerView rvScheduledApptList;
    private AppointmentListAdapter appointmentListAdapter;
    private SharedPreferenceManager sharedPreferenceManager;
    private ChatClientManager clientManager;
    ArrayList<AppointmentListResponseDetails> appointmentListResponseDetailsArrayList;
    private PatientProfileRequest patientProfileRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_appointment_list);
        sharedPreferenceManager = new SharedPreferenceManager(this);
        initToolbar();
        initViews();
        initSearch();
        callToGetListScheduledAppointment(sharedPreferenceManager.getStringData(Constants.USER_ID), sharedPreferenceManager.getStringData(Constants.ROLE_ID));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_scheduledappointment), R.color.colorAccent);
    }

    private void initViews() {
        rvScheduledApptList = findViewById(R.id.rvScheduledAppointmentList);
        edtFilter = findViewById(R.id.edt_filter);
        imgCloseFilter = findViewById(R.id.img_closeFavoriteFilter);
        imgCloseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtFilter.setText("");
            }
        });
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
        if (appointmentListResponseDetailsArrayList == null) {
            appointmentListResponseDetailsArrayList = new ArrayList<>();
        }
        appointmentListResponseDetailsArrayList.addAll(appointmentListResponseDetailsList);
        appointmentListAdapter = new AppointmentListAdapter(this,
                appointmentListResponseDetailsList, RoleId, new AppointmentListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(final String type, final AppointmentListResponseDetails appointmentListResponseDetails) {
                sharedPreferenceManager.saveString(Constants.APPOINTMENT_ID, "" + appointmentListResponseDetails.getId());
                if (type.equalsIgnoreCase(Constants.CANCEL)) {
                    showAlertDialog(appointmentListResponseDetails, "2");
                    return;
                }
                if (type.equalsIgnoreCase(Constants.APPROVE)) {
                    callAppointmentStatusAPI(appointmentListResponseDetails, "1");
                    return;
                }
                if (type.equalsIgnoreCase(Constants.PATIENT_FILE)) {
                    if (sharedPreferenceManager.getStringData(Constants.ROLE_ID).equalsIgnoreCase("1")) {
                        Intent iViewPatientProfile = new Intent(ScheduleAppointmentActivity.this, ViewPatientFileActivity.class);
                        iViewPatientProfile.putExtra(Constants.APPOINTMENT_ID, appointmentListResponseDetails.getId());
                        startActivity(iViewPatientProfile);
                    }
                } else {
                    switch (sharedPreferenceManager.getStringData(Constants.ROLE_ID)) {
                        case "1": // Doctor
                            if (appointmentListResponseDetails.getIsCompleted() != 1 && appointmentListResponseDetails.getType() == 1) {
                                // open audio call activity

                                callGetPatientProfileAPI(appointmentListResponseDetails);

                            } else if (appointmentListResponseDetails.getIsCompleted() != 1 && appointmentListResponseDetails.getType() == 2) {
                                // open chat  activity
                                showMainChatActivity(sharedPreferenceManager.getStringData(Constants.ROLE_ID),
                                        "" + appointmentListResponseDetails.getId(),
                                        appointmentListResponseDetails.getName(),
                                        appointmentListResponseDetails.getDocName(),
                                        "" + appointmentListResponseDetails.getUserId());

                            } else if (appointmentListResponseDetails.getIsCompleted() != 1 && appointmentListResponseDetails.getType() == 3) {
                                // open video activity
                                showVideoActivity(sharedPreferenceManager.getStringData(Constants.ROLE_ID),
                                        "" + appointmentListResponseDetails.getId(),
                                        appointmentListResponseDetails.getName(),
                                        appointmentListResponseDetails.getDocName(),
                                        "" + appointmentListResponseDetails.getUserId());
                            }
                            break;
                        case "2": // Patient
                            if (appointmentListResponseDetails.getIsPayed() == 1) {
                                if (appointmentListResponseDetails.getType() == 1) {
                                    Toast.makeText(ScheduleAppointmentActivity.this, "Doctor will call you.", Toast.LENGTH_LONG).show();
                                } else if (appointmentListResponseDetails.getType() == 2) {
                                    // open chat call activity
                                    showMainChatActivity(sharedPreferenceManager.getStringData(Constants.ROLE_ID),
                                            "" + appointmentListResponseDetails.getId(),
                                            appointmentListResponseDetails.getName(),
                                            appointmentListResponseDetails.getDocName(),
                                            "" + appointmentListResponseDetails.getUserId());

                                } else if (appointmentListResponseDetails.getType() == 3) {
                                    // open video activity
                                    showVideoActivity(sharedPreferenceManager.getStringData(Constants.ROLE_ID),
                                            "" + appointmentListResponseDetails.getId(),
                                            appointmentListResponseDetails.getName(),
                                            appointmentListResponseDetails.getDocName(),
                                            "" + appointmentListResponseDetails.getUserId());
                                }
                            }
                            break;
                    }
                }
            }
        });
        rvScheduledApptList.setAdapter(appointmentListAdapter);
    }

    private void showAlertDialog(final AppointmentListResponseDetails appointmentListResponseDetails, final String action) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        dialog.dismiss();
                        callAppointmentStatusAPI(appointmentListResponseDetails, action);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleAppointmentActivity.this);
        builder.setTitle(R.string.cancellation);
        builder.setMessage(R.string.alert_cancel_label).setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void callAppointmentStatusAPI(final AppointmentListResponseDetails appointmentListResponseDetails, final String action) {

        HashMap<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put("appo_id", "" + appointmentListResponseDetails.getId());
        stringHashMap.put("status_id", action);
        showProgressDialog();
        Call<JsonObject> getPatientById = App.apiService.actionappointment(stringHashMap);
        getPatientById.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                hideProgressDialog();
                Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                if (response.code() == 200) {
                    Log.i(TAG, "2>>>>>>>>>>>>>" + response.body());
                    if (response.body().get("status").getAsInt() == 200) {
                        Toast.makeText(ScheduleAppointmentActivity.this,
                                response.body().get("message").getAsString(),
                                Toast.LENGTH_LONG).show();
                        if (action.equalsIgnoreCase("1")) {
                            appointmentListResponseDetails.setStatusId(1);
                            appointmentListAdapter.update(appointmentListResponseDetails);
                        } else {
                            //2 cancel appt response
                            appointmentListAdapter.remove(appointmentListResponseDetails);
                        }
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                hideProgressDialog();
                t.fillInStackTrace();
            }
        });
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

    private void showMainVoiceActivity(String RoleId, String AppointmentId, String Name, String DoctorName, String PatientId, String PhoneNo) {
        Intent launchIntent = new Intent();
        launchIntent.setClass(getApplicationContext(), VoiceActivity.class);
        launchIntent.putExtra(Constants.ROLE_ID, RoleId);
        launchIntent.putExtra(Constants.NAME, Name);
        launchIntent.putExtra(Constants.DOCTOR_NAME, DoctorName);
        launchIntent.putExtra(Constants.APPOINTMENT_ID, AppointmentId);
        launchIntent.putExtra(Constants.PATIENT_ID, PatientId);
        launchIntent.putExtra(Constants.PATIENT_PHONE_NO, PhoneNo);
        startActivity(launchIntent);
    }

    private void showVideoActivity(String RoleId, String AppointmentId, String Name, String DoctorName, String PatientId) {
        Intent launchIntent = new Intent();
        launchIntent.setClass(getApplicationContext(), VideoActivity.class);
        launchIntent.putExtra(Constants.ROLE_ID, RoleId);
        launchIntent.putExtra(Constants.NAME, Name);
        launchIntent.putExtra(Constants.DOCTOR_NAME, DoctorName);
        launchIntent.putExtra(Constants.APPOINTMENT_ID, AppointmentId);
        launchIntent.putExtra(Constants.PATIENT_ID, PatientId);
        startActivity(launchIntent);
    }

    private void initSearch() {
        edtFilter.addTextChangedListener(new SearchTextWatcher() {
            @Override
            public void startSearch(String query) {
                imgCloseFilter.setVisibility(View.VISIBLE);
                filter(query);
            }

            @Override
            public void closeSearch() {
                imgCloseFilter.setVisibility(View.GONE);
                appointmentListAdapter.clear();
                appointmentListAdapter.addAll(getApptListCopy());
            }
        });
    }

    private void filter(String text) {
        List<AppointmentListResponseDetails> temp = new ArrayList<>();
        if (null != getApptListCopy()) {
            for (AppointmentListResponseDetails d : getApptListCopy()) {
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches
                if (d.getName().toLowerCase().contains(text.toLowerCase())
                        || d.getScheduledDate().toLowerCase().contains(text.toLowerCase())
                        || d.getDocName().toLowerCase().contains(text.toLowerCase())
                        || d.getScheduledTime().toLowerCase().contains(text.toLowerCase())
                        || d.getVisitPurpose().toLowerCase().contains(text.toLowerCase())
                        || d.getType().equals(getTypeVal(text))) {
                    temp.add(d);
                }
            }
            //update recycler-view
            appointmentListAdapter.clear();
            appointmentListAdapter.addAll(temp);
        }
    }


    private List<AppointmentListResponseDetails> getApptListCopy() {
        return appointmentListResponseDetailsArrayList;
    }

    private int getTypeVal(String val) {
        int type = 0;
        if (val.equalsIgnoreCase("Audio Call")) {
            type = 1;
        } else if (val.equalsIgnoreCase("Instant Message")) {
            type = 2;
        } else if (val.equalsIgnoreCase("Video Call")) {
            type = 3;
        }
        return type;
    }

    private synchronized void callGetPatientProfileAPI(final AppointmentListResponseDetails appointmentListResponseDetails) {
        showProgressDialog();

        String PatientId = "" + appointmentListResponseDetails.getUserId();
        if (StringHelper.isEmptyOrNull(PatientId)) {
            return;
        }

        Call<JsonObject> getPatientById = App.apiService.getPatientProfile(PatientId);
        getPatientById.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                hideProgressDialog();
                Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                if (response.code() == 200) {
                    try {
                        Log.i(TAG, "2>>>>>>>>>>>>>" + response.body());
                        JsonObject jsonObject = response.body().getAsJsonObject("result");
                        Log.i(TAG, "2>>name>>>>>>>>>>>" + jsonObject.get("name"));
                        Log.i(TAG, "2>>email>>>>>>>>>>>" + jsonObject.get("email"));
                        if (patientProfileRequest == null) {
                            patientProfileRequest = new PatientProfileRequest();
                        }
                        patientProfileRequest.setPhoneNo(jsonObject.get("phone_no").getAsString());

                        showMainVoiceActivity(sharedPreferenceManager.getStringData(Constants.ROLE_ID),
                                "" + appointmentListResponseDetails.getId(),
                                appointmentListResponseDetails.getName(),
                                appointmentListResponseDetails.getDocName(),
                                "" + appointmentListResponseDetails.getUserId(),
                                jsonObject.get("phone_no").getAsString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                hideProgressDialog();
                t.fillInStackTrace();
            }
        });
    }
}

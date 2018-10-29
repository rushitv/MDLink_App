package com.mdlink;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mdlink.model.PatientProfileRequest;
import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;
import com.mdlink.util.MdlinkProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Patient_Profile extends BaseActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private Toolbar toolbar;
    private EditText edtEmailPatientProfile, edtFullNamePatientProfile, edtPhonePatientProfile, edtAgePatientProfile;
    private TextView updatePatientProfile;
    private SharedPreferenceManager sharedPreferenceManager;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__profile);
        sharedPreferenceManager = new SharedPreferenceManager(this);
        initToolbar();

        updatePatientProfile = findViewById(R.id.updatePatientProfile);
        updatePatientProfile.setOnClickListener(this);
        edtEmailPatientProfile = findViewById(R.id.edtEmailPatientProfile);
        edtFullNamePatientProfile = findViewById(R.id.edtFullNamePatientProfile);
        edtPhonePatientProfile = findViewById(R.id.edtPhonePatientProfile);
        edtAgePatientProfile = findViewById(R.id.edtAgePatientProfile);
        populateFormData();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_profile), R.color.colorAccent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updatePatientProfile:

                if(ValidateForm().length() > 0) {
                    Toast.makeText( this,ValidateForm(),Toast.LENGTH_LONG).show();
                }else {
                    //call api
                    PatientProfileRequest patientProfileRequest = new PatientProfileRequest();
                    patientProfileRequest.setName(edtFullNamePatientProfile.getText().toString());
                    patientProfileRequest.setAge(edtAgePatientProfile.getText().toString());
                    patientProfileRequest.setEmail(edtEmailPatientProfile.getText().toString());
                    patientProfileRequest.setPhoneNo(edtPhonePatientProfile.getText().toString());
                    patientProfileRequest.setRoleId(sharedPreferenceManager.getStringData(Constants.ROLE_ID));
                    patientProfileRequest.setUserId(sharedPreferenceManager.getStringData(Constants.USER_ID));

                    callSubmitProfileAPI(patientProfileRequest);
                }
                break;
        }
    }

    private void populateFormData(){
        try {

            setFetchedData();
            callGetPatientProfileAPI();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setFetchedData(){
        try {
            if(null != sharedPreferenceManager.getStringData(Constants.PHONE) &&
                    !TextUtils.isEmpty(sharedPreferenceManager.getStringData("PhoneNumber"))) {
                edtPhonePatientProfile.setText(sharedPreferenceManager.getStringData("PhoneNumber"));
            }

            if(null != sharedPreferenceManager.getStringData(Constants.NAME) &&
                    !TextUtils.isEmpty(sharedPreferenceManager.getStringData("Name"))){
                edtFullNamePatientProfile.setText(sharedPreferenceManager.getStringData("Name"));
            }

            if(null != sharedPreferenceManager.getStringData(Constants.EMAIL) &&
                    !TextUtils.isEmpty(sharedPreferenceManager.getStringData("Email"))){
                edtEmailPatientProfile.setText(sharedPreferenceManager.getStringData("Email"));
            }

            if(null != sharedPreferenceManager.getStringData(Constants.AGE) &&
                    !TextUtils.isEmpty(sharedPreferenceManager.getStringData("Age"))){
                edtAgePatientProfile.setText(sharedPreferenceManager.getStringData("Age"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void callGetPatientProfileAPI() {
        MdlinkProgressBar.setProgressBar(this);
        Call<JsonObject> getPatientById = App.apiService.getPatientProfile(sharedPreferenceManager.getStringData(Constants.USER_ID));
        getPatientById.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                if (response.code() == 200) {
                    Log.i(TAG, "2>>>>>>>>>>>>>" + response.body());
                    JsonObject jsonObject = response.body().getAsJsonObject("result");
                    Log.i(TAG, "2>>name>>>>>>>>>>>" +jsonObject.get("name"));
                    Log.i(TAG, "2>>email>>>>>>>>>>>" +jsonObject.get("email"));
                    sharedPreferenceManager.saveString(Constants.NAME,jsonObject.get("name").getAsString());
                    sharedPreferenceManager.saveString(Constants.EMAIL,jsonObject.get("email").getAsString());
                    sharedPreferenceManager.saveString(Constants.PHONE,jsonObject.get("phone_no").getAsString());
                    sharedPreferenceManager.saveString(Constants.AGE,jsonObject.get("age").getAsString());
                    setFetchedData();
                }
                MdlinkProgressBar.hideProgressBar(Patient_Profile.this);
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                t.fillInStackTrace();
                MdlinkProgressBar.hideProgressBar(Patient_Profile.this);
            }
        });
    }

    private void callSubmitProfileAPI(PatientProfileRequest patientProfileRequest) {
        MdlinkProgressBar.setProgressBar(this);
        Call<JsonObject> updatePatientProfile = App.apiService.updatePatientProfile(
                patientProfileRequest);
        updatePatientProfile.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                if (response.code() == 200) {
                    Log.i(TAG, "2>>>>>>>>>>>>>" + response.body());
                }
                MdlinkProgressBar.hideProgressBar(Patient_Profile.this);
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                t.fillInStackTrace();
                MdlinkProgressBar.hideProgressBar(Patient_Profile.this);
            }
        });
    }

    private String ValidateForm() {
        String msg = "";
        if (TextUtils.isEmpty(edtFullNamePatientProfile.getText().toString())) {
            msg += "Please input full name \n";
        }
        if (TextUtils.isEmpty(edtAgePatientProfile.getText().toString())) {
            msg += "Please input age \n";
        }
        if(TextUtils.isEmpty(edtEmailPatientProfile.getText().toString()) &&
               !Patterns.EMAIL_ADDRESS.matcher(edtEmailPatientProfile.getText().toString()).matches()){
            msg += "Please enter valid email \n";
        }
        if (TextUtils.isEmpty(edtPhonePatientProfile.getText().toString())) {
            msg += "Please input phone number \n";
        }
        return msg;
    }
}


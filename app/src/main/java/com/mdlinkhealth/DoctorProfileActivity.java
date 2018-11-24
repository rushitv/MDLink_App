package com.mdlinkhealth;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mdlinkhealth.model.DoctorProfile;
import com.mdlinkhealth.preferences.SharedPreferenceManager;
import com.mdlinkhealth.util.Constants;
import com.mdlinkhealth.util.ValidationsUtil;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorProfileActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private Toolbar toolbar;
    private EditText edtEmailDPUpdate, edtFullNameDPUpdate, edtPhoneDPUpdate, edtAgeDPUpdate, edtQualificationDPUpdate,
            edtSpecialityDPUpdate, edtMedicalSchoolDPUpdate, edtMedicalCouncilDPUpdate,
            edtMedicalYearOfGraduationDPUpdate, edtRegistrationNumberDPUpdate, edtLocationCountryDPUpdate,
            edtAvailMorningTimeUpdate, edtAvailMorningTimeTOUpdate,
            edtAvailEveningTimeUpdate, edtAvailEveningTimeTOUpdate;
    private CheckBox checkboxSundayUpdate, checkboxMondayUpdate, checkboxTuesdayUpdate, checkboxWednesdayUpdate,
            checkboxThursdayUpdate, checkboxFridayUpdate, checkboxSaturdayUpdate;
    private SharedPreferenceManager sharedPreferenceManager;
    private TextView btnSubmitUpdate;
    private Gson gson = new Gson();
    TimePickerDialog timepickerdialog;
    Calendar calendarTime;
    String format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        initToolbar();
        edtEmailDPUpdate = findViewById(R.id.edtEmailDPUpdate);
        btnSubmitUpdate = findViewById(R.id.btnSubmitUpdate);
        btnSubmitUpdate.setOnClickListener(this);
        edtFullNameDPUpdate = findViewById(R.id.edtFullNameDPUpdate);
        edtPhoneDPUpdate = findViewById(R.id.edtPhoneDPUpdate);
        edtAgeDPUpdate = findViewById(R.id.edtAgeDPUpdate);
        edtQualificationDPUpdate = findViewById(R.id.edtQualificationDPUpdate);
        edtSpecialityDPUpdate = findViewById(R.id.edtSpecialityDPUpdate);
        edtMedicalSchoolDPUpdate = findViewById(R.id.edtMedicalSchoolDPUpdate);
        edtMedicalCouncilDPUpdate = findViewById(R.id.edtMedicalCouncilDPUpdate);
        edtMedicalYearOfGraduationDPUpdate = findViewById(R.id.edtMedicalYearOfGraduationDPUpdate);
        edtRegistrationNumberDPUpdate = findViewById(R.id.edtRegistrationNumberDPUpdate);
        edtLocationCountryDPUpdate = findViewById(R.id.edtLocationCountryDPUpdate);
        edtAvailMorningTimeUpdate = findViewById(R.id.edtAvailMorningTimeUpdate);
        edtAvailMorningTimeTOUpdate = findViewById(R.id.edtAvailMorningTimeTOUpdate);
        edtAvailEveningTimeUpdate = findViewById(R.id.edtAvailEveningTimeUpdate);
        edtAvailEveningTimeTOUpdate = findViewById(R.id.edtAvailEveningTimeTOUpdate);

        checkboxSundayUpdate = findViewById(R.id.checkboxSundayUpdate);
        checkboxMondayUpdate = findViewById(R.id.checkboxMondayUpdate);
        checkboxTuesdayUpdate = findViewById(R.id.checkboxTuesdayUpdate);
        checkboxWednesdayUpdate = findViewById(R.id.checkboxWednesdayUpdate);
        checkboxThursdayUpdate = findViewById(R.id.checkboxThursdayUpdate);
        checkboxFridayUpdate = findViewById(R.id.checkboxFridayUpdate);
        checkboxSaturdayUpdate = findViewById(R.id.checkboxSaturdayUpdate);

        sharedPreferenceManager = new SharedPreferenceManager(this);

        bindUI();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_doctorprofile), R.color.colorAccent);
    }

    private void bindUI() {
        String json = sharedPreferenceManager.getStringData(Constants.DOCTOR_PROFILE);
        DoctorProfile obj = gson.fromJson(json, DoctorProfile.class);
        edtEmailDPUpdate.setText(obj.getEmail());

        edtFullNameDPUpdate.setText(obj.getName());
        edtPhoneDPUpdate.setText(obj.getPhoneNo());
        edtAgeDPUpdate.setText(obj.getAge());
        edtQualificationDPUpdate.setText(obj.getQualification());
        edtSpecialityDPUpdate.setText(obj.getSpeciality());
        edtMedicalSchoolDPUpdate.setText(obj.getMedicalSchool());
        edtMedicalCouncilDPUpdate.setText(obj.getMedicalCouncil());
        edtMedicalYearOfGraduationDPUpdate.setText(obj.getGraduationYear());
        edtRegistrationNumberDPUpdate.setText(obj.getRegistrationNumber());
        edtLocationCountryDPUpdate.setText(obj.getLocation());
        edtAvailMorningTimeUpdate.setText(obj.getAvailMorning());
        edtAvailMorningTimeUpdate.setOnClickListener(this);
        edtAvailMorningTimeTOUpdate.setText(obj.getAvailMorningTo());
        edtAvailMorningTimeTOUpdate.setOnClickListener(this);
        edtAvailEveningTimeUpdate.setText(obj.getAvailEvening());
        edtAvailEveningTimeUpdate.setOnClickListener(this);
        edtAvailEveningTimeTOUpdate.setText(obj.getAvailEveningTo());
        edtAvailEveningTimeTOUpdate.setOnClickListener(this);

        if(!TextUtils.isEmpty(obj.getAvailableDay())){
            if(obj.getAvailableDay().contains("0")){
                checkboxSundayUpdate.setChecked(true);
            }
            if(obj.getAvailableDay().contains("1")){
                checkboxMondayUpdate.setChecked(true);
            }
            if(obj.getAvailableDay().contains("2")){
                checkboxTuesdayUpdate.setChecked(true);
            }
            if(obj.getAvailableDay().contains("3")){
                checkboxWednesdayUpdate.setChecked(true);
            }
            if(obj.getAvailableDay().contains("4")){
                checkboxThursdayUpdate.setChecked(true);
            }
            if(obj.getAvailableDay().contains("5")){
                checkboxFridayUpdate.setChecked(true);
            }
            if(obj.getAvailableDay().contains("6")){
                checkboxSaturdayUpdate.setChecked(true);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtAvailMorningTimeUpdate:
                showAndSetTimePopup(edtAvailMorningTimeUpdate);
                break;
            case R.id.edtAvailMorningTimeTOUpdate:
                showAndSetTimePopup(edtAvailMorningTimeTOUpdate);
                break;
            case R.id.edtAvailEveningTimeUpdate:
                showAndSetTimePopup(edtAvailEveningTimeUpdate);
                break;
            case R.id.edtAvailEveningTimeTOUpdate:
                showAndSetTimePopup(edtAvailEveningTimeTOUpdate);
                break;
            case R.id.btnSubmitUpdate:
                if(Valid().length()>0){
                    Toast.makeText(DoctorProfileActivity.this,Valid(),Toast.LENGTH_LONG);
                }else {
                    SubmitProfileUpdateRequest();
                }
                break;
        }
    }

    private String Valid(){
        String msg = "";
        if(TextUtils.isEmpty(edtFullNameDPUpdate.getText().toString())){
            msg += "Input Full Name \n";
        }
        if(TextUtils.isEmpty(edtRegistrationNumberDPUpdate.getText().toString())){
            msg += "Input Registration Number \n";
        }
        if(TextUtils.isEmpty(edtAvailMorningTimeUpdate.getText().toString())){
            msg += "Input Available Morning Time \n";
        }
        if(TextUtils.isEmpty(edtAvailMorningTimeTOUpdate.getText().toString())){
            msg += "Input Available Morning To Time \n";
        }
        if(TextUtils.isEmpty(edtAvailEveningTimeUpdate.getText().toString())){
            msg += "Input Available Evening Time \n";
        }
        if(TextUtils.isEmpty(edtAvailEveningTimeTOUpdate.getText().toString())){
            msg += "Input Available Evening To Time \n";
        }
        return msg;
    }

    private void showAndSetTimePopup(final EditText editText) {
        calendarTime = Calendar.getInstance();
        int CalendarHour, CalendarMinute;
        CalendarHour = calendarTime.get(Calendar.HOUR_OF_DAY);
        CalendarMinute = calendarTime.get(Calendar.MINUTE);
        timepickerdialog = new TimePickerDialog(DoctorProfileActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            format = " AM";
                        } else if (hourOfDay == 12) {
                            format = " PM";
                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            format = " PM";
                        } else {
                            format = " AM";
                        }

                        String val = hourOfDay + ":" + ValidationsUtil.getPaddedNumber(minute) + format;
                        editText.setText(val);
                        editText.setTag(val);
                    }
                    //DisplayTime.setText(hourOfDay + ":" + minute + format);

                }, CalendarHour, CalendarMinute, false);
        timepickerdialog.show();
    }

    private void SubmitProfileUpdateRequest(){

        ArrayList<String> stringArrayList = new ArrayList<>();
        if (checkboxSundayUpdate.isChecked()) {
            stringArrayList.add("0");
        }
        if (checkboxMondayUpdate.isChecked()) {
            stringArrayList.add("1");
        }
        if (checkboxTuesdayUpdate.isChecked()) {
            stringArrayList.add("2");
        }
        if (checkboxWednesdayUpdate.isChecked()) {
            stringArrayList.add("3");
        }
        if (checkboxThursdayUpdate.isChecked()) {
            stringArrayList.add("4");
        }
        if (checkboxFridayUpdate.isChecked()) {
            stringArrayList.add("5");
        }
        if (checkboxSaturdayUpdate.isChecked()) {
            stringArrayList.add("6");
        }

        StringBuilder checkboxExtract = new StringBuilder();
        for (String checkVal : stringArrayList) {
            checkboxExtract.append(checkVal);
            checkboxExtract.append(Constants.SEPARATOR);
        }

        String availabledays = checkboxExtract.toString();
        Log.i(TAG, "Available Days>>>>" + availabledays);

        DoctorProfile doctorProfile = new DoctorProfile();

        doctorProfile.setRoleId(Integer.parseInt(sharedPreferenceManager.getStringData(Constants.ROLE_ID)));
        doctorProfile.setUserId(sharedPreferenceManager.getStringData(Constants.USER_ID));
        doctorProfile.setName(edtFullNameDPUpdate.getText().toString());
        doctorProfile.setAge(edtAgeDPUpdate.getText().toString());
        doctorProfile.setAvailableDay(availabledays);
        doctorProfile.setRegistrationNumber(edtRegistrationNumberDPUpdate.getText().toString());
        doctorProfile.setPhoneNo(edtPhoneDPUpdate.getText().toString());
        doctorProfile.setQualification(edtQualificationDPUpdate.getText().toString());
        doctorProfile.setSpeciality(edtSpecialityDPUpdate.getText().toString());
        doctorProfile.setMedicalCouncil(edtMedicalCouncilDPUpdate.getText().toString());
        doctorProfile.setMedicalSchool(edtMedicalSchoolDPUpdate.getText().toString());
        doctorProfile.setGraduationYear(edtMedicalYearOfGraduationDPUpdate.getText().toString());
        doctorProfile.setAvailMorning(edtAvailMorningTimeUpdate.getText().toString());
        doctorProfile.setAvailMorningTo(edtAvailMorningTimeTOUpdate.getText().toString());
        doctorProfile.setAvailEvening(edtAvailEveningTimeUpdate.getText().toString());
        doctorProfile.setAvailEveningTo(edtAvailEveningTimeTOUpdate.getText().toString());

        callSubmitAPI(doctorProfile);

    }

    private void callSubmitAPI(DoctorProfile doctorProfile){
        showProgressDialog();
        Call<JsonObject> callToGetUserProfile = App.apiService.doctorProfile(doctorProfile);
        callToGetUserProfile.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideProgressDialog();
                Log.i(TAG, ">>>>>>>>>>>>>>>>>" + response.body());
                if (response.body().get("status").getAsString().equalsIgnoreCase("200")) {

                    Gson gson = new Gson();
                    JsonObject jsonObject = response.body().getAsJsonObject("result");
                    DoctorProfile doctorProfile = gson.fromJson(jsonObject.toString(), DoctorProfile.class);
                    String json = gson.toJson(doctorProfile);
                    sharedPreferenceManager.saveString(Constants.DOCTOR_PROFILE, json);

                    Toast.makeText(DoctorProfileActivity.this, response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideProgressDialog();
                Log.i(TAG, ">>>>>>>>>onFailure>>>>>>>>");
            }
        });
    }
}

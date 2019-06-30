package com.mdlinkhealth;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mdlinkhealth.api.APIService;
import com.mdlinkhealth.api.RestAPIClent;
import com.mdlinkhealth.model.AppointmentOptionsResponse;
import com.mdlinkhealth.model.AvailableDoctorsRequest;
import com.mdlinkhealth.model.BookAppointmentRequest;
import com.mdlinkhealth.model.DoctorsListByDateAndTimeModel;
import com.mdlinkhealth.model.DoctorsListModel;
import com.mdlinkhealth.model.OptionDetails;
import com.mdlinkhealth.model.Specialities;
import com.mdlinkhealth.preferences.SharedPreferenceManager;
import com.mdlinkhealth.util.Constants;
import com.mdlinkhealth.util.FileUtil;
import com.mdlinkhealth.util.ValidationsUtil;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();
    private static final int WRITE_STORAGE_PERMISSION_REQUEST_CODE = 3;
    private static final int ACTIVITY_CHOOSE_FILE = 2;
    private static final String[] PERMISSION_PICTURES = {
//            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    int mYear, mMonth, mDay;
    String phamacysel, docsel;
    StringBuilder sb;
    CheckBox cbRenew, cbSickNote, cbCancerBookAppt, cbDiabetesBookAppt, cbHeartDiseasBookAppt, cbStrokeBookAppt, cbHighBPBookAppt, cbHighCholesterolBookAppt, cbHighAsthmaBookAppt, cbDepressionBookAppt, cbArthritisBookAppt, cbAbnormalThyroidBookAppt, cbAPregnantBookAppt, cbOtherBookAppt;
    TextView submitbook, tvExtraFile, tvExtraFilePath;

    EditText editPharmacyBookAppt, edtDoctorBookAppt, edtChooseTimeBookAppt, edtDateBookAppt, edtCouponCodeBookAppt,
            edtLocationBookAppt, edtPreviousVisitedHospitalBookAppt, edtAllergiesBookAppt,
            edtNameBookAppt, edtAgeBookAppt, edtPurposeBookAppt, editSpecialityBookAppt;
    List<DoctorsListByDateAndTimeModel> listDoctorsListByDateAndTimeModel;
    String format;
    Calendar calendarTime;
    TimePickerDialog timepickerdialog;
    APIService apiService =
            RestAPIClent.getClient().create(APIService.class);
    RadioGroup rgHowToConnect;
    RadioButton rbHowToConnectVal;
    SharedPreferenceManager sharedPreferenceManager;
    Toolbar toolbar;

    List<OptionDetails> pharmacyList;
    List<Specialities> specialitiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__appoinment);
        initToolbar();
        edtNameBookAppt = findViewById(R.id.edtNameBookAppt);
        edtAgeBookAppt = findViewById(R.id.edtAgeBookAppt);
        edtPurposeBookAppt = findViewById(R.id.edtPurposeBookAppt);

        edtPreviousVisitedHospitalBookAppt = findViewById(R.id.edtPreviousVisitedHospitalBookAppt);
        edtAllergiesBookAppt = findViewById(R.id.edtAllergiesBookAppt);

        edtDoctorBookAppt = findViewById(R.id.edtDoctorBookAppt);
        edtDoctorBookAppt.setOnClickListener(this);
        edtChooseTimeBookAppt = findViewById(R.id.edtChooseTimeBookAppt);
        edtChooseTimeBookAppt.setOnClickListener(this);
        edtDateBookAppt = findViewById(R.id.edtDateBookAppt);
        edtDateBookAppt.setOnClickListener(this);

        rgHowToConnect = findViewById(R.id.rgHowToConnect);
        sharedPreferenceManager = new SharedPreferenceManager(this);

        editPharmacyBookAppt = findViewById(R.id.editPharmacyBookAppt);
        editPharmacyBookAppt.setOnClickListener(this);

        editSpecialityBookAppt = findViewById(R.id.editSpecialityBookAppt);
        editSpecialityBookAppt.setOnClickListener(this);

        submitbook = findViewById(R.id.book_submit);
        submitbook.setOnClickListener(this);

        cbRenew = findViewById(R.id.cbRenew);
        cbSickNote = findViewById(R.id.cbSickNote);

        cbCancerBookAppt = findViewById(R.id.cbCancerBookAppt);
        cbDiabetesBookAppt = findViewById(R.id.cbDiabetesBookAppt);
        cbHeartDiseasBookAppt = findViewById(R.id.cbHeartDiseasBookAppt);
        cbStrokeBookAppt = findViewById(R.id.cbStrokeBookAppt);

        cbHighBPBookAppt = findViewById(R.id.cbHighBPBookAppt);
        cbHighCholesterolBookAppt = findViewById(R.id.cbHighCholesterolBookAppt);
        cbHighAsthmaBookAppt = findViewById(R.id.cbHighAsthmaBookAppt);
        cbDepressionBookAppt = findViewById(R.id.cbDepressionBookAppt);
        cbArthritisBookAppt = findViewById(R.id.cbArthritisBookAppt);
        cbAbnormalThyroidBookAppt = findViewById(R.id.cbAbnormalThyroidBookAppt);
        cbAPregnantBookAppt = findViewById(R.id.cbAPregnantBookAppt);
        cbOtherBookAppt = findViewById(R.id.cbOtherBookAppt);

        edtCouponCodeBookAppt = findViewById(R.id.edtCouponCodeBookAppt);

        edtDateBookAppt.setText(ValidationsUtil.getCurrentDate());

        if (!TextUtils.isEmpty(sharedPreferenceManager.getStringData("Name"))) {
            edtNameBookAppt.setText(sharedPreferenceManager.getStringData("Name"));
        }
        if (!TextUtils.isEmpty(sharedPreferenceManager.getStringData("Age"))) {
            edtAgeBookAppt.setText(sharedPreferenceManager.getStringData("Age"));
        }
        if (!TextUtils.isEmpty(sharedPreferenceManager.getStringData("Location"))) {
            edtLocationBookAppt.setText(sharedPreferenceManager.getStringData("Location"));
        }
        CallGetAppointmentOptions();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_bookappointment), R.color.colorAccent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doc_file:
                if (checkPermissionForReadWriteExtertalStorage(BookAppointmentActivity.this)) {
                    openFilesDirectory();
                } else {
                    try {
                        requestPermissionForReadWriteExtertalStorage(BookAppointmentActivity.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.editPharmacyBookAppt:
                bindPharmacyOptions();
                break;
            case R.id.editSpecialityBookAppt:
                bindSpecialities();
                break;
            case R.id.edtDoctorBookAppt:
                bindDropdown();
                break;
            case R.id.edtChooseTimeBookAppt:
                showAndSetTimePopup(edtChooseTimeBookAppt);
                break;
            case R.id.edtDateBookAppt:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtDateBookAppt.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.book_submit:
                String type = "";

                if (TextUtils.isEmpty(editPharmacyBookAppt.getText().toString())) {
                    Toast.makeText(this, "Please choose pharmacy", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(edtChooseTimeBookAppt.getText().toString())) {
                    Toast.makeText(this, "Please choose time", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(editSpecialityBookAppt.getText().toString())) {
                    Toast.makeText(this, "Please choose speciality", Toast.LENGTH_LONG).show();
                    return;
                }

                BookAppointmentRequest bookAppointmentRequest = new BookAppointmentRequest();
                try {
                    if (selectedDoctor() != -1) {
                        Log.i(TAG, ">>>>>" + selectedDoctor());
                        Log.i(TAG, ">>doctor>>id>>>>>" + listDoctorsListByDateAndTimeModel.get(selectedDoctor()).getId());
                        bookAppointmentRequest.setPreferredDoctor("" + listDoctorsListByDateAndTimeModel.get(selectedDoctor()).getId());
                    } else {
                        Toast.makeText(this, "Please choose doctor", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (ArrayIndexOutOfBoundsException exception) {
                    bookAppointmentRequest.setPreferredDoctor("0");
                }

                String Speciality = "";
                try {

                    if (selectedSpeciality() != -1) {
                        Log.i(TAG, ">>setSpecialities_id>>" + specialitiesList.get(selectedSpeciality()).getId());
                        Speciality = specialitiesList.get(selectedSpeciality()).getName();
                        bookAppointmentRequest.setSpecialities_id(specialitiesList.get(selectedSpeciality()).getId());
                    } else {
                        Toast.makeText(this, "Please choose speciality", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    bookAppointmentRequest.setSpecialities_id(0);
                }

                int selectedId = rgHowToConnect.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                rbHowToConnectVal = findViewById(selectedId);
                if (rbHowToConnectVal != null) {
                    Log.i(TAG, ">>>>>>>>" + rbHowToConnectVal.getText());
                    switch (rbHowToConnectVal.getText().toString()) {
                        case "Audio":
                            type = "1";
                            break;
                        case "Instant Message":
                            type = "2";
                            break;
                        case "Video Call":
                            type = "3";
                            break;
                    }
                    Log.i(TAG, "type>>>>>>>>" + type);
                } else {
                    Toast.makeText(this, "Choose How would you like to connect?", Toast.LENGTH_LONG).show();
                    return;
                }


                ArrayList<String> stringArrayList = new ArrayList<>();
                if (cbCancerBookAppt.isChecked()) {
                    stringArrayList.add(cbCancerBookAppt.getText().toString());
                }
                if (cbDiabetesBookAppt.isChecked()) {
                    stringArrayList.add(cbDiabetesBookAppt.getText().toString());
                }
                if (cbStrokeBookAppt.isChecked()) {
                    stringArrayList.add(cbStrokeBookAppt.getText().toString());
                }
                if (cbAbnormalThyroidBookAppt.isChecked()) {
                    stringArrayList.add(cbAbnormalThyroidBookAppt.getText().toString());
                }
                if (cbAPregnantBookAppt.isChecked()) {
                    stringArrayList.add(cbAPregnantBookAppt.getText().toString());
                }
                if (cbArthritisBookAppt.isChecked()) {
                    stringArrayList.add(cbArthritisBookAppt.getText().toString());
                }
                if (cbDepressionBookAppt.isChecked()) {
                    stringArrayList.add(cbDepressionBookAppt.getText().toString());
                }
                if (cbHeartDiseasBookAppt.isChecked()) {
                    stringArrayList.add(cbHeartDiseasBookAppt.getText().toString());
                }
                if (cbHighAsthmaBookAppt.isChecked()) {
                    stringArrayList.add(cbHighAsthmaBookAppt.getText().toString());
                }
                if (cbHighBPBookAppt.isChecked()) {
                    stringArrayList.add(cbHighBPBookAppt.getText().toString());
                }
                if (cbHighCholesterolBookAppt.isChecked()) {
                    stringArrayList.add(cbHighCholesterolBookAppt.getText().toString());
                }
                if (cbOtherBookAppt.isChecked()) {
                    stringArrayList.add(cbOtherBookAppt.getText().toString());
                }
                StringBuilder checkboxMedicalCondition = new StringBuilder();
                for (String checkVal : stringArrayList) {
                    checkboxMedicalCondition.append(checkVal);
                    checkboxMedicalCondition.append(Constants.SEPARATOR);
                }
                bookAppointmentRequest.setVisitPurpose(edtPurposeBookAppt.getText().toString());
                bookAppointmentRequest.setAge(edtAgeBookAppt.getText().toString());
                bookAppointmentRequest.setAllergy(edtAllergiesBookAppt.getText().toString());
                bookAppointmentRequest.setIsRenew(cbRenew.isChecked() ? "1" : "0");
                bookAppointmentRequest.setSickNote(cbSickNote.isChecked() ? "1" : "0");
                bookAppointmentRequest.setCouponCode(edtCouponCodeBookAppt.getText().toString());
                bookAppointmentRequest.setMedicalConditions(String.valueOf(checkboxMedicalCondition));
                bookAppointmentRequest.setName(edtNameBookAppt.getText().toString());
                bookAppointmentRequest.setPharmacy(editPharmacyBookAppt.getText().toString());
                bookAppointmentRequest.setPreviousHospital(edtPreviousVisitedHospitalBookAppt.getText().toString());
                bookAppointmentRequest.setScheduledDate(edtDateBookAppt.getText().toString());
                bookAppointmentRequest.setScheduledTime(edtChooseTimeBookAppt.getText().toString());
                bookAppointmentRequest.setType(type);
                bookAppointmentRequest.setUserId(sharedPreferenceManager.getStringData("UserId"));

                callCreateAppointmentAPI(Speciality, bookAppointmentRequest);
                break;
        }
    }

    private void callCreateAppointmentAPI(final String Speciality, final BookAppointmentRequest bookAppointmentRequest) {
        Call<JsonObject> callToGetUserProfile = apiService.createAppointment(bookAppointmentRequest);
        callToGetUserProfile.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i(TAG, ">>>>>>>>>>>>>>>>>" + response.body());
                if (response.body().get("status").getAsString().equalsIgnoreCase("200")) {
                    Toast.makeText(BookAppointmentActivity.this, response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();

                    JsonObject jsonObject = response.body().get("result").getAsJsonObject();
                    Intent iConfirmAppt = new Intent(BookAppointmentActivity.this, ConfirmAppointmentActivity.class);
                    iConfirmAppt.putExtra("Speciality", Speciality);
                    iConfirmAppt.putExtra("AppointmentId", jsonObject.get("id").getAsString());
                    iConfirmAppt.putExtra("CouponCode", edtCouponCodeBookAppt.getText().toString());
                    iConfirmAppt.putExtra("Amount", jsonObject.get("amount").getAsDouble());
                    iConfirmAppt.putExtra("BookAppointmentRequest", bookAppointmentRequest);
                    iConfirmAppt.putExtra("PreferredDoctorName", edtDoctorBookAppt.getText().toString());
                    startActivity(iConfirmAppt);
                } else {
                    Toast.makeText(BookAppointmentActivity.this, response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i(TAG, ">>>>>>>>>onFailure>>>>>>>>");
            }
        });
    }

    private void callAvailableDoctors() {

        if (null == edtChooseTimeBookAppt.getTag() || null == edtDateBookAppt.getText() || TextUtils.isEmpty(edtChooseTimeBookAppt.getTag().toString()) || TextUtils.isEmpty(edtDateBookAppt.getText().toString())) {
            Toast.makeText(this, "Please select valid date and time", Toast.LENGTH_LONG).show();
            return;
        }

        Log.i(TAG, "date:: " + edtDateBookAppt.getText().toString());
        Log.i(TAG, "time:: " + edtChooseTimeBookAppt.getTag().toString());

        AvailableDoctorsRequest availableDoctorsRequest = new AvailableDoctorsRequest();
        availableDoctorsRequest.setScheduledDate(edtDateBookAppt.getText().toString().trim());
        availableDoctorsRequest.setScheduledTime(edtChooseTimeBookAppt.getText().toString().trim());
        availableDoctorsRequest.setSpecialitiesId(specialitiesList.get(selectedSpeciality()).getId());

        Call<DoctorsListModel> callToGetUserProfile = apiService.availabledoctors(availableDoctorsRequest);
        showProgressDialog();
        callToGetUserProfile.enqueue(new Callback<DoctorsListModel>() {
            @Override
            public void onResponse(Call<DoctorsListModel> call, Response<DoctorsListModel> response) {
                hideProgressDialog();
                Log.i(TAG, ">>>>>>>>>>>>>>>>>" + response.body());
                if (response.body().getResult().size() > 0) {
                    listDoctorsListByDateAndTimeModel = response.body().getResult();
                }
            }

            @Override
            public void onFailure(Call<DoctorsListModel> call, Throwable t) {
                hideProgressDialog();
                Log.i(TAG, ">>>>>>>>>onFailure>>>>>>>>");
            }
        });
    }

    private void bindDropdown() {

        if (listDoctorsListByDateAndTimeModel != null && listDoctorsListByDateAndTimeModel.size() > 0) {
            final ArrayList<String> cityLST = new ArrayList<>(listDoctorsListByDateAndTimeModel.size());

            for (int i = 0; i < listDoctorsListByDateAndTimeModel.size(); i++) {
                cityLST.add(listDoctorsListByDateAndTimeModel.get(i).getName());
            }

            AlertDialog.Builder myDialog = new AlertDialog.Builder(this, R.style.LightDialogTheme);
            final EditText editText = new EditText(this);
            final ListView listview = new ListView(this);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(editText);
            layout.addView(listview);
            myDialog.setView(layout);
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cityLST);

            editText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    adapter.getFilter().filter(s);
                }
            });

            listview.setAdapter(adapter);

            final AlertDialog alert = myDialog.create();
            alert.show();

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Log.d(TAG, "Selected Item is = " + listview.getItemAtPosition(position));
                    edtDoctorBookAppt.setText("" + listview.getItemAtPosition(position));
                    alert.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "Make sure you have selected Date / Time / Pharmacy / Doctor's Speciality ", Toast.LENGTH_LONG).show();
        }

    }

    private int selectedDoctor() {
        final DoctorsListByDateAndTimeModel doctorsListByDateAndTimeModel = new DoctorsListByDateAndTimeModel();
        doctorsListByDateAndTimeModel.setName(edtDoctorBookAppt.getText().toString());
        //return listDoctorsListByDateAndTimeModel.indexOf(doctorsListByDateAndTimeModel);
        if (listDoctorsListByDateAndTimeModel != null) {
            for (int i = 0; i < listDoctorsListByDateAndTimeModel.size(); i++) {
                if (listDoctorsListByDateAndTimeModel.get(i).getName().equalsIgnoreCase(doctorsListByDateAndTimeModel.getName())) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int selectedSpeciality() {
        final Specialities specialities = new Specialities();
        specialities.setName(editSpecialityBookAppt.getText().toString());
        //return listDoctorsListByDateAndTimeModel.indexOf(doctorsListByDateAndTimeModel);
        if (specialitiesList != null) {
            for (int i = 0; i < specialitiesList.size(); i++) {
                if (specialitiesList.get(i).getName().equalsIgnoreCase(specialities.getName())) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void showAndSetTimePopup(final EditText editText) {
        calendarTime = Calendar.getInstance();
        int CalendarHour, CalendarMinute;
        CalendarHour = calendarTime.get(Calendar.HOUR_OF_DAY);
        CalendarMinute = calendarTime.get(Calendar.MINUTE);
        timepickerdialog = new TimePickerDialog(this,
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

    private void CallGetAppointmentOptions() {
        showProgressDialog();
        Call<AppointmentOptionsResponse> getPatientById = App.apiService.getAppointmentOptions();
        getPatientById.enqueue(new Callback<AppointmentOptionsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AppointmentOptionsResponse> call, Response<AppointmentOptionsResponse> response) {
                hideProgressDialog();
                Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                if (response.code() == 200) {
                    Log.i(TAG, "Labs>>>size>>>>>>>" + response.body().getAppointmentOptions().getLabs().size());
                    Log.i(TAG, "Pharmacy>>>size>>>" + response.body().getAppointmentOptions().getPharmacys().size());
                    Log.i(TAG, "Radiology>>>size>>>" + response.body().getAppointmentOptions().getRadiologys().size());
                    if (response.body().getAppointmentOptions().getPharmacys() != null
                            &&
                            response.body().getAppointmentOptions().getPharmacys().size() > 0) {
                        pharmacyList = response.body().getAppointmentOptions().getPharmacys();

                        specialitiesList = response.body().getAppointmentOptions().getSpecialities();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<AppointmentOptionsResponse> call, Throwable t) {
                hideProgressDialog();
                t.fillInStackTrace();
            }
        });
    }

    private void bindPharmacyOptions() {
        if (pharmacyList != null && pharmacyList.size() > 0) {
            final ArrayList<String> dataList = new ArrayList<>(pharmacyList.size());

            for (int i = 0; i < pharmacyList.size(); i++) {
                dataList.add(pharmacyList.get(i).getName());
            }

            AlertDialog.Builder myDialog = new AlertDialog.Builder(this, R.style.LightDialogTheme);
            final EditText editText = new EditText(this);
            final ListView listview = new ListView(this);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(editText);
            layout.addView(listview);
            myDialog.setView(layout);
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);

            editText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    adapter.getFilter().filter(s);
                }
            });

            listview.setAdapter(adapter);

            final AlertDialog alert = myDialog.create();
            alert.show();

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Log.d(TAG, "Selected Item is = " + listview.getItemAtPosition(position));
                    editPharmacyBookAppt.setText("" + listview.getItemAtPosition(position));
                    alert.dismiss();
                }
            });
        }
    }

    private void bindSpecialities() {
        if (specialitiesList != null && specialitiesList.size() > 0) {
            final ArrayList<String> dataList = new ArrayList<>(specialitiesList.size());

            for (int i = 0; i < specialitiesList.size(); i++) {
                dataList.add(specialitiesList.get(i).getName());
            }

            AlertDialog.Builder myDialog = new AlertDialog.Builder(this, R.style.LightDialogTheme);
            final EditText editText = new EditText(this);
            final ListView listview = new ListView(this);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(editText);
            layout.addView(listview);
            myDialog.setView(layout);
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);

            editText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    adapter.getFilter().filter(s);
                }
            });

            listview.setAdapter(adapter);

            final AlertDialog alert = myDialog.create();
            alert.show();

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Log.d(TAG, "Selected Item is = " + listview.getItemAtPosition(position));
                    editSpecialityBookAppt.setText("" + listview.getItemAtPosition(position));
                    alert.dismiss();
                    callAvailableDoctors();
                }
            });
        }
    }

    private void openFilesDirectory() {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("image/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    public boolean checkPermissionForReadWriteExtertalStorage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadWriteExtertalStorage(Context context) throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context,
                    PERMISSION_PICTURES,
                    WRITE_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //openFilesDirectory();
                }
            case 3:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFilesDirectory();
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                Uri imageUri = FileUtil.writeInputSteamToCache(BookAppointmentActivity.this, inputStream);
                if (null != imageUri) {
                    Log.i(TAG, "filePath>>>>>>>>" + imageUri);
                    tvExtraFilePath.setText(imageUri.toString());
                    return;
                }
            } catch (Exception e) {
                Log.e(TAG, " onActivityResult :: " + e.getMessage());
            }
        }
    }
}

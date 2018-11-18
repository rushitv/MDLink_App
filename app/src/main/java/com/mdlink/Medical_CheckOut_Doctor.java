package com.mdlink;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mdlink.adapter.CheckboxAdapter;
import com.mdlink.model.AddPrescriptionDoctorSide;
import com.mdlink.model.AppointmentListResponseDetails;
import com.mdlink.model.AppointmentOptionsResponse;
import com.mdlink.model.DoctorsListByDateAndTimeModel;
import com.mdlink.model.OptionDetails;
import com.mdlink.model.PharmacyResponse;
import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;
import com.mdlink.util.ValidationsUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Medical_CheckOut_Doctor extends BaseActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    int mYear, mMonth, mDay;
    List<OptionDetails> pharmacyList;
    List<OptionDetails> labList;
    List<OptionDetails> radiologyList;
    List<String> radiologyType = Constants.radiology_type;
    RecyclerView rvCheckboxList;
    private List<String> currentSelectedItems = new ArrayList<>();
    CheckboxAdapter checkboxAdapter;
    private EditText editPharmacyCheckout, editFaxCheckout,edtLabCheckout,edtRadiologyLab,
            edtRadiologyType,edtSickRequestDate,
            edtPatientAddressCheckout,edtPatientPhoneNumberCheckout,edtPatientEmailCheckout,edtPatientNameCheckout,
            edtBirthdateCheckout,edtAgeCheckout,edtOtherLabTest, edtPrescriptionRequest,edtDateCheckout,edtMedicalHistoryCheckout,
            edtRadiologyRequest,edtDiagnosis;
    private Spinner spnMedicalRecomm,spnGender;
    private String AppointmentId, PatientId;
    private SharedPreferenceManager sharedPreferenceManager;
    private TextView tvSubmitMedicalCheckout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical__check_out__doctor);
        initToolbar();
        tvSubmitMedicalCheckout = findViewById(R.id.tvSubmitMedicalCheckout);
        tvSubmitMedicalCheckout.setOnClickListener(this);
        if(getIntent().getStringExtra(Constants.APPOINTMENT_ID)!=null &&
                getIntent().getStringExtra(Constants.PATIENT_ID)!=null)
        {
            AppointmentId = getIntent().getStringExtra(Constants.APPOINTMENT_ID);
            PatientId =getIntent().getStringExtra(Constants.PATIENT_ID);
        }

        sharedPreferenceManager = new SharedPreferenceManager(this);
        edtDateCheckout = findViewById(R.id.edtDateCheckout);
        edtMedicalHistoryCheckout = findViewById(R.id.edtMedicalHistoryCheckout);
        edtDiagnosis = findViewById(R.id.edtDiagnosis);

        edtDateCheckout.setText(ValidationsUtil.getCurrentDate());

        spnMedicalRecomm = findViewById(R.id.spnMedicalRecomm);
        spnGender = findViewById(R.id.spnGender);

        edtSickRequestDate = findViewById(R.id.edtSickRequestDate);
        edtRadiologyType= findViewById(R.id.edtRadiologyType);
        edtLabCheckout= findViewById(R.id.edtLabCheckout);
        edtRadiologyLab = findViewById(R.id.edtRadiologyLab);
        edtRadiologyLab.setOnClickListener(this);
        edtLabCheckout.setOnClickListener(this);
        edtRadiologyType.setText("None");
        edtRadiologyType.setOnClickListener(this);
        editPharmacyCheckout = findViewById(R.id.editPharmacyCheckout);
        editPharmacyCheckout.setOnClickListener(this);

        edtPatientAddressCheckout = findViewById(R.id.edtPatientAddressCheckout);
        edtPatientPhoneNumberCheckout = findViewById(R.id.edtPatientPhoneNumberCheckout);
        edtPatientEmailCheckout = findViewById(R.id.edtPatientEmailCheckout);
        edtPatientNameCheckout = findViewById(R.id.edtPatientNameCheckout);
        edtBirthdateCheckout = findViewById(R.id.edtBirthdateCheckout);
        edtAgeCheckout = findViewById(R.id.edtAgeCheckout);
        edtOtherLabTest = findViewById(R.id.edtOtherLabTest);
        edtPrescriptionRequest = findViewById(R.id.edtPrescriptionRequest);

        edtRadiologyRequest = findViewById(R.id.edtRadiologyRequest);

        editFaxCheckout = findViewById(R.id.editFaxCheckout);

        rvCheckboxList = findViewById(R.id.rvCheckboxList);

        rvCheckboxList.setNestedScrollingEnabled(false);
        checkboxAdapter = new CheckboxAdapter(this, Constants.listLabList, new CheckboxAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(String item) {
                currentSelectedItems.add(item);
            }

            @Override
            public void onItemUncheck(String item) {
                currentSelectedItems.remove(item);
            }
        });
        rvCheckboxList.setAdapter(checkboxAdapter);
        if(getIntent().getStringExtra(Constants.PATIENT_ID)!=null){
            callGetPatientProfileAPI(getIntent().getStringExtra(Constants.PATIENT_ID));
        }

        CallGetAppointmentOptions();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_medical_checkout), R.color.colorAccent);
    }


    private OptionDetails selectedPharmacyOptionObject() {
        for (int i = 0; i < pharmacyList.size(); i++) {
            if (pharmacyList.get(i).getName().equalsIgnoreCase(editPharmacyCheckout.getText().toString())) {
                return pharmacyList.get(i);
            }
        }
        return null;
    }

    private OptionDetails selectedLabOptionObject() {
        for (int i = 0; i < labList.size(); i++) {
            if (labList.get(i).getName().equalsIgnoreCase(edtLabCheckout.getText().toString())) {
                return labList.get(i);
            }
        }
        return null;
    }

    private OptionDetails selectedRadioLabOptionObject() {
        for (int i = 0; i < radiologyList.size(); i++) {
            if (radiologyList.get(i).getName().equalsIgnoreCase(edtRadiologyLab.getText().toString())) {
                return radiologyList.get(i);
            }
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmitMedicalCheckout:
                submitToAPI();
                break;
            case R.id.editPharmacyCheckout:
                bindPharmacyOptions();
                break;
            case R.id.edtLabCheckout:
                bindLabOptions();
                break;
            case R.id.edtRadiologyLab:
                bindRadiologyOptions();
                break;
            case R.id.edtRadiologyType:
                bindRadiologyType();
                break;
            case R.id.edtSickRequestDate:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtSickRequestDate.setText(year+"-"+(monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
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
                        labList = response.body().getAppointmentOptions().getLabs();
                        radiologyList = response.body().getAppointmentOptions().getRadiologys();
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
                    editPharmacyCheckout.setText("" + listview.getItemAtPosition(position));
                    if(selectedPharmacyOptionObject().getFaxNumber()!=null){
                        Log.i(TAG,">>>>>>>"+selectedPharmacyOptionObject().getFaxNumber());
                        editFaxCheckout.setText(selectedPharmacyOptionObject().getFaxNumber());
                    }else {
                        Log.i(TAG,">>>null>>>>");
                    }
                    alert.dismiss();
                }
            });
        }
    }

    private void callPharmacyById(int PharmacyId){
        showProgressDialog();
        Call<PharmacyResponse> getPatientById = App.apiService.getPharmacyById(PharmacyId);
        getPatientById.enqueue(new Callback<PharmacyResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PharmacyResponse> call, Response<PharmacyResponse> response) {
                hideProgressDialog();
                Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                if (response.code() == 200) {
                    Log.i(TAG, "Labs>>>size>>>>>>>" + response.body());
                    if (response.body() != null) {
                     Log.i(TAG,"pharmacy_email>>>>>>>>>"+response.body().getPharmacyResponseDetail().getEmail());
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<PharmacyResponse> call, Throwable t) {
                hideProgressDialog();
                t.fillInStackTrace();
            }
        });
    }

    private void bindLabOptions() {
        if (labList != null && labList.size() > 0) {
            final ArrayList<String> dataList = new ArrayList<>(labList.size());

            for (int i = 0; i < labList.size(); i++) {
                dataList.add(labList.get(i).getName());
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
                    edtLabCheckout.setText("" + listview.getItemAtPosition(position));
                    alert.dismiss();
                }
            });
        }
    }

    private void bindRadiologyOptions() {
        if (radiologyList != null && radiologyList.size() > 0) {
            final ArrayList<String> dataList = new ArrayList<>(radiologyList.size());

            for (int i = 0; i < radiologyList.size(); i++) {
                dataList.add(radiologyList.get(i).getName());
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
                    edtRadiologyLab.setText("" + listview.getItemAtPosition(position));
                    alert.dismiss();
                }
            });
        }
    }

    private void bindRadiologyType(){
        //radiology_type

        if (radiologyType != null && radiologyType.size() > 0) {

            AlertDialog.Builder myDialog = new AlertDialog.Builder(this, R.style.LightDialogTheme);
            final EditText editText = new EditText(this);
            final ListView listview = new ListView(this);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(editText);
            layout.addView(listview);
            myDialog.setView(layout);
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, radiologyType);

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
                    edtRadiologyType.setText(""+listview.getItemAtPosition(position));
                    alert.dismiss();
                }
            });
        }
    }

    private void callGetPatientProfileAPI(String PatientId) {
        showProgressDialog();
        Call<JsonObject> getPatientById = App.apiService.getPatientProfile(PatientId);
        getPatientById.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                hideProgressDialog();
                Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                if (response.code() == 200) {
                    Log.i(TAG, "2>>>>>>>>>>>>>" + response.body());
                    JsonObject jsonObject = response.body().getAsJsonObject("result");
                    Log.i(TAG, "2>>name>>>>>>>>>>>" +jsonObject.get("name"));
                    Log.i(TAG, "2>>email>>>>>>>>>>>" +jsonObject.get("email"));
                    setPatientFetchedData(jsonObject);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                hideProgressDialog();
                t.fillInStackTrace();
            }
        });
    }

    private void setPatientFetchedData(JsonObject jsonObject){
        hideProgressDialog();
        Log.i(TAG, "2>>name>>>>>>>>>>>" +jsonObject.get("name"));
        Log.i(TAG, "2>>email>>>>>>>>>>>" +jsonObject.get("email"));
        Log.i(TAG, "2>>email>>>>>>>>>>>" +jsonObject.get("phone_no"));

        if(!TextUtils.isEmpty(jsonObject.get("name").getAsString())){
            edtPatientNameCheckout.setText(jsonObject.get("name").getAsString());
        }
        if(!TextUtils.isEmpty(jsonObject.get("email").getAsString())){
            edtPatientEmailCheckout.setText(jsonObject.get("email").getAsString());
        }
        if(!TextUtils.isEmpty(jsonObject.get("birthdate").getAsString())){
            edtBirthdateCheckout.setText(jsonObject.get("birthdate").getAsString());
        }
        if(!TextUtils.isEmpty(jsonObject.get("phone_no").getAsString())){
            edtPatientPhoneNumberCheckout.setText(jsonObject.get("phone_no").getAsString());
        }
        if(!TextUtils.isEmpty(jsonObject.get("address").getAsString())){
            edtPatientAddressCheckout.setText(jsonObject.get("address").getAsString());
        }
        if(jsonObject.get("age").getAsInt()!=0){
            edtAgeCheckout.setText(""+jsonObject.get("age").getAsInt());
        }

    }

    private String Validate(){
        String msg = "";
        if(TextUtils.isEmpty(edtMedicalHistoryCheckout.getText().toString())){
            msg += "Add Medical History \n";
        }
        if(TextUtils.isEmpty(edtDiagnosis.getText().toString())){
            msg += "Add Diagnosis History \n";
        }
        if(TextUtils.isEmpty(editPharmacyCheckout.getText().toString())){
            msg += "Select Pharmacy \n";
        }
        if(TextUtils.isEmpty(edtLabCheckout.getText().toString())){
            msg += "Select Lab \n";
        }
        if(TextUtils.isEmpty(edtRadiologyLab.getText().toString())){
            msg += "Select Radiology Lab \n";
        }
        if(TextUtils.isEmpty(edtRadiologyType.getText().toString())){
            msg += "Select Radiology Type \n";
        }
        if(TextUtils.isEmpty(edtPatientEmailCheckout.getText().toString())){
            msg += "Input Patient Email \n";
        }
        if(TextUtils.isEmpty(edtPatientPhoneNumberCheckout.getText().toString())){
            msg += "Input Patient Phone Number\n";
        }
        return msg;
    }

    private void submitToAPI(){
        if(Validate().length()>0){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert");
            builder.setMessage(Validate())
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }else {

            AddPrescriptionDoctorSide addPrescriptionDoctorSide = new AddPrescriptionDoctorSide();
            addPrescriptionDoctorSide.setBirthDate(edtBirthdateCheckout.getText().toString());
            addPrescriptionDoctorSide.setDiagnosis(edtDiagnosis.getText().toString());
            addPrescriptionDoctorSide.setDocPhoneNo("");
            addPrescriptionDoctorSide.setDoctorEmail(sharedPreferenceManager.getStringData(Constants.USER_NAME));
            addPrescriptionDoctorSide.setDoctorId(sharedPreferenceManager.getStringData(Constants.USER_ID));
            if(spnGender.getSelectedItem().equals("Male")){
                addPrescriptionDoctorSide.setGender("M");
            }else {
                addPrescriptionDoctorSide.setGender("F");
            }
            addPrescriptionDoctorSide.setAge(edtAgeCheckout.getText().toString());
            addPrescriptionDoctorSide.setAppointmentId(AppointmentId);
            addPrescriptionDoctorSide.setLabName(selectedLabOptionObject().getEmail());
            addPrescriptionDoctorSide.setLabRequestNote(edtOtherLabTest.getText().toString());
            addPrescriptionDoctorSide.setNotes(edtMedicalHistoryCheckout.getText().toString());
            addPrescriptionDoctorSide.setPatientAddress(edtPatientAddressCheckout.getText().toString());
            addPrescriptionDoctorSide.setPatientEmail(edtPatientEmailCheckout.getText().toString());
            addPrescriptionDoctorSide.setPatientName(edtPatientNameCheckout.getText().toString());
            addPrescriptionDoctorSide.setPhoneNo(edtPatientPhoneNumberCheckout.getText().toString());
            addPrescriptionDoctorSide.setPatientId(PatientId);
            addPrescriptionDoctorSide.setPrescriptionDate(edtDateCheckout.getText().toString());

            StringBuilder checkboxExtract = new StringBuilder();
            for (String checkVal : currentSelectedItems) {
                checkboxExtract.append(checkVal);
                checkboxExtract.append(Constants.SEPARATOR);
            }
            addPrescriptionDoctorSide.setTest(checkboxExtract.toString());
            addPrescriptionDoctorSide.setPrescription(edtPrescriptionRequest.getText().toString());
            addPrescriptionDoctorSide.setPharmacyName(selectedPharmacyOptionObject().getEmail());
            addPrescriptionDoctorSide.setPharmacyFaxNumber(editFaxCheckout.getText().toString());
            addPrescriptionDoctorSide.setRadiologyEmail(selectedRadioLabOptionObject().getEmail());
            addPrescriptionDoctorSide.setRadiologyRequest(edtRadiologyRequest.getText().toString());
            addPrescriptionDoctorSide.setRadiologyType(edtRadiologyType.getText().toString());
            addPrescriptionDoctorSide.setDoctorName(sharedPreferenceManager.getStringData(Constants.NAME));
            callSubmitPrescriptionAPI(addPrescriptionDoctorSide);
            //Gson gson = new Gson();
            //System.out.println(">>>>>>>>>>"+gson.toJson(addPrescriptionDoctorSide));
        }
    }

    private void callSubmitPrescriptionAPI(AddPrescriptionDoctorSide addPrescriptionDoctorSide){
        showProgressDialog();
        Call<JsonObject> callToGetUserProfile = App.apiService.prescription(addPrescriptionDoctorSide);
        callToGetUserProfile.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideProgressDialog();
                Log.i(TAG, ">>>>>>>>>>>>>>>>>" + response.body());
                if (response.body().get("status").getAsString().equalsIgnoreCase("200")) {
                    Toast.makeText(Medical_CheckOut_Doctor.this, response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Medical_CheckOut_Doctor.this, DoctorPortalActivity.class);
                    finish();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(Medical_CheckOut_Doctor.this, response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
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

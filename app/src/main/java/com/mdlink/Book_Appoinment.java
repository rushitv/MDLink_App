package com.mdlink;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mdlink.api.APIService;
import com.mdlink.api.RestAPIClent;
import com.mdlink.model.BookAppointmentRequest;
import com.mdlink.model.DoctorsListByDateAndTimeModel;
import com.mdlink.model.DoctorsListModel;
import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;
import com.mdlink.util.ValidationsUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Book_Appoinment extends BaseActivity implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();
    int mYear, mMonth, mDay;
    Spinner spnrPharmacyBookAppt, spinnerdoc;
    String phamacysel, docsel;
    StringBuilder sb;
    CheckBox cbRenew, cbSickNote, cbCancerBookAppt, cbDiabetesBookAppt, cbHeartDiseasBookAppt, cbStrokeBookAppt, cbHighBPBookAppt, cbHighCholesterolBookAppt, cbHighAsthmaBookAppt, cbDepressionBookAppt, cbArthritisBookAppt, cbAbnormalThyroidBookAppt, cbAPregnantBookAppt, cbOtherBookAppt;
    TextView submitbook;
    String[] pharmacy = {"Select pharmacy", "Liguanea Lane Pharmacy-liglanedidp@yahoo.com", "C-cheabowen@gmail.com", "B Pharmacy - unicoreshopping@gmail.com", "Manor Park Pharmacy", "new kingston Pharmacy-nkrxorders@gmail.com", "Musgrave Pharmacy - musgravepharmacyorders@gmail.com"};
    String[] doctor = {"Select Doctor", "JOHN", "A", "b", "c", "d", "E", "F", "G"};

    EditText edtDoctorBookAppt, edtChooseTimeBookAppt, edtDateBookAppt, edtCouponCodeBookAppt,
            edtLocationBookAppt, edtPreviousVisitedHospitalBookAppt, edtAllergiesBookAppt,
            edtNameBookAppt, edtAgeBookAppt, edtPurposeBookAppt;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__appoinment);
        initToolbar();
        edtNameBookAppt = findViewById(R.id.edtNameBookAppt);
        edtAgeBookAppt = findViewById(R.id.edtAgeBookAppt);
        edtPurposeBookAppt = findViewById(R.id.edtPurposeBookAppt);
        edtLocationBookAppt = findViewById(R.id.edtLocationBookAppt);

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

        spnrPharmacyBookAppt = findViewById(R.id.spnrPharmacyBookAppt);
        spinnerdoc = findViewById(R.id.doc_spinner);

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

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(Book_Appoinment.this, android.R.layout.simple_spinner_dropdown_item, pharmacy);
        spnrPharmacyBookAppt.setAdapter(stringArrayAdapter);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(Book_Appoinment.this, android.R.layout.simple_spinner_dropdown_item, doctor);
        spinnerdoc.setAdapter(Adapter);


        spnrPharmacyBookAppt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                phamacysel = (String) parent.getItemAtPosition(position);

                Toast.makeText(Book_Appoinment.this, phamacysel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerdoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                docsel = parent.getItemAtPosition(position).toString();
                Toast.makeText(Book_Appoinment.this, docsel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (new ConnectionCall(Book_Appoinment.this).isConnectingToInternet()) {
            // call api
        } else new ConnectionCall(Book_Appoinment.this).connectiondetect();

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
                if(spnrPharmacyBookAppt.getSelectedItemPosition() == 0){
                    Toast.makeText(this, "Please select pharmacy",Toast.LENGTH_LONG).show();
                    return;
                }

                int selectedId = rgHowToConnect.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                rbHowToConnectVal = findViewById(selectedId);
                if(rbHowToConnectVal != null) {
                    Log.i(TAG, ">>>>>>>>" + rbHowToConnectVal.getText());
                    switch (rbHowToConnectVal.getText().toString()) {
                        case "Audio ($12)":
                            type = "1";
                            break;
                        case "Instant Message ($12)":
                            type = "2";
                            break;
                        case "Video Call ($15)":
                            type = "3";
                            break;
                    }
                    Log.i(TAG, "type>>>>>>>>" + type);
                }else {
                    Toast.makeText(this, "Choose How would you like to connect?",Toast.LENGTH_LONG).show();
                    return;
                }

                BookAppointmentRequest bookAppointmentRequest = new BookAppointmentRequest();
                try {
                    if(selectedDoctor() != -1){
                        Log.i(TAG,">>>>>"+selectedDoctor());
                        Log.i(TAG, ">>doctor>>id>>>>>" + listDoctorsListByDateAndTimeModel.get(selectedDoctor()).getId());
                        bookAppointmentRequest.setPreferredDoctor(""+listDoctorsListByDateAndTimeModel.get(selectedDoctor()).getId());
                    }else {
                        Toast.makeText(this,"Please choose doctor",Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (ArrayIndexOutOfBoundsException exception) {
                    bookAppointmentRequest.setPreferredDoctor("0");
                }


                ArrayList<String> stringArrayList = new ArrayList<>();
                if (cbCancerBookAppt.isChecked()) {
                    stringArrayList.add(cbCancerBookAppt.getText().toString());
                }
                if (cbDiabetesBookAppt.isChecked()) {
                    stringArrayList.add(cbCancerBookAppt.getText().toString());
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

                bookAppointmentRequest.setAge(edtAgeBookAppt.getText().toString());
                bookAppointmentRequest.setAllergy(edtAllergiesBookAppt.getText().toString());
                bookAppointmentRequest.setIsRenew(cbRenew.isChecked() ? "1":"0");
                bookAppointmentRequest.setSickNote(cbSickNote.isChecked() ? "1":"0");
                bookAppointmentRequest.setCouponCode(edtCouponCodeBookAppt.getText().toString());
                bookAppointmentRequest.setMedicalConditions(String.valueOf(checkboxMedicalCondition));
                bookAppointmentRequest.setLocation(edtLocationBookAppt.getText().toString());
                bookAppointmentRequest.setName(edtNameBookAppt.getText().toString());
                bookAppointmentRequest.setPharmacy(spnrPharmacyBookAppt.getSelectedItem().toString());
                bookAppointmentRequest.setPreviousHospital(edtPreviousVisitedHospitalBookAppt.getText().toString());
                bookAppointmentRequest.setScheduledDate(edtDateBookAppt.getText().toString());
                bookAppointmentRequest.setScheduledTime(edtChooseTimeBookAppt.getText().toString());
                bookAppointmentRequest.setType(type);
                bookAppointmentRequest.setUserId(sharedPreferenceManager.getStringData("UserId"));
                callCreateAppointmentAPI(bookAppointmentRequest);

                break;
        }
    }

    private void callCreateAppointmentAPI(BookAppointmentRequest bookAppointmentRequest) {
        Call<JsonObject> callToGetUserProfile = apiService.createAppointment(bookAppointmentRequest);
        callToGetUserProfile.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i(TAG, ">>>>>>>>>>>>>>>>>" + response.body());
                if (response.body().get("status").getAsString().equalsIgnoreCase("200")) {
                    Toast.makeText(Book_Appoinment.this,response.body().get("message").getAsString(),Toast.LENGTH_LONG).show();
                    finish();
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

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("scheduled_date", edtDateBookAppt.getText().toString());
        hashMap.put("scheduled_time", edtChooseTimeBookAppt.getTag().toString());
        Call<DoctorsListModel> callToGetUserProfile = apiService.availabledoctors(hashMap);
        callToGetUserProfile.enqueue(new Callback<DoctorsListModel>() {
            @Override
            public void onResponse(Call<DoctorsListModel> call, Response<DoctorsListModel> response) {
                Log.i(TAG, ">>>>>>>>>>>>>>>>>" + response.body());
                if (response.body().getResult().size() > 0) {
                    listDoctorsListByDateAndTimeModel = response.body().getResult();
                }
            }

            @Override
            public void onFailure(Call<DoctorsListModel> call, Throwable t) {
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
        }else {
            Toast.makeText(this,"Please choose date and time",Toast.LENGTH_LONG).show();
        }

    }

    private int selectedDoctor() {
        final DoctorsListByDateAndTimeModel doctorsListByDateAndTimeModel = new DoctorsListByDateAndTimeModel();
        doctorsListByDateAndTimeModel.setName(edtDoctorBookAppt.getText().toString());
        //return listDoctorsListByDateAndTimeModel.indexOf(doctorsListByDateAndTimeModel);
        for (int i = 0; i < listDoctorsListByDateAndTimeModel.size(); i++) {
            if (listDoctorsListByDateAndTimeModel.get(i).getName().equalsIgnoreCase(doctorsListByDateAndTimeModel.getName())) {
                return i;
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
                        callAvailableDoctors();
                    }
                    //DisplayTime.setText(hourOfDay + ":" + minute + format);

                }, CalendarHour, CalendarMinute, false);
        timepickerdialog.show();
    }
}

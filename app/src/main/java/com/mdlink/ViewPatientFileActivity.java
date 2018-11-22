package com.mdlink;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mdlink.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewPatientFileActivity extends BaseActivity {
    private Toolbar toolbar;
    private TextView txtRenewApptForCA, txtSicknoteApptForCA, txtApptTypeCA, txtNameCA, txtAgeCA, txtPurposeCA,
            txtPreviousHospitalCA,
            txtAllergiesCA, txtMedicalConditionCA, txtPharmacyCA, txtLocationCA, txtDateCA, txtTimeCA,
            txtPreferredDoctorCA, txtPayByPaypalCA, txtApptPaymentStatusCA;
    private LinearLayout llPaymentStatus;
    private int AppointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_appt);
        initToolbar();
        initViews();
        if (getIntent().getIntExtra(Constants.APPOINTMENT_ID,0) != 0) {
            AppointmentId = getIntent().getIntExtra(Constants.APPOINTMENT_ID,0);
            getDataFromServer(AppointmentId);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_view_patient_file), R.color.colorAccent);
    }

    private void initViews() {
        llPaymentStatus = findViewById(R.id.llPaymentStatus);
        llPaymentStatus.setVisibility(View.VISIBLE);
        txtApptPaymentStatusCA = findViewById(R.id.txtApptPaymentStatusCA);

        txtRenewApptForCA = findViewById(R.id.txtRenewApptForCA);
        txtSicknoteApptForCA = findViewById(R.id.txtSicknoteApptForCA);

        txtApptTypeCA = findViewById(R.id.txtApptTypeCA);
        txtNameCA = findViewById(R.id.txtNameCA);
        txtAgeCA = findViewById(R.id.txtAgeCA);
        txtPurposeCA = findViewById(R.id.txtPurposeCA);
        txtAllergiesCA = findViewById(R.id.txtAllergiesCA);
        txtPreviousHospitalCA = findViewById(R.id.txtPreviousHospitalCA);
        txtMedicalConditionCA = findViewById(R.id.txtMedicalConditionCA);
        txtPharmacyCA = findViewById(R.id.txtPharmacyCA);
        txtLocationCA = findViewById(R.id.txtLocationCA);
        txtDateCA = findViewById(R.id.txtDateCA);
        txtTimeCA = findViewById(R.id.txtTimeCA);
        txtPreferredDoctorCA = findViewById(R.id.txtPreferredDoctorCA);

        txtPayByPaypalCA = findViewById(R.id.txtPayByPaypalCA);
        txtPayByPaypalCA.setVisibility(View.GONE);
    }

    private void getDataFromServer(int appointmentId) {
        showProgressDialog();
        Call<JsonObject> getPatientById = App.apiService.getAppointmentById(appointmentId);
        getPatientById.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                hideProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        JsonObject responseObj = response.body().getAsJsonObject("result");
                        if (responseObj.has("name")) {
                            txtNameCA.setText(getString(R.string.colon, responseObj.get("name").getAsString()));
                        }
                        if (responseObj.has("age")) {
                            txtAgeCA.setText(getString(R.string.colon, "" + responseObj.get("age").getAsInt()));
                        }
                        if (responseObj.has("type")) {
                            if (responseObj.get("type").getAsInt() == 3) {
                                txtApptTypeCA.setText("Video Call");
                            } else if (responseObj.get("type").getAsInt() == 2) {
                                txtApptTypeCA.setText("Instant Message");
                            } else if (responseObj.get("type").getAsInt() == 1) {
                                txtApptTypeCA.setText("Audio Call");
                            }
                        }
                        if (responseObj.has("is_payed")) {
                            txtApptPaymentStatusCA.setText(responseObj.get("is_payed").getAsInt() == 0 ? "Pending" : "Paid");
                        }
                        if (responseObj.has("is_renew")) {
                            txtRenewApptForCA.setText(getString(R.string.i_would_to_renew_refill_a_prescription,
                                    responseObj.get("is_renew").getAsInt() == 0 ? "No" : "Yes"));
                        }
                        if (responseObj.has("sick_note")) {
                            txtSicknoteApptForCA.setText(getString(R.string.i_would_to_obtain_sicknote,
                                    responseObj.get("sick_note").getAsInt() == 0 ? "No" : "Yes"));
                        }
                        if (responseObj.has("visit_purpose")) {
                            txtPurposeCA.setText(getString(R.string.colon, responseObj.get("visit_purpose").getAsString()));
                        }
                        if (responseObj.has("allergy")) {
                            txtAllergiesCA.setText(getString(R.string.colon, responseObj.get("allergy").getAsString()));
                        }
                        if (responseObj.has("pharmacy")) {
                            txtPharmacyCA.setText(getString(R.string.colon, responseObj.get("pharmacy").getAsString()));
                        }
                        if (responseObj.has("location")) {
                            txtLocationCA.setText(getString(R.string.colon, responseObj.get("location").getAsString()));
                        }
                        if (responseObj.has("scheduled_date")) {
                            txtDateCA.setText(getString(R.string.colon, responseObj.get("scheduled_date").getAsString()));
                        }
                        if (responseObj.has("scheduled_time")) {
                            txtTimeCA.setText(getString(R.string.colon, responseObj.get("scheduled_time").getAsString()));
                        }
                        if (responseObj.has("preferred_doctor")) {
                            txtPreferredDoctorCA.setText(getString(R.string.colon, responseObj.get("preferred_doctor").getAsString()));
                        }

                        if(responseObj.has("medical_conditions")){
                            txtMedicalConditionCA.setText(getString(R.string.medicalcondition,responseObj.get("medical_conditions").getAsString()));
                        }

                        if(responseObj.has("preferred_doctor")){
                            txtPreviousHospitalCA.setText(responseObj.get("preferred_doctor").getAsString());
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

}

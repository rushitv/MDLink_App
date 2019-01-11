package com.mdlinkhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mdlinkhealth.model.DoctorProfile;
import com.mdlinkhealth.preferences.SharedPreferenceManager;
import com.mdlinkhealth.util.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    TextView tv;
    EditText email, password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing__up__doctor);
        initToolbar();

        tv = findViewById(R.id.singup_submit);
        email = findViewById(R.id.login_doc_email);
        password = findViewById(R.id.login_doc_password);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().length() == 0 || email.getText().toString().matches(emailPattern)) {
                } else {
                    email.setError("Enter Valid Email");
                    return;
                }

                if (password.getText().toString().length() == 0) {
                    password.setError("Password is required!");
                    password.requestFocus();
                }

                if (new ConnectionCall(LoginActivity.this).connectiondetect()) {
                    HashMap<String, String> loginRequestHashmap = new HashMap<>();
                    loginRequestHashmap.put("email", email.getText().toString());
                    loginRequestHashmap.put("password", password.getText().toString());
                    loginRequestHashmap.put("device_token", getFCMToken());
                    loginRequestHashmap.put("device_type", Constants.DEVICE_TYPE);
                    callLoginAPI(loginRequestHashmap);
                } else {
                    new ConnectionCall(LoginActivity.this).isConnectingToInternet();
                }

            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_login), R.color.colorAccent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void callLoginAPI(HashMap<String, String> hashMapLoginRequest) {
        hideKeyboard(getBaseActivity());
        showProgressDialog();
        Call<JsonObject> getPatientById = App.apiService.login(hashMapLoginRequest);
        getPatientById.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                hideProgressDialog();
                Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                if (response.code() == 200) {
                    Log.i(TAG, "2>>>>>>>>>>>>>" + response.body());
                    if (response.body().get("status").getAsInt() == 200) {
                        JsonObject jsonObject = response.body().getAsJsonObject("result");
                        setDataToPreference(jsonObject);
                    } else {
                        Toast.makeText(LoginActivity.this,
                                response.body().get("message").getAsString(),
                                Toast.LENGTH_LONG).show();
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

    private void setDataToPreference(JsonObject jsonObject) {
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(LoginActivity.this);
        sharedPreferenceManager.saveString(Constants.USER_NAME, email.getText().toString());
        sharedPreferenceManager.saveString(Constants.ROLE_ID, jsonObject.get("role_id").getAsString());
        if (jsonObject.get("role_id").getAsString().equalsIgnoreCase("1")) {
            Gson gson = new Gson();
            DoctorProfile doctorProfile = gson.fromJson(jsonObject.toString(), DoctorProfile.class);
            Log.i(TAG, "age>>>>>" + doctorProfile.getAge());
            String json = gson.toJson(doctorProfile);
            sharedPreferenceManager.saveString(Constants.DOCTOR_PROFILE, json);
        }
        sharedPreferenceManager.saveString(Constants.USER_ID, jsonObject.get("id").getAsString());
        if (jsonObject.has("age")) {
            sharedPreferenceManager.saveString(Constants.AGE, jsonObject.get("age").getAsString());
        }
        if (jsonObject.has("name")) {
            sharedPreferenceManager.saveString(Constants.NAME, jsonObject.get("name").getAsString());
        }
        if (jsonObject.has("location")) {
            sharedPreferenceManager.saveString(Constants.LOCATION, jsonObject.get("location").getAsString());
        }
        if (jsonObject.has("birthdate")) {
            sharedPreferenceManager.saveString(Constants.BIRTH_DATE, jsonObject.get("birthdate").getAsString());
        }

        if (jsonObject.get("role_id").getAsString().equalsIgnoreCase("1")) {
            Intent intent = new Intent(LoginActivity.this, DoctorPortalActivity.class);
            intent.putExtra(Constants.USER_NAME, jsonObject.get("name").getAsString());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoginActivity.this, PatientPortalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(Constants.USER_NAME, jsonObject.get("name").getAsString());
            startActivity(intent);
        }
    }
}

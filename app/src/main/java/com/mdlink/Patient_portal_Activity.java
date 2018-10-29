package com.mdlink;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.mdlink.asynctask.PatientRegisterAsyncTask;
import com.mdlink.util.ValidationsUtil;

import java.util.Calendar;
import java.util.HashMap;

public class Patient_portal_Activity extends BaseActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    TextView tvPatient_submit, tvSignIn;
    EditText editEmail,editFullName, editPhone, editAge, editBirthdate, editAddress, editPassword, editConfirmPassword;
    CountryCodePicker ccp;
    HashMap<String, String> hashMap = new HashMap<>();
    int mYear,mMonth,mDay;
    String CountryCode="";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_portal_);
        initToolbar();

        tvPatient_submit = (TextView) findViewById(R.id.patient_submit);
        tvSignIn = (TextView)findViewById(R.id.textview_sing);
        ccp = findViewById(R.id.ccp);
        CountryCode = ccp.getSelectedCountryCode();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                CountryCode = "";
               // Toast.makeText(getContext(), "Updated " + ccp.getSelectedCountryName(), Toast.LENGTH_SHORT).show();
                CountryCode = ccp.getSelectedCountryCode();
            }
        });
        editEmail = findViewById(R.id.editEmail);
        editFullName = findViewById(R.id.editFullName);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);
        editAge = findViewById(R.id.editAge);
        editBirthdate = findViewById(R.id.editBirthdate);
        editBirthdate.setOnClickListener(this);

        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);

        tvPatient_submit.setOnClickListener(this);

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_portal_Activity.this,Login_Doctor.class);
                startActivity(intent);
            }
        });

    }
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_doctorregistration), R.color.colorAccent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.patient_submit:
                if(isFormValid().length()>0) {
                    Toast.makeText(Patient_portal_Activity.this,"",Toast.LENGTH_LONG).show();
                }else {
                    hashMap.clear();
                    Log.i(TAG,"CountryCode>>>>>"+CountryCode);
                    hashMap.put("email", editEmail.getText().toString());
                    hashMap.put("name", editFullName.getText().toString());
                    hashMap.put("phone_no", CountryCode + editPhone.getText().toString());
                    hashMap.put("age", editAge.getText().toString());
                    hashMap.put("birthdate", editBirthdate.getText().toString());
                    hashMap.put("address", editAddress.getText().toString());
                    hashMap.put("password", editPassword.getText().toString());
                    hashMap.put("confirmpassword", editConfirmPassword.getText().toString());
                    hashMap.put("terms_and_cond", "1");
                    hashMap.put("userID", editEmail.getText().toString());
                    hashMap.put("role_id", "2");

                    new PatientRegisterAsyncTask(Patient_portal_Activity.this, hashMap).execute();
                }
                break;
            case R.id.editBirthdate:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                editBirthdate.setText(year+"-"+(monthOfYear + 1) +"-"+dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
    }
    private String isFormValid(){
        String str = "";
        if(!TextUtils.isEmpty(editEmail.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()){

        }else {
            str += "Please enter valid email \n";
        }
        if(TextUtils.isEmpty(editPhone.getText().toString())){
            str+=" Please enter phone number \n";
        }
        if(TextUtils.isEmpty(editPassword.getText().toString())){
            str+=" Password should not be empty \n";
        }

        if(TextUtils.isEmpty(editConfirmPassword.getText().toString())){
            str+="Confirm Password should not be empty \n";
        }

        if(!(editPassword.getText().toString().equals(editConfirmPassword.getText().toString()))){
            str+="Passwords do not match! \n";
        }

        return str;

    }


}

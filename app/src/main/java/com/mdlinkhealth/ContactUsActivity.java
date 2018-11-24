package com.mdlinkhealth;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mdlinkhealth.helper.StringHelper;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    Toolbar toolbar;
    private EditText edtName,edtEmail,edtPhone,edtMessage;
    private TextView txtSubmitContactUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_);
        initToolbar();
        initViews();

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_contactus), R.color.colorAccent);
    }

    private void initViews(){
        edtEmail = findViewById(R.id.edtEmailCU);
        edtName = findViewById(R.id.edtNameCU);
        edtPhone = findViewById(R.id.edtPhoneNumberCU);
        edtMessage = findViewById(R.id.edtMessageCU);
        txtSubmitContactUs = findViewById(R.id.tvContactUsSubmit);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){

            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private String Validate(){
        String msg = "";
        if(!StringHelper.isValidEmail(edtEmail.getText().toString())){
            msg +="Please input valid email \n";
            edtEmail.setError("Please input valid email");
        }
        if(TextUtils.isEmpty(edtName.getText().toString())){
            msg +="Please input name \n";
            edtName.setError("Please input name");
        }
        if(TextUtils.isEmpty(edtMessage.getText().toString())){
            msg +="Please input message \n";
            edtMessage.setError("Please input message");
        }
        if(TextUtils.isEmpty(edtPhone.getText().toString())){
            msg +="Please input phone \n";
            edtPhone.setError("Please input phone");
        }
        return msg;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvContactUsSubmit:
                if(Validate().length()>0){
                    Toast.makeText(ContactUsActivity.this,Validate(),Toast.LENGTH_LONG).show();
                }else {
                    callContactUSAPI();
                }
            break;
        }
    }

    private void callContactUSAPI(){
        HashMap<String, String> hashMapInput = new HashMap<>();
        hashMapInput.put("name",edtName.getText().toString());
        hashMapInput.put("email", edtEmail.getText().toString());
        hashMapInput.put("phone_no", edtPhone.getText().toString());
        hashMapInput.put("message", edtMessage.getText().toString());

        Call<JsonObject> callToGetUserProfile = App.apiService.contactUs(hashMapInput);
        callToGetUserProfile.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i(TAG, ">>>>>>>>>>>>>>>>>" + response.body());
                if (response.body().get("status").getAsString().equalsIgnoreCase("200")) {
                    Toast.makeText(ContactUsActivity.this, response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i(TAG, ">>>>>>>>>onFailure>>>>>>>>");
            }
        });
    }

}

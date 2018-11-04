package com.mdlink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {

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
                    new InserData().execute();
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

    private class InserData extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Loading..Hold A Second..");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("email", email.getText().toString());
            hashMap.put("password", password.getText().toString());
            return new MakeServiceCall().MakeServiceCall("http://api.themdlink.com/api/v1/login", MakeServiceCall.POST, hashMap);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            try {
                JSONObject json = new JSONObject(s);

                if (json.getString("status").equalsIgnoreCase("200")) {
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                    JSONObject jsonArray = json.getJSONObject("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //JSONObject object = jsonArray.getJSONObject("i");
                        //Toast.makeText(LoginActivity.this,jsonArray.getString("role_id"),Toast.LENGTH_SHORT).show();

                        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(LoginActivity.this);
                        sharedPreferenceManager.saveString(Constants.USER_NAME,email.getText().toString());
                        sharedPreferenceManager.saveString(Constants.ROLE_ID,jsonArray.getString("role_id"));
                        sharedPreferenceManager.saveString(Constants.USER_ID,jsonArray.getString("id"));
                        if(jsonArray.has("age")){
                            sharedPreferenceManager.saveString(Constants.AGE,jsonArray.getString("age"));
                        }
                        if(jsonArray.has("name")){
                            sharedPreferenceManager.saveString(Constants.NAME,jsonArray.getString("name"));
                        }
                        if(jsonArray.has("location")){
                            sharedPreferenceManager.saveString(Constants.LOCATION,jsonArray.getString("location"));
                        }
                        if(jsonArray.has("birthdate")){
                            sharedPreferenceManager.saveString(Constants.BIRTH_DATE,jsonArray.getString("birthdate"));
                        }

                        if (jsonArray.getString("role_id").equalsIgnoreCase("1")) {
                            Intent intent = new Intent(LoginActivity.this, DoctorPortalActivity.class);
                            intent.putExtra(Constants.USER_NAME,jsonArray.getString("name"));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, PatientPortalActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra(Constants.USER_NAME,jsonArray.getString("name"));
                            startActivity(intent);
                        }
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this,"Oops...Something went wrong! Please try later...!",Toast.LENGTH_LONG).show();
            }


        }
    }
}
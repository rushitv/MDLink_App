package com.mdlink;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.Gson;
import com.mdlink.model.DoctorProfile;
import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;

public class DoctorProfileActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    private Toolbar toolbar;
    private EditText edtEmailDPUpdate;
    private SharedPreferenceManager sharedPreferenceManager;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        initToolbar();
        edtEmailDPUpdate = findViewById(R.id.edtEmailDPUpdate);
        sharedPreferenceManager = new SharedPreferenceManager(this);

        String json = sharedPreferenceManager.getStringData(Constants.DOCTOR_PROFILE);
        DoctorProfile obj = gson.fromJson(json, DoctorProfile.class);
        edtEmailDPUpdate.setText(obj.getEmail());
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_doctorprofile), R.color.colorAccent);
    }
}

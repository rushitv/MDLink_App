package com.mdlinkhealth;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.mdlinkhealth.helper.UiHelper;
import com.mdlinkhealth.preferences.SharedPreferenceManager;
import com.mdlinkhealth.util.Constants;

public class UserSettingsActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlPrivacyPolicy, rlTerms, rlFAQs, rlShareApp;
    private Toolbar toolbar;
    private SwitchCompat switchNotificationONOff;
    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        initToolbar();
    }

    private void initViews() {
        sharedPreferenceManager = new SharedPreferenceManager(this);

        rlPrivacyPolicy = findViewById(R.id.rlPrivacyPolicy);
        rlPrivacyPolicy.setOnClickListener(this);
        rlTerms = findViewById(R.id.rlTerms);
        rlTerms.setOnClickListener(this);
        rlFAQs = findViewById(R.id.rlFAQs);
        rlFAQs.setOnClickListener(this);
        rlShareApp = findViewById(R.id.rlShareApp);
        rlShareApp.setOnClickListener(this);

        switchNotificationONOff = findViewById(R.id.switchNotificationONOff);
        switchNotificationONOff.setChecked(sharedPreferenceManager.getBooleanData(Constants.PUSH_NOTIFICATION_STATE));
        switchNotificationONOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                persistChecked(isChecked);
            }
        });
    }

    private void persistChecked(boolean isChecked) {
        sharedPreferenceManager.saveBoolean(Constants.PUSH_NOTIFICATION_STATE, isChecked);
        switchNotificationONOff.setChecked(sharedPreferenceManager.getBooleanData(Constants.PUSH_NOTIFICATION_STATE));
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.action_settings), R.color.colorAccent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlPrivacyPolicy:
                UiHelper.startURLIntent(this, this.getString(R.string.url_privacy_policy));
                break;
            case R.id.rlTerms:
                UiHelper.startURLIntent(this, this.getString(R.string.url_terms));
                break;
            case R.id.rlFAQs:
                UiHelper.startURLIntent(this, this.getString(R.string.url_faqs));
                break;
            case R.id.rlShareApp:
                shareAppIntent();
                break;
        }
    }

    private void shareAppIntent() {
        try {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "iTunes - " + this.getResources().getString(R.string.itunes_link) + "\n \n" +
                    "Google Play - " + this.getResources().getString(R.string.playstore_link);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MDLink Health App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } catch (ActivityNotFoundException e) {
            e.getMessage();
        }
    }
}

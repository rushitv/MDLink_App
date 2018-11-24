package com.mdlinkhealth.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.Window;
import android.view.animation.AlphaAnimation;

import com.mdlinkhealth.BaseActivity;
import com.mdlinkhealth.DoctorPortalActivity;
import com.mdlinkhealth.Guide_Page;
import com.mdlinkhealth.PatientPortalActivity;
import com.mdlinkhealth.R;
import com.mdlinkhealth.chat.ChatClientManager;
import com.mdlinkhealth.chat.SessionManager;
import com.mdlinkhealth.preferences.SharedPreferenceManager;
import com.mdlinkhealth.util.Constants;

public class SplashActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    private AppCompatImageView appCompatImageView;
    SharedPreferenceManager preferenceManager;
    private ChatClientManager clientManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_);
        preferenceManager = new SharedPreferenceManager(this);
        appCompatImageView = findViewById(R.id.logo);

        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(2500);
        appCompatImageView.startAnimation(animation);
        SessionManager.getInstance().logoutUser();
        //clientManager = App.getInstance().getChatClientManager();
      /*  SessionManager.getInstance().createLoginSession("rushit@gmail.com");
        new AsyncTask<Void, Void, String>(
        ) {
            @Override
            protected String doInBackground(Void... voids) {

                initializeChatClient();
                return null;
            }
        }.execute();*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (TextUtils.isEmpty(preferenceManager.getStringData(Constants.USER_ID))) {
                    //Intent intent = new Intent(SplashActivity.this, VoiceActivity.class);
                    //Intent intent = new Intent(SplashActivity.this, Medical_CheckOut_Doctor.class);
                    Intent intent = new Intent(SplashActivity.this, Guide_Page.class);
                    //intent.putExtra(Constants.PATIENT_ID,"3");
                    finish();
                    startActivity(intent);
                } else {
                    Intent intentd;
                    if (preferenceManager.getStringData(Constants.ROLE_ID).equalsIgnoreCase("1")) {
                        intentd = new Intent(SplashActivity.this, DoctorPortalActivity.class);
                        startActivity(intentd);
                        finish();
                    } else {
                        intentd = new Intent(SplashActivity.this, PatientPortalActivity.class);
                        startActivity(intentd);
                        finish();
                    }
                }
            }
        }, 3000);
    }



}

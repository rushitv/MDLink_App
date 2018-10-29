package com.mdlink.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.mdlink.Doctor_Portel_Data;
import com.mdlink.Guide_Page;
import com.mdlink.MainActivity;
import com.mdlink.Patient_Portal_Data;
import com.mdlink.R;
import com.mdlink.preferences.SharedPreferenceManager;

public class SplashActivity extends AppCompatActivity {
    private AppCompatImageView appCompatImageView;
    SharedPreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_);
        preferenceManager = new SharedPreferenceManager(this);
        appCompatImageView = findViewById(R.id.logo);

        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(2500);
        appCompatImageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(preferenceManager.getStringData("UserId"))) {
                    Intent intent = new Intent(SplashActivity.this, Guide_Page.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (preferenceManager.getStringData("Role").equalsIgnoreCase("2")) {
                        Intent intentd = new Intent(SplashActivity.this, Patient_Portal_Data.class);
                        startActivity(intentd);
                        finish();
                    }else {
                        Intent intentd = new Intent(SplashActivity.this, Doctor_Portel_Data.class);
                        startActivity(intentd);
                        finish();
                    }
                }
            }
        }, 3000);

    }
}

package com.mdlink.splash;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;

import com.mdlink.App;
import com.mdlink.BaseActivity;
import com.mdlink.DoctorPortalActivity;
import com.mdlink.Guide_Page;
import com.mdlink.MainActivity;
import com.mdlink.PatientPortalActivity;
import com.mdlink.R;
import com.mdlink.chat.ChatClientManager;
import com.mdlink.chat.MainChatActivity;
import com.mdlink.chat.MessageActivity;
import com.mdlink.chat.SessionManager;
import com.mdlink.chat.listeners.TaskCompletionListener;
import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;

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
        clientManager = App.getInstance().getChatClientManager();
        SessionManager.getInstance().createLoginSession("rushit@gmail.com");


        new AsyncTask<Void, Void, String>(
        ) {
            @Override
            protected String doInBackground(Void... voids) {

                initializeChatClient();
                return null;
            }
        }.execute();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intentd = new Intent(SplashActivity.this, MessageActivity.class);
                startActivity(intentd);
                finish();

                *//*if (TextUtils.isEmpty(preferenceManager.getStringData(Constants.USER_ID))) {
                    Intent intent = new Intent(SplashActivity.this, Guide_Page.class);
                    startActivity(intent);
                    finish();
                } else {

                  *//*


         *//*if (preferenceManager.getStringData(Constants.ROLE_ID).equalsIgnoreCase("1")) {
                        Intent intentd = new Intent(SplashActivity.this, DoctorPortalActivity.class);
                        startActivity(intentd);
                        finish();
                    }else {
                        Intent intentd = new Intent(SplashActivity.this, PatientPortalActivity.class);
                        startActivity(intentd);
                        finish();
                    }*//*
                //}
            }
        }, 3000);*/

    }

    private void initializeChatClient() {

        clientManager.connectClient(new TaskCompletionListener<Void, String>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMainChatActivity();
            }

            @Override
            public void onError(String errorMessage) {

                Log.i(TAG, ">>>>>>>>>>>>>>" + errorMessage);
            }
        });
    }


    private void showMainChatActivity() {
        Intent launchIntent = new Intent();
        launchIntent.setClass(getApplicationContext(), MainChatActivity.class);
        startActivity(launchIntent);

        finish();
    }

}

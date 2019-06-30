package com.mdlinkhealth;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.mdlinkhealth.api.APIService;
import com.mdlinkhealth.api.RestAPIClent;
import com.mdlinkhealth.chat.ChatClientManager;
import com.mdlinkhealth.helper.StringHelper;
import com.mdlinkhealth.preferences.SharedPreferenceManager;
import com.mdlinkhealth.util.Constants;
import com.twilio.chat.ErrorInfo;

public class App extends Application {

    private static App mAppInstance;
    private ChatClientManager basicClient;
    private static SharedPreferenceManager sharedPreferenceManager;
    public static APIService apiService =
            RestAPIClent.getClient().create(APIService.class);


    public static App getInstance() {
        return mAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
        sharedPreferenceManager = new SharedPreferenceManager(this);
        basicClient = new ChatClientManager(getApplicationContext());
    }

    public ChatClientManager getChatClientManager() {
        return this.basicClient;
    }

    public void showToast(final String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void showToast(final String text, final int duration) {
        Log.d("MDLink", text);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });
    }

    public String GetChannelName() {
        return sharedPreferenceManager.getStringData(Constants.APPOINTMENT_ID);
    }

    public String GetUserName() {
        return sharedPreferenceManager.getStringData(Constants.USER_NAME);
    }

    public String GetName() {
        return sharedPreferenceManager.getStringData(Constants.NAME);
    }

    public String GetFirstName() {
        String[] splitedName = sharedPreferenceManager.getStringData(Constants.NAME).split("\\s+");
        return splitedName[0];
    }

    public static boolean IsNotificationOn() {
        return sharedPreferenceManager.getBooleanData(Constants.PUSH_NOTIFICATION_STATE);
    }

    public void showError(final ErrorInfo error) {
        showError("Something went wrong", error);
    }

    public void showError(final String message, final ErrorInfo error) {
        showToast(formatted(message, error), Toast.LENGTH_LONG);
        logErrorInfo(message, error);
    }

    public void logErrorInfo(final String message, final ErrorInfo error) {
        Log.e("TwilioApplication", formatted(message, error));
    }

    private String formatted(String message, ErrorInfo error) {
        return String.format("%s. %s", message, error.toString());
    }
}
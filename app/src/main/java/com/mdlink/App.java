package com.mdlink;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.mdlink.api.APIService;
import com.mdlink.api.RestAPIClent;
import com.mdlink.chat.BasicChatClient;
import com.mdlink.chat.ChatClientManager;
import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;
import com.twilio.chat.ErrorInfo;

public class App extends Application {

    private static App mAppInstance;
    private ChatClientManager basicClient;
    private SharedPreferenceManager sharedPreferenceManager;
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

    public void showToast(final String text, final int duration)
    {
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

    public String GetChannelName(){
        return sharedPreferenceManager.getStringData(Constants.APPOINTMENT_ID);
    }

    public String GetUserName(){
        return sharedPreferenceManager.getStringData(Constants.USER_NAME);
    }

    public void showError(final ErrorInfo error)
    {
        showError("Something went wrong", error);
    }

    public void showError(final String message, final ErrorInfo error)
    {
        showToast(formatted(message, error), Toast.LENGTH_LONG);
        logErrorInfo(message, error);
    }

    public void logErrorInfo(final String message, final ErrorInfo error)
    {
        Log.e("TwilioApplication", formatted(message, error));
    }

    private String formatted(String message, ErrorInfo error)
    {
        return String.format("%s. %s", message, error.toString());
    }
}
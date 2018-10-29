package com.mdlink;

import android.app.Application;

import com.mdlink.api.APIService;
import com.mdlink.api.RestAPIClent;

public class App extends Application {

    private static App mAppInstance;

    public static APIService apiService =
            RestAPIClent.getClient().create(APIService.class);

    public static App getInstance() {
        return mAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
    }
}
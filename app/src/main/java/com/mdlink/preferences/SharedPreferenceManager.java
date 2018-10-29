package com.mdlink.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.mdlink.R;

public class SharedPreferenceManager {
    private SharedPreferences prefs;
    private Context context;
    public static String PREF_USER_DATA="PREF_USER_DATA";

    public SharedPreferenceManager(Context context) {
        this.context = context;
        this.prefs=context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE);
    }

    public void saveString(String key,String data){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(key,data);
        editor.commit();
    }

    public void saveBoolean(String key,boolean data){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putBoolean(key,data);
        editor.commit();
    }
    public String getStringData(String key){
        return prefs.getString(key,"");
    }

    public boolean getBooleanData(String key){
        return prefs.getBoolean(key,false);
    }

    public void saveIntegerData(String key,int val){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt(key,val);
        editor.commit();
    }

    public int getIntegerData(String key){
        int myIntValue = prefs.getInt(key, -1);
        return myIntValue;
    }

    public void ClearData(){
        SharedPreferences.Editor editor=prefs.edit();
        editor.clear();
        editor.commit();
    }
}

package com.mdlinkhealth.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mdlinkhealth.LoginActivity;
import com.mdlinkhealth.MakeServiceCall;
import com.mdlinkhealth.PatientPortalActivity;
import com.mdlinkhealth.preferences.SharedPreferenceManager;
import com.mdlinkhealth.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PatientRegisterAsyncTask extends AsyncTask<Void,Void,String> {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private HashMap<String, String> hashMap;
    private ProgressDialog pd;

    public PatientRegisterAsyncTask(Context context, HashMap<String,String> hashMap){
        this.context = context;
        this.hashMap = hashMap;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("Loading..Hold A Second..");
        pd.setCancelable(false);
        pd.show();
    }


    @Override
    protected String doInBackground(Void... voids) {
        return new MakeServiceCall().MakeServiceCall("http://api.themdlink.com/api/v1/patient", MakeServiceCall.POST, hashMap);
        //return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println(">>>>onPostExecute>>>>>>");
        if (pd.isShowing()) {
            pd.dismiss();
        }
        try {
            JSONObject json = new JSONObject(s);
            if (json.getString("status").equalsIgnoreCase("200")) {
                if(json.has("message")){
                    Toast.makeText(context,json.getString("message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            }else {
                if(json.has("message")) {
                    Toast.makeText(context, json.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

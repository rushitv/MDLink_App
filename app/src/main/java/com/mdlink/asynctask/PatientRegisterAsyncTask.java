package com.mdlink.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mdlink.Doctor_Portel_Data;
import com.mdlink.Login_Doctor;
import com.mdlink.MakeServiceCall;
import com.mdlink.Patient_Portal_Data;
import com.mdlink.Patient_portal_Activity;
import com.mdlink.preferences.SharedPreferenceManager;

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
        if (pd.isShowing()) {
            pd.dismiss();
        }
        try {
            JSONObject json = new JSONObject(s);
            System.out.println(">>>>>>>>>>"+json);
            if (json.getString("status").equalsIgnoreCase("200")) {
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
                JSONObject jsonArray = json.getJSONObject("result");
                if(!TextUtils.isEmpty(jsonArray.getString("user_id"))){
                    Log.i(TAG,"user_id>>"+jsonArray.getString("user_id"));
                    Log.i(TAG,"name>>"+jsonArray.getString("name"));
                    Log.i(TAG,"birthdate>>"+jsonArray.getString("birthdate"));

                    Intent intent = new Intent(context, Patient_Portal_Data.class);
                    intent.putExtra("UserName",hashMap.get("userID"));
                    intent.putExtra("Role","2");
                    intent.putExtra("UserId",jsonArray.getString("user_id"));
                    SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(context);
                    sharedPreferenceManager.saveString("UserName",hashMap.get("userID"));
                    sharedPreferenceManager.saveString("RoleId","2");
                    sharedPreferenceManager.saveString("UserId",jsonArray.getString("user_id"));
                    sharedPreferenceManager.saveString("Name",jsonArray.getString("name"));
                    sharedPreferenceManager.saveString("Birthdate",jsonArray.getString("birthdate"));
                    sharedPreferenceManager.saveString("Address",jsonArray.getString("address"));
                    sharedPreferenceManager.saveString("Age",jsonArray.getString("age"));
                    sharedPreferenceManager.saveString("Location",jsonArray.getString("location"));

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

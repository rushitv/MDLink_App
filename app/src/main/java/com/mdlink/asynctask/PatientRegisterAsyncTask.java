package com.mdlink.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mdlink.MakeServiceCall;
import com.mdlink.PatientPortalActivity;
import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.mdlink.util.Constants.USER_NAME;

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
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
                JSONObject jsonArray = json.getJSONObject("result");
                if(!TextUtils.isEmpty(jsonArray.getString("user_id"))){
                    Log.i(TAG,"user_id>>"+jsonArray.getString("user_id"));
                    Log.i(TAG,"name>>"+jsonArray.getString("name"));
                    Log.i(TAG,"birthdate>>"+jsonArray.getString("birthdate"));

                    Intent intent = new Intent(context, PatientPortalActivity.class);
                    intent.putExtra(Constants.USER_NAME,hashMap.get("userID"));
                    intent.putExtra(Constants.ROLE_ID,"2");
                    intent.putExtra(Constants.USER_ID,jsonArray.getString("user_id"));
                    SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(context);
                    sharedPreferenceManager.saveString(Constants.USER_NAME,hashMap.get("userID"));
                    sharedPreferenceManager.saveString(Constants.ROLE_ID,"2");
                    sharedPreferenceManager.saveString(Constants.USER_ID,jsonArray.getString("user_id"));
                    sharedPreferenceManager.saveString(Constants.NAME,jsonArray.getString("name"));
                    sharedPreferenceManager.saveString(Constants.BIRTH_DATE,jsonArray.getString("birthdate"));
                    sharedPreferenceManager.saveString(Constants.ADDRESS,jsonArray.getString("address"));
                    sharedPreferenceManager.saveString(Constants.AGE,jsonArray.getString("age"));
                    sharedPreferenceManager.saveString(Constants.LOCATION,jsonArray.getString("location"));

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

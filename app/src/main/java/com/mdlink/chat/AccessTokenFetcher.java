package com.mdlink.chat;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.JsonObject;
import com.mdlink.App;
import com.mdlink.chat.listeners.TaskCompletionListener;
import com.mdlink.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessTokenFetcher {
  private String TAG = getClass().getSimpleName();
  private String accessToken, identity;
  private Context context;

  public AccessTokenFetcher(Context context) {
    this.context = context;
  }

  public void fetch(final TaskCompletionListener<String, String> listener) {

    try {

      Call<JsonObject> getChatToken = App.apiService.getChatToken(Constants.TWILIO_USER,Constants.CHANNEL_UNIQUE_NAME);
      JsonObject jsonObject = getChatToken.execute().body();
      identity = jsonObject.get("identity").getAsString();
      accessToken = jsonObject.get("token").getAsString();

      listener.onSuccess(accessToken);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Map<String, String> getTokenRequestParams(Context context) {
    String androidId =
        Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    Map<String, String> params = new HashMap<>();
    params.put("deviceId", androidId);
    params.put("identity", SessionManager.getInstance().getUsername());
    return params;
  }

}

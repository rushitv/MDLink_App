package com.mdlink.chat;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mdlink.App;
import com.mdlink.R;
import com.mdlink.chat.listeners.TaskCompletionListener;
import com.mdlink.util.Constants;
import com.mdlink.video.VideoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

            String user = App.getInstance().GetUserName().substring(0, 4);

            Call<JsonObject> getChatToken = App.apiService.getChatToken(
                    user, Constants.CHANNEL_DEFAULT_NAME + App.getInstance().GetChannelName());

            getChatToken.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    identity = response.body().get("identity").getAsString();
                    accessToken = response.body().get("token").getAsString();
                    listener.onSuccess(accessToken);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.i(TAG,">>>>>>>>>>>>>>failure>>>>>>>>>>");
                }
            });

           /* JsonObject jsonObject = getChatToken.execute().body();
            identity = jsonObject.get("identity").getAsString();
            accessToken = jsonObject.get("token").getAsString();*/

            /*Ion.with(App.getInstance())
                    .load(String.format("%s?identity=RushitV-19000", ACCESS_TOKEN_SERVER,
                            UUID.randomUUID().toString()))
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String token) {
                            if (e == null) {

                                JsonParser jsonParser = new JsonParser();
                                JsonObject jo = (JsonObject)jsonParser.parse(token);

                                Log.i(TAG,">>>>>>>>>"+jo.get("token").getAsString());
                                token = jo.get("token").getAsString();
                                VideoActivity.this.accessToken = token;
                            } else {
                                Toast.makeText(VideoActivity.this,
                                        R.string.error_retrieving_access_token, Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });*/

            //listener.onSuccess(accessToken);

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

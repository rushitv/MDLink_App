package com.mdlink.chat;

import com.google.gson.JsonObject;
import com.mdlink.App;
import com.mdlink.chat.listeners.TaskCompletionListener;
import com.twilio.accessmanager.AccessManager;

import com.twilio.chat.BuildConfig;
import com.twilio.chat.StatusListener;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ErrorInfo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasicChatClient extends CallbackListener<ChatClient>
    implements AccessManager.Listener, AccessManager.TokenUpdateListener
{
    private String TAG = getClass().getSimpleName();
    private static final Logger logger = Logger.getLogger(BasicChatClient.class);

    private String accessToken;
    private String fcmToken;

    private ChatClient mChatClient;

    private Context       context;
    private AccessManager accessManager;

    private LoginListener loginListener;
    private Handler       loginListenerHandler;

    private String        urlString = "http://api.themdlink.com/api/v1/chat-token?identity=Pravin&room_name=appo_room_112";
    private String        username;

    public BasicChatClient(Context context)
    {
        super();
        this.context = context;


        try {
            Call<JsonObject> getChatToken = App.apiService.getChatToken("Pravin","appo_room_112");
            getChatToken.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                    Log.i(TAG, "1>>>>>>>>>>>>" + response.body());
                    if (response.code() == 200) {
                        String identity = response.body().get("identity").getAsString();
                        accessToken = response.body().get("token").getAsString();
                        Log.i(TAG, "accessToken>>>>>>>>>" + accessToken);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                    t.fillInStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                onSuccess(mChatClient);
                if (BuildConfig.DEBUG) {
                    logger.e("Enabling DEBUG logging");
                    ChatClient.setLogLevel(android.util.Log.DEBUG);
                }
            }
        }, 1000);


        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                createAccessManager();
                createClient(accessToken);
                accessManager.updateToken(accessToken);
            }
        }, 1000);


    }

    public interface LoginListener {
        public void onLoginStarted();

        public void onLoginFinished();

        public void onLoginError(String errorMessage);

        public void onLogoutFinished();
    }


    public ChatClient getmChatClient()
    {
        return mChatClient;
    }


    private void createAccessManager()
    {
        if (accessManager != null) return;

        accessManager = new AccessManager(accessToken, this);
        accessManager.addTokenUpdateListener(this);

    }

    private void createClient(String accessToken)
    {
        if (mChatClient != null) return;

        ChatClientBuilder chatClientBuilder = new ChatClientBuilder(context);
        chatClientBuilder.build(accessToken, new TaskCompletionListener<ChatClient, String>() {
            @Override
            public void onSuccess(ChatClient chatClient) {
                logger.d("Received completely initialized ChatClient");
                mChatClient = chatClient;
            }

            @Override
            public void onError(String s) {

            }
        });

    }

    public void shutdown()
    {
        mChatClient.shutdown();
        mChatClient = null; // Client no longer usable after shutdown()
    }

    // Client created, remember the reference and set up UI
    @Override
    public void onSuccess(ChatClient client)
    {

        mChatClient = client;
    }

    // Client not created, fail
    @Override
    public void onError(final ErrorInfo errorInfo)
    {
        App.getInstance().logErrorInfo("Login error", errorInfo);

        loginListenerHandler.post(new Runnable() {
            @Override
            public void run()
            {
                if (loginListener != null) {
                    loginListener.onLoginError(errorInfo.toString());
                }
            }
        });
    }

    // AccessManager.Listener

    @Override
    public void onTokenWillExpire(AccessManager accessManager)
    {
        //TwilioApplication.get().showToast("Token will expire in 3 minutes. Getting new token.");
        new GetAccessTokenAsyncTask().execute(username, urlString);
    }

    @Override
    public void onTokenExpired(AccessManager accessManager)
    {
        //TwilioApplication.get().showToast("Token expired. Getting new token.");
        new GetAccessTokenAsyncTask().execute(username, urlString);
    }

    @Override
    public void onError(AccessManager accessManager, String err)
    {
        App.getInstance().showToast("AccessManager error: " + err);
    }

    // AccessManager.TokenUpdateListener

    @Override
    public void onTokenUpdated(String token)
    {
        if (mChatClient == null) return;

        mChatClient.updateToken(token, new StatusListener(
              ) {
            @Override
            public void onSuccess() {

            }
        });
    }

    /**
     * Modify this method if you need to provide more information to your Access Token Service.
     */
    private class GetAccessTokenAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params)
        {

            return accessToken;
        }

        @Override
        protected void onPostExecute(final String result)
        {
            super.onPostExecute(result);


        }
    }
}
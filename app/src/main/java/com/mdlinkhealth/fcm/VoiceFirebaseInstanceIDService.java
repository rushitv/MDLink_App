package com.mdlinkhealth.fcm;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.mdlinkhealth.voice.VoiceActivity;

public class VoiceFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "VoiceFbIIDSvc";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]

    @Override
    public void onNewToken(String mToken) {
        super.onNewToken(mToken);
        Log.i("TOKEN>>>>>>>>>>>>: ",mToken);
        // Notify Activity of FCM token
        Intent intent = new Intent(VoiceActivity.ACTION_FCM_TOKEN);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    // [END refresh_token]

}

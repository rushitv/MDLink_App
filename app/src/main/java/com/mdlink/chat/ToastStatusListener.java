package com.mdlink.chat;

import com.mdlink.App;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Message;
import com.twilio.chat.StatusListener;

class ToastStatusListener extends CallbackListener<Message>
{
    private final String okText;
    private final String errorText;

    ToastStatusListener(String ok, String error) {
        okText = ok;
        errorText = error;
    }


    @Override
    public void onSuccess(Message message) {
        App.getInstance().showToast(okText);
    }

    @Override
    public void onError(ErrorInfo errorInfo)
    {
        App.getInstance().showError(errorText, errorInfo);
    }
}
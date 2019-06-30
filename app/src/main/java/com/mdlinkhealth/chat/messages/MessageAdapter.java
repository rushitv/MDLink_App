package com.mdlinkhealth.chat.messages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdlinkhealth.App;
import com.mdlinkhealth.R;
import com.mdlinkhealth.chat.ImagePreviewActivity;
import com.mdlinkhealth.helper.StringHelper;
import com.mdlinkhealth.helper.UiHelper;
import com.mdlinkhealth.util.Constants;
import com.mdlinkhealth.util.FileUtil;
import com.mdlinkhealth.util.ValidationsUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.twilio.chat.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MessageAdapter extends BaseAdapter {
    private String TAG = getClass().getSimpleName();
    private final int TYPE_MESSAGE = 0;
    private final int TYPE_STATUS = 1;

    private List<ChatMessage> messages;
    private LayoutInflater layoutInflater;
    private TreeSet<Integer> statusMessageSet = new TreeSet<>();
    private Activity activity;

    public MessageAdapter(Activity activity) {
        this.activity = activity;
        layoutInflater = activity.getLayoutInflater();
        messages = new ArrayList<>();
    }

    public void setMessages(List<Message> messages) {
        this.messages = convertTwilioMessages(messages);
        this.statusMessageSet.clear();
        notifyDataSetChanged();
    }

    public void addMessage(Message message) {
        messages.add(new UserMessage(message));
        notifyDataSetChanged();
    }

    public void addStatusMessage(StatusMessage message) {
        messages.add(message);
        statusMessageSet.add(messages.size() - 1);
        notifyDataSetChanged();
    }

    public void removeMessage(Message message) {
        messages.remove(messages.indexOf(message));
        notifyDataSetChanged();
    }

    private List<ChatMessage> convertTwilioMessages(List<Message> messages) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (Message message : messages) {
            chatMessages.add(new UserMessage(message));
        }
        return chatMessages;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return statusMessageSet.contains(position) ? TYPE_STATUS : TYPE_MESSAGE;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        int type = getItemViewType(position);
        int res;
        switch (type) {
            case TYPE_MESSAGE:
                String selfUser = messages.get(position).getAuthor();
                Log.d(TAG, "selfUser>>>>" + selfUser + ">>>>" + App.getInstance().GetFirstName());
                if (messages.get(position).getAuthor().contains("-")) {
                    int firstSpace = (selfUser.indexOf("-") >= 0) ? selfUser.indexOf("-") : selfUser.length() - 1;
                    selfUser = selfUser.substring(0, firstSpace);
                }

                if (StringHelper.equals(selfUser, App.getInstance().GetFirstName()) || StringHelper.equals(selfUser, App.getInstance().GetUserName())) {
                    res = R.layout.message_self;
                } else {
                    res = R.layout.message_opponent;
                }
                convertView = layoutInflater.inflate(res, viewGroup, false);
                final ChatMessage message = messages.get(position);
                TextView textViewMessage = convertView.findViewById(R.id.textViewMessage);
                TextView textViewAuthor = convertView.findViewById(R.id.textViewAuthor);
                TextView textViewDate = convertView.findViewById(R.id.textViewDate);
                ImageView imageViewMessage = convertView.findViewById(R.id.imageViewMessage);

                final ProgressBar progressImageLoading = convertView.findViewById(R.id.progress_image_loading);
                if (FileUtil.isURL(message.getMessageBody())) {
                    progressImageLoading.setVisibility(View.VISIBLE);
                    Log.i(TAG, ">>>>>>>isURL>>>>>" + message.getMessageBody());
                    Picasso.get().load(message.getMessageBody())
                            .resize(UiHelper.widthInPixels(50), 0)
                            .into(imageViewMessage, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            progressImageLoading.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            progressImageLoading.setVisibility(View.GONE);
                                        }
                                    }
                            );
                    imageViewMessage.setVisibility(View.VISIBLE);
                    textViewMessage.setVisibility(View.GONE);

                    imageViewMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPhoto(message.getMessageBody());
                        }
                    });
                } else {
                    imageViewMessage.setVisibility(View.GONE);
                    textViewMessage.setVisibility(View.VISIBLE);

                    textViewMessage.setText(message.getMessageBody());
                    textViewAuthor.setText(message.getAuthor());
                    textViewAuthor.setVisibility(View.GONE);
                }
                textViewDate.setText(DateFormatter.getFormattedDateFromISOString(message.getDateCreated()));
                break;
           /* case TYPE_STATUS:
                res = R.layout.status_message;
                convertView = layoutInflater.inflate(res, viewGroup, false);
                ChatMessage status = messages.get(position);
                TextView textViewStatus = (TextView) convertView.findViewById(R.id.textViewStatusMessage);
                String statusMessage = status.getMessageBody();
                textViewStatus.setText(statusMessage);
                break;*/
        }
        return convertView;
    }

    private void showPhoto(String url) {
        Intent intent = new Intent(activity, ImagePreviewActivity.class);
        intent.putExtra(Constants.IMAGE_URL, url);
        activity.startActivity(intent);
    }
}

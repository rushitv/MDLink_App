package com.mdlinkhealth.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mdlinkhealth.App;
import com.mdlinkhealth.R;
import com.mdlinkhealth.api.APIService;
import com.mdlinkhealth.api.RestAPIClent;
import com.mdlinkhealth.chat.messages.MessageAdapter;
import com.mdlinkhealth.util.Constants;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChannelListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Member;
import com.twilio.chat.Message;
import com.twilio.chat.Messages;
import com.twilio.chat.StatusListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class MainChatFragment extends Fragment implements ChannelListener {
    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 1;
    private String TAG = getClass().getSimpleName();
    Context context;
    Activity mainActivity;
    ImageView sendButton, imgGallery;
    ListView messagesListView;
    EditText messageTextEdit;
    MessageAdapter messageAdapter;
    Channel currentChannel;
    Messages messagesObject;
    private int REQUEST_GET_SINGLE_FILE = 101;
    APIService apiService =
            RestAPIClent.getClient().create(APIService.class);
    private String AppointmentId;

    public MainChatFragment() {
    }

    public static MainChatFragment newInstance(String RoleId, String AppointmentId, String Name, String DoctorName) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ROLE_ID, RoleId);
        bundle.putString(Constants.APPOINTMENT_ID, AppointmentId);
        bundle.putString(Constants.NAME, Name);
        bundle.putString(Constants.DOCTOR_NAME, DoctorName);
        MainChatFragment fragment = new MainChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getActivity();
        mainActivity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_chat, container, false);
        imgGallery = view.findViewById(R.id.imgGallery);
        sendButton = view.findViewById(R.id.buttonSend);
        messagesListView = view.findViewById(R.id.listViewMessages);
        messageTextEdit = view.findViewById(R.id.editTextMessage);

        messageAdapter = new MessageAdapter(mainActivity);
        messagesListView.setAdapter(messageAdapter);
        setUpListeners();
        setMessageInputEnabled(false);

        AppointmentId = getArguments().getString(Constants.APPOINTMENT_ID);
        Log.i(TAG, "AppointmentId>>>>>>>>>>>>" + AppointmentId);

        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_STORAGE);
                } else {
                    launchGalleryOpener();
                }

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel, final StatusListener handler) {
        if (currentChannel == null) {
            this.currentChannel = null;
            return;
        }
        if (!currentChannel.equals(this.currentChannel)) {
            setMessageInputEnabled(false);
            this.currentChannel = currentChannel;
            this.currentChannel.addListener(this);
            if (this.currentChannel.getStatus() == Channel.ChannelStatus.JOINED) {
                loadMessages(handler);
            } else {
                this.currentChannel.join(new StatusListener() {
                    @Override
                    public void onSuccess() {
                        loadMessages(handler);
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                    }
                });
            }
        }
    }

    private void loadMessages(final StatusListener handler) {
        this.messagesObject = this.currentChannel.getMessages();

        if (messagesObject != null) {
            messagesObject.getLastMessages(100, new CallbackListener<List<Message>>() {
                @Override
                public void onSuccess(List<Message> messageList) {
                    messageAdapter.setMessages(messageList);
                    setMessageInputEnabled(true);
                    messageTextEdit.requestFocus();
                    handler.onSuccess();
                }
            });
        }
    }

    private void setUpListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        messageTextEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
    }

    private void sendMessage() {
        String messageText = getTextInput();
        if (messageText.length() == 0) {
            return;
        }
        Message.Options messageOptions = Message.options().withBody(messageText);
        this.messagesObject.sendMessage(messageOptions, null);
        clearTextInput();
    }

    private void sendMessage(String url) {
        Message.Options messageOptions = Message.options().withBody(url);
        this.messagesObject.sendMessage(messageOptions, null);
        clearTextInput();
    }

    private void setMessageInputEnabled(final boolean enabled) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainChatFragment.this.sendButton.setEnabled(enabled);
                MainChatFragment.this.messageTextEdit.setEnabled(enabled);
            }
        });
    }

    private String getTextInput() {
        return messageTextEdit.getText().toString();
    }

    private void clearTextInput() {
        messageTextEdit.setText("");
    }

    @Override
    public void onMessageAdded(Message message) {
        messageAdapter.addMessage(message);
    }

    @Override
    public void onMemberAdded(Member member) {
        //com.twilio.twiliochat.chat.messages.StatusMessage statusMessage = new com.twilio.twiliochat.chat.messages.JoinedStatusMessage(member.getIdentity());
        //this.messageAdapter.addStatusMessage(statusMessage);
    }

    @Override
    public void onMemberDeleted(Member member) {
        //com.twilio.twiliochat.chat.messages.StatusMessage statusMessage = new com.twilio.twiliochat.chat.messages.LeftStatusMessage(member.getIdentity());
        //this.messageAdapter.addStatusMessage(statusMessage);
    }

    @Override
    public void onMessageUpdated(Message message, Message.UpdateReason updateReason) {
    }

    @Override
    public void onMessageDeleted(Message message) {
    }

    @Override
    public void onMemberUpdated(Member member, Member.UpdateReason updateReason) {
    }

    @Override
    public void onTypingStarted(Channel channel, Member member) {
    }

    @Override
    public void onTypingEnded(Channel channel, Member member) {
    }

    @Override
    public void onSynchronizationChanged(Channel channel) {
    }

    private void launchGalleryOpener() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                        Uri imageUri = writeInputSteamToCache(inputStream);
                        if (null != imageUri) {
                            Log.d(TAG, " onActivityResult :: " + "imageUri : " + imageUri);
                            //uploadImageToServer(imageUri.getPath());
                            Intent i = new Intent(getContext(), ImagePreviewActivity.class);
                            i.putExtra(Constants.IMAGE_URI, imageUri.getPath());
                            startActivityForResult(i, 11);
                            return;
                        }
                    } catch (Exception e) {
                        Log.d(TAG, " onActivityResult :: " + e.getMessage());
                    }
                }

                if (requestCode == 11) {
                    String resultImageUri = data.getStringExtra(Constants.IMAGE_URI);
                    Log.d(TAG, " onActivityResult ::resultImageUri:: " + resultImageUri);
                    uploadImageToServer(Uri.parse(resultImageUri).getPath());
                    return;
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    private Uri writeInputSteamToCache(InputStream inputStream) throws Exception {
        if (null == inputStream) {
            return null;
        }

        File file = getTemporaryFile();

        OutputStream output = new FileOutputStream(file);
        byte[] buffer = new byte[4 * 1024]; // or other buffer size
        int read;

        while ((read = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, read);
        }

        output.flush();
        output.close();
        inputStream.close();

        return Uri.fromFile(file);
    }

    private File getTemporaryFile() {
        return new File(App.getInstance().getCacheDir(), System.currentTimeMillis() + ".jpg");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! do the
                    launchGalleryOpener();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void uploadImageToServer(String path) {
        //pass it like this
        File fileImage = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part mpFile =
                MultipartBody.Part.createFormData("file", fileImage.getName(), requestFile);
        Call<JsonObject> callToUploadImage = apiService.uploadFiles(AppointmentId, mpFile);
        callToUploadImage.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.code() == 200) {
                    if (response.body().get("status").getAsInt() == 200) {
                        String url = response.body().get("url").getAsString();
                        Log.i(TAG, ">>>>>>>imageurl>>>>>>>>>>>>>>" + url);
                        sendMessage(url);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.fillInStackTrace();
            }
        });

    }
}

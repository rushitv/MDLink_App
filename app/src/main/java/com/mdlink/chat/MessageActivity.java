package com.mdlink.chat;
import java.util.ArrayList;
import java.util.List;

import com.mdlink.App;
import com.mdlink.R;
import com.mdlink.chat.channels.ChannelManager;
import com.mdlink.chat.listeners.TaskCompletionListener;
import com.mdlink.util.Constants;
import com.twilio.chat.BuildConfig;
import com.twilio.chat.Channel;
import com.twilio.chat.Channel.ChannelType;
import com.twilio.chat.ChannelListener;
import com.twilio.chat.Channels;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Member;
import com.twilio.chat.Members;
import com.twilio.chat.Message;
import com.twilio.chat.Messages;
import com.twilio.chat.Paginator;
import com.twilio.chat.StatusListener;
import com.twilio.chat.User;
import com.twilio.chat.UserDescriptor;
import com.twilio.chat.internal.Logger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import uk.co.ribot.easyadapter.EasyAdapter;


public class MessageActivity extends Activity implements ChannelListener
{
    private static final Logger logger = Logger.getLogger(MessageActivity.class);
    private static final        String[] MESSAGE_OPTIONS = {
        "Remove", "Edit", "Get Attributes", "Edit Attributes"
    };
    private ListView                 messageListView;
    private EditText                 inputText;
    private EasyAdapter<MessageItem> adapter;
    private Channel                  channel;


    private static final int REMOVE = 0;
    private static final int EDIT = 1;
    private static final int GET_ATTRIBUTES = 2;
    private static final int SET_ATTRIBUTES = 3;

    private ArrayList<MessageItem> messageItemList = new ArrayList<>();
    private String                 identity;

    private String TAG = getClass().getSimpleName();

    TaskCompletionListener<ChatClient, String> buildListener;
    final String channelSid = "appo_room_112";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        createUI();
    }

    @Override
    protected void onDestroy()
    {
        channel.removeListener(MessageActivity.this);
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            channel = intent.getParcelableExtra(Constants.EXTRA_CHANNEL);
            if (channel != null) {
                setupListView(channel);
            }
        }
    }

    private void createUI()
    {
        setContentView(R.layout.activity_message);
        if (getIntent() != null) {

            final ChatClientManager chatClientManager = App.getInstance().getChatClientManager();
            identity = "Pravin-1541437652";
            //String   channelSid = getIntent().getStringExtra(Constants.EXTRA_CHANNEL_SID);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    Channels channelsObject = chatClientManager.getChatClient().getChannels();
                    channelsObject.getChannel(channelSid, new CallbackListener<Channel>() {
                        @Override
                        public void onSuccess(final Channel foundChannel)
                        {
                            channel = foundChannel;
                            channel.addListener(MessageActivity.this);
                            MessageActivity.this.setTitle(
                                    ((channel.getType() == ChannelType.PUBLIC) ? "PUB " : "PRIV ")
                                            + channel.getFriendlyName());

                            setupListView(channel);

                            messageListView = findViewById(R.id.message_list_view);
                            if (messageListView != null) {
                                messageListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                                messageListView.setStackFromBottom(true);
                                adapter.registerDataSetObserver(new DataSetObserver() {
                                    @Override
                                    public void onChanged()
                                    {
                                        super.onChanged();
                                        messageListView.setSelection(adapter.getCount() - 1);
                                    }
                                });
                            }
                            setupInput();
                        }

                        @Override
                        public void onError(ErrorInfo errorInfo) {
                            Log.i(TAG, ">>>>>>> no channel found>>>>>>>>>>>>>>" );
                        }
                    });
                }
            }, 5000);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //getMenuInflater().inflate(R.menu.message, menu);
        return true;
    }

    private void getUsersPage(Paginator<UserDescriptor> userDescriptorPaginator) {
        logger.e(userDescriptorPaginator.getItems().toString());
        for (UserDescriptor u : userDescriptorPaginator.getItems()) {
            u.subscribe(new CallbackListener<User>() {
                @Override
                public void onSuccess(User user) {
                    logger.d("Hi I am subscribed user now "+user.getIdentity());
                }
            });
        }
        if (userDescriptorPaginator.hasNextPage()) {
            userDescriptorPaginator.requestNextPage(new CallbackListener<Paginator<UserDescriptor>>() {
                @Override
                public void onSuccess(Paginator<UserDescriptor> userDescriptorPaginator) {
                    getUsersPage(userDescriptorPaginator);
                }
            });
        }
    }

    private void loadAndShowMessages()
    {
        final Messages messagesObject = channel.getMessages();
        if (messagesObject != null) {
            messagesObject.getLastMessages(50, new CallbackListener<List<Message>>() {
                @Override
                public void onSuccess(List<Message> messagesArray) {
                    messageItemList.clear();
                    Members  members = channel.getMembers();
                    if (messagesArray.size() > 0) {
                        for (int i = 0; i < messagesArray.size(); i++) {
                            messageItemList.add(new MessageItem(messagesArray.get(i), members, identity));
                        }
                    }
                    adapter.getItems().clear();
                    adapter.getItems().addAll(messageItemList);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }



    private void setupInput()
    {
        // Setup our input methods. Enter key on the keyboard or pushing the send button
        EditText inputText = (EditText)findViewById(R.id.messageInput);
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }
            @Override
            public void afterTextChanged(Editable s)
            {
                if (channel != null) {
                    channel.typing();
                }
            }
        });

        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent)
            {
                if (actionId == EditorInfo.IME_NULL
                    && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sendMessage();
            }
        });
    }


    private void setupListView(Channel channel)
    {
        messageListView = (ListView)findViewById(R.id.message_list_view);
        final Messages messagesObject = channel.getMessages();

        messageListView.getViewTreeObserver().addOnScrollChangedListener(
            new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged()
                {
                    if ((messageListView.getLastVisiblePosition() >= 0)
                        && (messageListView.getLastVisiblePosition() < adapter.getCount())) {
                        MessageItem item =
                            adapter.getItem(messageListView.getLastVisiblePosition());
                        if (item != null && messagesObject != null)
                            messagesObject.advanceLastConsumedMessageIndexWithResult(
                                    item.getMessage().getMessageIndex(), new CallbackListener<Long>() {
                                        @Override
                                        public void onSuccess(Long aLong) {

                                        }
                                    });
                    }
                }
            });

        adapter = new EasyAdapter<MessageItem>(
                MessageActivity.this,
                MessageViewHolder.class,
                new MessageViewHolder.OnMessageClickListener() {
                    @Override
                    public void onMessageClicked(final MessageItem message)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
                        builder.setTitle("Select an option")
                                .setItems(MESSAGE_OPTIONS, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        if (which == REMOVE) {
                                            dialog.cancel();

                                            messagesObject.removeMessage(message.message, new StatusListener() {
                                                @Override
                                                public void onSuccess() {
                                                    messageItemList.remove(message);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            });
                                        } else if (which == EDIT) {
                                            //showUpdateMessageDialog(message.getMessage());
                                        } else if (which == GET_ATTRIBUTES) {
                                            String attr = "";
                                            try {
                                                attr = message.getMessage().getAttributes().toString();
                                            } catch (JSONException e) {
                                            }
                                            App.getInstance().showToast(attr);
                                        } else if (which == SET_ATTRIBUTES) {
                                            //showUpdateMessageAttributesDialog(message.getMessage());
                                        }
                                    }
                                });
                        builder.show();
                    }
                });
        messageListView.setAdapter(adapter);

        loadAndShowMessages();
    }

    private void sendMessage(String text)
    {
        Message.Options messageOptions = Message.options().withBody(text);

        final Messages messagesObject = this.channel.getMessages();
        messagesObject.sendMessage(messageOptions, new ToastStatusListener(
                "Successfully sent message",
                "Error sending message") {
            public void onSuccess()
            {
                adapter.notifyDataSetChanged();
                inputText.setText("");
            }
        });
    }

    private void sendMessage()
    {
        inputText = (EditText)findViewById(R.id.messageInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            sendMessage(input);
        }

        inputText.requestFocus();
    }

    @Override
    public void onMessageAdded(Message message)
    {
        setupListView(this.channel);
    }

    @Override
    public void onMessageUpdated(Message message, Message.UpdateReason updateReason) {

    }

    @Override
    public void onMessageDeleted(Message message)
    {
        if (message != null) {
            App.getInstance().showToast(message.getSid() + " deleted");
            logger.d("Received onMessageDelete for message sid|" + message.getSid() + "|");
        } else {
            logger.d("Received onMessageDelete.");
        }
    }

    @Override
    public void onMemberAdded(Member member)
    {
        if (member != null) {
            App.getInstance().showToast(member.getIdentity() + " joined");
        }
    }

    @Override
    public void onMemberUpdated(Member member, Member.UpdateReason updateReason) {

    }

    public void onMemberUpdated(Member member)
    {
        if (member != null) {
            App.getInstance().showToast(member.getIdentity() + " changed");
        }
    }

    @Override
    public void onMemberDeleted(Member member)
    {
        if (member != null) {
            App.getInstance().showToast(member.getIdentity() + " deleted");
        }
    }

    @Override
    public void onTypingStarted(Channel channel, Member member) {

    }

    @Override
    public void onTypingEnded(Channel channel, Member member) {

    }

    public void onTypingStarted(Member member)
    {
        if (member != null) {
            TextView typingIndc = (TextView)findViewById(R.id.typingIndicator);
            String   text = member.getIdentity() + " is typing .....";
            typingIndc.setText(text);
            typingIndc.setTextColor(Color.RED);
            logger.d(text);
        }
    }

    public void onTypingEnded(Member member)
    {
        if (member != null) {
            TextView typingIndc = (TextView)findViewById(R.id.typingIndicator);
            typingIndc.setText(null);
            logger.d(member.getIdentity() + " ended typing");
        }
    }

    @Override
    public void onSynchronizationChanged(Channel channel)
    {
        logger.d("Received onSynchronizationChanged callback " + channel.getFriendlyName());
    }

    public static class MessageItem
    {
        Message message;
        Members members;
        String  currentUser;

        public MessageItem(Message message, Members members, String currentUser)
        {
            this.message = message;
            this.members = members;
            this.currentUser = currentUser;
        }

        public Message getMessage()
        {
            return message;
        }

        public Members getMembers()
        {
            return members;
        }
    }
}

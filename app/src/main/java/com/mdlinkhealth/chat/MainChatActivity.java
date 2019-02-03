package com.mdlinkhealth.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mdlinkhealth.App;
import com.mdlinkhealth.BaseActivity;
import com.mdlinkhealth.Medical_CheckOut_Doctor;
import com.mdlinkhealth.R;
import com.mdlinkhealth.chat.channels.ChannelAdapter;
import com.mdlinkhealth.chat.channels.ChannelManager;
import com.mdlinkhealth.chat.channels.LoadChannelListener;
import com.mdlinkhealth.chat.listeners.InputOnClickListener;
import com.mdlinkhealth.chat.listeners.TaskCompletionListener;
import com.mdlinkhealth.splash.SplashActivity;
import com.mdlinkhealth.util.Constants;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.Channels;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ChatClientListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.StatusListener;
import com.twilio.chat.User;

import java.util.List;

public class MainChatActivity extends BaseActivity implements ChatClientListener {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private Activity mainActivity;
    private Button logoutButton;
    private Button addChannelButton;
    private TextView usernameTextView;
    private ChatClientManager chatClientManager;
    private ListView channelsListView;
    private ChannelAdapter channelAdapter;
    private ChannelManager channelManager;
    private MainChatFragment chatFragment;
    private DrawerLayout drawer;
    private ProgressDialog progressDialog;
    private MenuItem leaveChannelMenuItem;
    private MenuItem deleteChannelMenuItem;
    private SwipeRefreshLayout refreshLayout;
    private Toolbar toolbar;
    private String AppointmentId, RoleId, Name, DoctorName, PatientId;

    @Override
    public void onBackPressed() {
        Log.i(TAG, "----------onBackPressed---------");
        super.onBackPressed();
        try {
            //leaveCurrentChannel();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    /*if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (null != chatClientManager) {
                    chatClientManager.shutdown();
                }
                //App.getInstance().getChatClientManager().setChatClient(null);
                getChatClientManager().setChatClient(null);
            }
        });
    }

    public ChatClientManager getChatClientManager() {
        return this.chatClientManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        if (getIntent() != null) {
            PatientId = getIntent().getStringExtra(Constants.PATIENT_ID);
            AppointmentId = getIntent().getStringExtra(Constants.APPOINTMENT_ID);
            Log.d(TAG, ">>>AppointmentId>>" + AppointmentId);
            RoleId = getIntent().getStringExtra(Constants.ROLE_ID);
            Name = getIntent().getStringExtra(Constants.NAME);
            DoctorName = getIntent().getStringExtra(Constants.DOCTOR_NAME);
        }

        chatClientManager = new ChatClientManager(getApplicationContext());
        initToolbar();

    /*drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();*/

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        chatFragment = MainChatFragment.newInstance(RoleId, AppointmentId, Name, DoctorName);
        fragmentTransaction.add(R.id.fragment_container, chatFragment);
        fragmentTransaction.commit();

        context = this;
        mainActivity = this;
        logoutButton = (Button) findViewById(R.id.buttonLogout);
        addChannelButton = (Button) findViewById(R.id.buttonAddChannel);
        usernameTextView = (TextView) findViewById(R.id.textViewUsername);
        channelsListView = (ListView) findViewById(R.id.listViewChannels);

        channelManager = new ChannelManager(Constants.CHANNEL_DEFAULT_NAME + AppointmentId);
        channelManager.setDefaultChannelUniqueName(Constants.CHANNEL_DEFAULT_NAME + AppointmentId);
        Log.d(TAG, "getDefaultChannelName>>>>>>>>>" + channelManager.getDefaultChannelName());

        setUsernameTextView();

        setUpListeners();
        checkTwilioClient();

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
        setUpToolbar(toolbar, R.color.colorAccent);
        if (RoleId.equalsIgnoreCase("1")) {
            setToolbarTitle(DoctorName, R.color.colorAccent);
        } else {
            setToolbarTitle(Name, R.color.colorAccent);
        }
    }

    private void setUpListeners() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptLogout();
            }
        });
        addChannelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddChannelDialog();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                refreshChannels();
            }
        });
        channelsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setChannel(position);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_chat, menu);
        //this.leaveChannelMenuItem = menu.findItem(R.id.action_leave_channel);
        //this.leaveChannelMenuItem.setVisible(false);
        //this.deleteChannelMenuItem = menu.findItem(R.id.action_delete_channel);
        //this.deleteChannelMenuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_close) {
            leaveCurrentChannel();
            Log.i(TAG, "go to medical checkout screen");
            if (RoleId.equalsIgnoreCase("1")) {
                Intent iMedicalCheckout = new Intent(MainChatActivity.this, Medical_CheckOut_Doctor.class);
                iMedicalCheckout.putExtra(Constants.PATIENT_ID, PatientId);
                iMedicalCheckout.putExtra(Constants.APPOINTMENT_ID, AppointmentId);
                startActivity(iMedicalCheckout);
            } else {
                finish();
            }
            return true;
        }

    /*if (id == R.id.action_leave_channel) {
      leaveCurrentChannel();
      return true;
    }
    if (id == R.id.action_delete_channel) {
      promptChannelDeletion();
    }*/

        return super.onOptionsItemSelected(item);
    }

    private String getStringResource(int id) {
        Resources resources = getResources();
        return resources.getString(id);
    }

    private void refreshChannels() {
        channelManager.populateChannels(new LoadChannelListener() {
            @Override
            public void onChannelsFinishedLoading(final List<Channel> channels) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //channelAdapter.setChannels(channels);
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void populateChannels() {

        channelManager.setChannelListener(this);
        channelManager.populateChannels(new LoadChannelListener() {
            @Override
            public void onChannelsFinishedLoading(final List<Channel> channels) {
                channelAdapter = new ChannelAdapter(mainActivity, channels);
                channelsListView.setAdapter(channelAdapter);

                channelManager
                        .joinOrCreateGeneralChannelWithCompletion(new StatusListener() {
                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //channelAdapter.notifyDataSetChanged();
                                        chatClientManager.getChatClient().getChannels().getChannel(Constants.APPO_ROOM + AppointmentId, new CallbackListener<Channel>() {
                                            @Override
                                            public void onSuccess(final Channel channel) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        chatFragment.setCurrentChannel(channel, new StatusListener() {
                                                            @Override
                                                            public void onSuccess() {
                                                                MainChatActivity.this.stopActivityIndicator();
                                                            }

                                                            @Override
                                                            public void onError(ErrorInfo errorInfo) {
                                                                stopActivityIndicator();
                                                            }
                                                        });
                                                        setTitle(channel.getFriendlyName());
                                                        //drawer.closeDrawer(GravityCompat.START);
                                                    }
                                                });
                                            }
                                        });
                                        //setChannel(Constants.CHANNEL_DEFAULT_NAME + AppointmentId);
                                    }
                                });
                            }

                            @Override
                            public void onError(ErrorInfo errorInfo) {
                                stopActivityIndicator();
                                showAlertWithMessage(getStringResource(R.string.generic_error));
                            }
                        });
            }
        });


    }

    private void setChannel(String byName) {

        List<Channel> channels = channelManager.getChannels();
        Log.i(TAG, ">>>before>>>>>" + channels.size());
        Log.i(TAG, ">>>>desired>>channel>>>>>>>>>>>>" + Constants.APPO_ROOM + AppointmentId);
        Log.i(TAG, ">>>after>>>" + channels.size());

        if (channels == null) {
            return;
        }

        int pos = -1;
        for (Channel channel : channels) {
            pos++;
            System.out.println(">>>>" + channel.getUniqueName() + ">>>>" + channel.getFriendlyName());
            System.out.println("pos>>>>>>>" + pos + ">>>>>>>>>>>>>>" + channel.getUniqueName());
            if (channel.getUniqueName().equalsIgnoreCase(channelManager.getDefaultChannelUniqueName())) {
                break;
            }
        }

        final Channel currentChannel = chatFragment.getCurrentChannel();
        final Channel selectedChannel = channels.get(pos);
        //final Channel selectedChannel = getChannelFromList(channels);
        System.out.println("selectedChannel>>>>>>" + selectedChannel.getUniqueName());

        System.out.println("selectedChannel>>>>>>>>>>>>>>>" + pos + ">>>>>>>" + channels.get(pos).getUniqueName());

       /* if (currentChannel != null && currentChannel.getSid().contentEquals(selectedChannel.getSid())) {
            //drawer.closeDrawer(GravityCompat.START);
            return;
        }*/
        //hideMenuItems(pos);

        if (selectedChannel != null) {
            showActivityIndicator("Joining " + selectedChannel.getFriendlyName() + " channel");
            if (currentChannel != null && currentChannel.getStatus() == Channel.ChannelStatus.JOINED) {
                channelManager.leaveChannelWithHandler(currentChannel, new StatusListener() {
                    @Override
                    public void onSuccess() {
                        joinChannel(selectedChannel);
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        stopActivityIndicator();
                    }
                });
                return;
            }
            joinChannel(selectedChannel);
            stopActivityIndicator();
        } else {
            stopActivityIndicator();
            showAlertWithMessage(getStringResource(R.string.generic_error));
            System.out.println("Selected channel out of range");
        }

    }

    private Channel getChannelFromList(List<Channel> channels) {
        for (Channel channel : channels) {
            if (channel.getUniqueName().trim().equalsIgnoreCase(Constants.APPO_ROOM + AppointmentId)) {
                Log.i(TAG, ">if>>>" + channel.getUniqueName().trim());
                return channel;
            } else {
                Log.i(TAG, ">ielse>>>" + channel.getUniqueName().trim());
            }
        }
        return null;
    }

    private void setChannel(final int position) {
        List<Channel> channels = channelManager.getChannels();
        int pos = -1;
        for (Channel channel : channels) {
            pos++;
            System.out.println(">>>>" + channel.getUniqueName() + ">>>>" + channel.getFriendlyName());
            System.out.println("pos>>>>>>>" + pos + ">>>>>>>>>>>>>>" + channel.getUniqueName());
            if (channel.getUniqueName().equalsIgnoreCase(Constants.APPO_ROOM + AppointmentId)) {
                break;
            }
            /*if (channel.getUniqueName().equalsIgnoreCase(channelManager.getDefaultChannelUniqueName())) {
                break;
            }*/
        }

        if (channels == null) {
            return;
        }

        final Channel currentChannel = chatFragment.getCurrentChannel();
        final Channel selectedChannel = channels.get(pos);

        if (null == getChannelFromList(channels)) {
            Log.i(TAG, "nul>>");
        }
        //final Channel selectedChannel = getChannelFromList(channels);

        Log.i("selected>>", pos + ">>>>>>>" + channels.get(pos).getUniqueName());

        if (currentChannel != null && currentChannel.getSid().contentEquals(selectedChannel.getSid())) {
            //drawer.closeDrawer(GravityCompat.START);
            return;
        }
        //hideMenuItems(position);
        if (selectedChannel != null) {
            showActivityIndicator("Joining " + selectedChannel.getFriendlyName() + " channel");
            if (currentChannel != null && currentChannel.getStatus() == Channel.ChannelStatus.JOINED) {
                this.channelManager.leaveChannelWithHandler(currentChannel, new StatusListener() {
                    @Override
                    public void onSuccess() {
                        joinChannel(selectedChannel);
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        stopActivityIndicator();
                    }
                });
                return;
            }


            joinChannel(selectedChannel);
            stopActivityIndicator();
        } else {
            stopActivityIndicator();
            showAlertWithMessage(getStringResource(R.string.generic_error));
            System.out.println("Selected channel out of range");
        }
    }

    private void joinChannel(final Channel selectedChannel) {
        System.out.println("joining>>>>>channel>>" + selectedChannel.getUniqueName() + "-------" + selectedChannel.getFriendlyName());

        chatClientManager.getChatClient().getChannels().getChannel(Constants.APPO_ROOM + AppointmentId, new CallbackListener<Channel>() {
            @Override
            public void onSuccess(final Channel channel) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatFragment.setCurrentChannel(channel, new StatusListener() {
                            @Override
                            public void onSuccess() {
                                MainChatActivity.this.stopActivityIndicator();
                            }

                            @Override
                            public void onError(ErrorInfo errorInfo) {
                            }
                        });
                        setTitle(selectedChannel.getFriendlyName());
                        //drawer.closeDrawer(GravityCompat.START);
                    }
                });
            }
        });
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatFragment.setCurrentChannel(selectedChannel, new StatusListener() {
                    @Override
                    public void onSuccess() {
                        MainChatActivity.this.stopActivityIndicator();
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                    }
                });
                setTitle(selectedChannel.getFriendlyName());
                //drawer.closeDrawer(GravityCompat.START);
            }
        });*/
    }

    private void hideMenuItems(final int position) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainChatActivity.this.leaveChannelMenuItem.setVisible(position != 0);
                MainChatActivity.this.deleteChannelMenuItem.setVisible(position != 0);
            }
        });
    }

    private void showAddChannelDialog() {
        String message = getStringResource(R.string.new_channel_prompt);
        AlertDialogHandler.displayInputDialog(message, context, new InputOnClickListener() {
            @Override
            public void onClick(String input) {
                if (input.length() == 0) {
                    showAlertWithMessage(getStringResource(R.string.channel_name_required_message));
                    return;
                }
                createChannelWithName(input);
            }
        });
    }

    private void createChannelWithName(String name) {
        name = name.trim();
        if (name.toLowerCase()
                .contentEquals(this.channelManager.getDefaultChannelName().toLowerCase())) {
            showAlertWithMessage(getStringResource(R.string.channel_name_equals_default_name));
            return;
        }
        this.channelManager.createChannelWithName(name, new StatusListener() {
            @Override
            public void onSuccess() {
                refreshChannels();
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                showAlertWithMessage(getStringResource(R.string.generic_error));
            }
        });
    }

    private void promptChannelDeletion() {
        String message = getStringResource(R.string.channel_delete_prompt_message);
        AlertDialogHandler.displayCancellableAlertWithHandler(message, context,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCurrentChannel();
                    }
                });
    }

    private void deleteCurrentChannel() {
        Channel currentChannel = chatFragment.getCurrentChannel();
        channelManager.deleteChannelWithHandler(currentChannel, new StatusListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                showAlertWithMessage(getStringResource(R.string.message_deletion_forbidden));
            }
        });
    }

    private void leaveCurrentChannel() {
        final Channel currentChannel = chatFragment.getCurrentChannel();
        if (currentChannel.getStatus() == Channel.ChannelStatus.NOT_PARTICIPATING) {
            setChannel(0);
            return;
        }
        channelManager.leaveChannelWithHandler(currentChannel, new StatusListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                stopActivityIndicator();
            }
        });
    }

    private void checkTwilioClient() {
        showActivityIndicator(getStringResource(R.string.loading_channels_message));
        //chatClientManager = getChatClientManager();
        if (chatClientManager.getChatClient() == null) {

            new AsyncTask<Void, Void, String>(
            ) {
                @Override
                protected String doInBackground(Void... voids) {
                    chatClientManager = App.getInstance().getChatClientManager();
                    initializeClient();
                    return null;
                }
            }.execute();
            //initializeClient();
        } else {
            populateChannels();
        }
    }

    private void initializeClient() {
        chatClientManager.connectClient(new TaskCompletionListener<Void, String>() {
            @Override
            public void onSuccess(Void aVoid) {
                populateChannels();
            }

            @Override
            public void onError(String errorMessage) {
                stopActivityIndicator();
                showAlertWithMessage("Client connection error");
            }
        });
    }

    private void promptLogout() {
        final String message = getStringResource(R.string.logout_prompt_message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogHandler.displayCancellableAlertWithHandler(message, context,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SessionManager.getInstance().logoutUser();
                                showLoginActivity();
                            }
                        });
            }
        });

    }

    private void showLoginActivity() {
        Intent launchIntent = new Intent();
        launchIntent.setClass(getApplicationContext(), SplashActivity.class);
        startActivity(launchIntent);
        finish();
    }

    private void setUsernameTextView() {
        String username =
                SessionManager.getInstance().getUserDetails().get(SessionManager.KEY_USERNAME);
        usernameTextView.setText(username);
    }

    private void stopActivityIndicator() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void showActivityIndicator(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(MainChatActivity.this.mainActivity);
                progressDialog.setMessage(message);
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
            }
        });
    }

    private void showAlertWithMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogHandler.displayAlertWithMessage(message, context);
            }
        });
    }


    @Override
    public void onChannelAdded(Channel channel) {
        System.out.println("Channel Added");
        refreshChannels();
    }

    @Override
    public void onChannelDeleted(final Channel channel) {
        System.out.println("Channel Deleted");
        Channel currentChannel = chatFragment.getCurrentChannel();
        if (channel.getSid().contentEquals(currentChannel.getSid())) {
            chatFragment.setCurrentChannel(null, null);
            //setChannel(0);
        }
        refreshChannels();
    }

    @Override
    public void onChannelInvited(Channel channel) {

    }

    @Override
    public void onChannelSynchronizationChange(Channel channel) {

    }

    @Override
    public void onError(ErrorInfo errorInfo) {

    }

    @Override
    public void onClientSynchronization(ChatClient.SynchronizationStatus synchronizationStatus) {

    }

    @Override
    public void onConnectionStateChange(ChatClient.ConnectionState connectionState) {

    }

    @Override
    public void onChannelJoined(Channel channel) {

    }

    @Override
    public void onChannelUpdated(Channel channel, Channel.UpdateReason updateReason) {

    }

    @Override
    public void onUserUpdated(User user, User.UpdateReason updateReason) {

    }

    @Override
    public void onUserSubscribed(User user) {

    }

    @Override
    public void onUserUnsubscribed(User user) {

    }

    @Override
    public void onNewMessageNotification(String s, String s1, long l) {

    }

    @Override
    public void onAddedToChannelNotification(String s) {

    }

    @Override
    public void onInvitedToChannelNotification(String s) {

    }

    @Override
    public void onRemovedFromChannelNotification(String s) {

    }

    @Override
    public void onNotificationSubscribed() {

    }

    @Override
    public void onNotificationFailed(ErrorInfo errorInfo) {

    }
}

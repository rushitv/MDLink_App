package com.mdlink.chat.channels;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

import com.mdlink.App;
import com.mdlink.chat.ChatClientManager;
import com.mdlink.chat.listeners.TaskCompletionListener;
import com.mdlink.util.Constants;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.Channel.ChannelType;
import com.twilio.chat.ChannelDescriptor;
import com.twilio.chat.Channels;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ChatClientListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Paginator;
import com.twilio.chat.StatusListener;
import com.twilio.chat.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChannelManager implements ChatClientListener {
  private static ChannelManager sharedManager = new ChannelManager();
  public Channel generalChannel;
  private ChatClientManager chatClientManager;
  private ChannelExtractor channelExtractor;
  private List<Channel> channels;
  private Channels channelsObject;
  private ChatClientListener listener;
  private String defaultChannelUniqueName;
  private Handler handler;
  private Boolean isRefreshingChannels = false;

  private ChannelManager() {

  }

  public ChannelManager(String defaultChannelUniqueName) {
    this.chatClientManager = App.getInstance().getChatClientManager();
    this.channelExtractor = new ChannelExtractor();
    this.listener = this;
    this.defaultChannelUniqueName = defaultChannelUniqueName;
    handler = setupListenerHandler();
  }

  public static ChannelManager getInstance() {
    return sharedManager;
  }

  public List<Channel> getChannels() {
    return channels;
  }

  public void setDefaultChannelUniqueName(String defaultChannelUniqueName) {
    this.defaultChannelUniqueName = defaultChannelUniqueName;
  }

  public String getDefaultChannelName() {
    return this.defaultChannelUniqueName;
  }
  public String getDefaultChannelUniqueName() {
    return this.defaultChannelUniqueName;
  }

  public void leaveChannelWithHandler(Channel channel, StatusListener handler) {
    channel.leave(handler);
  }

  public void deleteChannelWithHandler(Channel channel, StatusListener handler) {
    channel.destroy(handler);
  }

  public void populateChannels(final LoadChannelListener listener) {
    if (this.chatClientManager == null || this.isRefreshingChannels) {
      return;
    }
    this.isRefreshingChannels = true;

    handler.post(new Runnable() {
      @Override
      public void run() {
        channelsObject = chatClientManager.getChatClient().getChannels();

        channelsObject.getPublicChannelsList(new CallbackListener<Paginator<ChannelDescriptor>>() {
          @Override
          public void onSuccess(Paginator<ChannelDescriptor> channelDescriptorPaginator) {
            extractChannelsFromPaginatorAndPopulate(channelDescriptorPaginator, listener);
          }
        });

      }
    });
  }

  private void extractChannelsFromPaginatorAndPopulate(final Paginator<ChannelDescriptor> channelsPaginator,
                                                       final LoadChannelListener listener) {
    channels = new ArrayList<>();
    ChannelManager.this.channels.clear();
    channelExtractor.extractAndSortFromChannelDescriptor(channelsPaginator,
        new TaskCompletionListener<List<Channel>, String>() {
      @Override
      public void onSuccess(List<Channel> channels) {
        ChannelManager.this.channels.addAll(channels);
        Collections.sort(ChannelManager.this.channels, new CustomChannelComparator());
        ChannelManager.this.isRefreshingChannels = false;
        chatClientManager.setClientListener(ChannelManager.this);
        listener.onChannelsFinishedLoading(ChannelManager.this.channels);
      }

      @Override
      public void onError(String errorText) {
        System.out.println("Error populating channels: " + errorText);
      }
    });
  }

  public void createChannelWithName(String name, final StatusListener handler) {
    this.channelsObject
        .channelBuilder()
        .withFriendlyName(name)
        .withType(ChannelType.PUBLIC)
        .build(new CallbackListener<Channel>() {
          @Override
          public void onSuccess(final Channel newChannel) {
            handler.onSuccess();
          }

          @Override
          public void onError(ErrorInfo errorInfo) {
            handler.onError(errorInfo);
          }
        });
  }

  public void joinOrCreateGeneralChannelWithCompletion(final StatusListener listener) {
    channelsObject.getChannel(defaultChannelUniqueName, new CallbackListener<Channel>() {
      @Override
      public void onSuccess(Channel channel) {
        ChannelManager.this.generalChannel = channel;

          //createGeneralChannelWithCompletion(listener);

        if (channel != null) {
          System.out.println("sID>>>>"+channel.getSid());
          System.out.println("getUniqueName>>>"+channel.getUniqueName());
          System.out.println("getType>>"+channel.getType());
          System.out.println("getFriendlyName>>>>"+channel.getFriendlyName());
          joinGeneralChannelWithCompletion(listener);
        } else {
          createGeneralChannelWithCompletion(listener);
        }
      }
      @Override
      public void onError(ErrorInfo errorInfo) {
        //listener.onError(errorInfo);
        createGeneralChannelWithCompletion(listener);
      }
    });
  }

  private void joinGeneralChannelWithCompletion(final StatusListener listener) {
    System.out.println("general_channel>>>>>>>>>>"+this.generalChannel.getSid());
    System.out.println("general_channel>>>>>>>>>>"+this.generalChannel.getFriendlyName());
    System.out.println("general_channel>>>>>>>>>>"+this.generalChannel.getUniqueName());

    this.generalChannel.join(new StatusListener() {
      @Override
      public void onSuccess() {
        listener.onSuccess();
      }

      @Override
      public void onError(ErrorInfo errorInfo) {
        listener.onError(errorInfo);
      }
    });
  }

  private void createGeneralChannelWithCompletion(final StatusListener listener) {
    this.channelsObject
        .channelBuilder()
        .withFriendlyName(defaultChannelUniqueName)
        .withUniqueName(defaultChannelUniqueName)
        .withType(ChannelType.PUBLIC)
        .build(new CallbackListener<Channel>() {
          @Override
          public void onSuccess(final Channel channel) {
            ChannelManager.this.generalChannel = channel;
            ChannelManager.this.channels.add(channel);
            joinGeneralChannelWithCompletion(listener);
          }

          @Override
          public void onError(ErrorInfo errorInfo) {
            listener.onError(errorInfo);
          }
        });
  }

  public void setChannelListener(ChatClientListener listener) {
    this.listener = listener;
  }

  @Override
  public void onChannelAdded(Channel channel) {
    if (listener != null) {
      listener.onChannelAdded(channel);
    }
  }

  @Override
  public void onChannelUpdated(Channel channel, Channel.UpdateReason updateReason) {
    if (listener != null) {
      listener.onChannelUpdated(channel, updateReason);
    }
  }

  @Override
  public void onChannelDeleted(Channel channel) {
    if (listener != null) {
      listener.onChannelDeleted(channel);
    }
  }

  @Override
  public void onChannelSynchronizationChange(Channel channel) {
    if (listener != null) {
      listener.onChannelSynchronizationChange(channel);
    }
  }

  @Override
  public void onError(ErrorInfo errorInfo) {
    if (listener != null) {
      listener.onError(errorInfo);
    }
  }

  @Override
  public void onClientSynchronization(ChatClient.SynchronizationStatus synchronizationStatus) {

  }

  @Override
  public void onChannelJoined(Channel channel) {

  }

  @Override
  public void onChannelInvited(Channel channel) {

  }

  @Override
  public void onUserUpdated(User user, User.UpdateReason updateReason) {
    if (listener != null) {
      listener.onUserUpdated(user, updateReason);
    }
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

  @Override
  public void onConnectionStateChange(ChatClient.ConnectionState connectionState) {

  }

  private Handler setupListenerHandler() {
    Looper looper;
    Handler handler;
    if ((looper = Looper.myLooper()) != null) {
      handler = new Handler(looper);
    } else if ((looper = Looper.getMainLooper()) != null) {
      handler = new Handler(looper);
    } else {
      throw new IllegalArgumentException("Channel Listener must have a Looper.");
    }
    return handler;
  }
}

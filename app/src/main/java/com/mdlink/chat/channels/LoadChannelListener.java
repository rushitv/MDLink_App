package com.mdlink.chat.channels;

import com.twilio.chat.Channel;

import java.util.List;

public interface LoadChannelListener {

  void onChannelsFinishedLoading(List<Channel> channels);

}

package com.mdlink.chat.messages;

public interface ChatMessage {

  String getMessageBody();

  String getAuthor();

  String getDateCreated();
}

package com.mdlinkhealth.chat.messages;

public interface ChatMessage {

  String getMessageBody();

  String getAuthor();

  String getDateCreated();
}

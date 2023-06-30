package com.rmnnorbert.InquireNet.dao.model.chat;

import com.rmnnorbert.InquireNet.dto.chat.ChatRegisterDTO;

import java.util.List;

public interface ChatDao {
    List<Chat> getAllChatByUserId(long id);
    Chat findChatByTitle(String title);
    boolean storeChat(ChatRegisterDTO chatDTO);
    boolean deleteChatByTitle(String title);
}

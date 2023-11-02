package com.rmnnorbert.InquireNet.service.chat;

import com.rmnnorbert.InquireNet.dao.model.chat.Chat;
import com.rmnnorbert.InquireNet.dao.model.chat.ChatDAOJdbc;
import com.rmnnorbert.InquireNet.dto.chat.ChatDTO;
import com.rmnnorbert.InquireNet.dto.chat.ChatDeleteRequest;
import com.rmnnorbert.InquireNet.dto.chat.ChatRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatDAOJdbc chatDAOJdbc;
    @Autowired
    public ChatService(ChatDAOJdbc chatDAOJdbc) {
        this.chatDAOJdbc = chatDAOJdbc;
    }
    public List<ChatDTO> getAllChatByUserId(long id){
        return chatDAOJdbc.getAllChatByUserId(id)
                .stream()
                .map(ChatDTO::of)
                .toList();
    }
    public boolean deleteChatByTitle(ChatDeleteRequest dto){
        Chat chat = chatDAOJdbc.findChatByTitle(dto.targetID());
        if(dto.userId() == chat.user_id()){
            return chatDAOJdbc.deleteChatByTitle(dto.targetID());
        }
        return false;
    }
    public boolean storeChat(ChatRegisterDTO chatDTO){
        return chatDAOJdbc.storeChat(chatDTO);
    }
}

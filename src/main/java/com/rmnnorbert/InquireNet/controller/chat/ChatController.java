package com.rmnnorbert.InquireNet.controller.chat;

import com.rmnnorbert.InquireNet.dto.chat.ChatDTO;
import com.rmnnorbert.InquireNet.dto.chat.ChatDeleteRequest;
import com.rmnnorbert.InquireNet.dto.chat.ChatRegisterDTO;
import com.rmnnorbert.InquireNet.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatController {
    private final ChatService chatService;
    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    @GetMapping("/{id}")
    public List<ChatDTO> getAllChatByUser(@PathVariable long id){
        return chatService.getAllChatByUserId(id);
    }
    @PostMapping("/")
    public boolean storeNewChat(@RequestBody ChatRegisterDTO dto){
        return chatService.storeChat(dto);
    }
    @DeleteMapping("/")
    public boolean deleteChatByChatTitle(@RequestBody ChatDeleteRequest dto){
        return chatService.deleteChatByTitle(dto);
    }
}

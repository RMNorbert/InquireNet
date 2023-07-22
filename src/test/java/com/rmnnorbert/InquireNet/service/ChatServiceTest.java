package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.model.chat.Chat;
import com.rmnnorbert.InquireNet.dao.model.chat.ChatDAOJdbc;
import com.rmnnorbert.InquireNet.dto.chat.ChatDTO;
import com.rmnnorbert.InquireNet.dto.chat.ChatDeleteRequest;
import com.rmnnorbert.InquireNet.dto.chat.ChatRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatServiceTest {
    @Mock
    private ChatDAOJdbc chatDAOJdbc;
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chatService = new ChatService(chatDAOJdbc);
    }

    @Test
    void getAllChatByUserIdShouldReturnExpectedChatList() {
        long id = 1;
        List<Chat> chats = List.of(
                new Chat(1,1,"Chat","user","Chat")
        );

        when(chatDAOJdbc.getAllChatByUserId(id)).thenReturn(chats);

        List<ChatDTO> expected = chats.stream().map(ChatDTO::of).toList();
        List<ChatDTO> actual = chatService.getAllChatByUserId(id);

        assertEquals(expected,actual);
    }
    @Test
    void getAllChatByUserIdWhenThereIsNoChatRegisteredToTheUserShouldReturnEmptyList() {
        long id = 2;

        when(chatDAOJdbc.getAllChatByUserId(id)).thenReturn(new ArrayList<>());

        List<ChatDTO> expected = new ArrayList<>();
        List<ChatDTO> actual = chatService.getAllChatByUserId(id);

        assertEquals(expected,actual);
    }
    @Test
    void deleteChatByTitleShouldReturnTrue() {
        ChatDeleteRequest deleteRequest = new ChatDeleteRequest(1,"Title");
        Chat chat = new Chat(1,1,"Title","user","content");
        when(chatDAOJdbc.findChatByTitle(deleteRequest.targetID())).thenReturn(chat);
        when(chatDAOJdbc.deleteChatByTitle(deleteRequest.targetID())).thenReturn(true);

        boolean actual = chatService.deleteChatByTitle(deleteRequest);

        assertTrue(actual);
    }
    @Test
    void deleteChatByTitleWenTitleDoNotExistShouldThrowNotFoundException() {
        ChatDeleteRequest deleteRequest = new ChatDeleteRequest(1,"T");
        when(chatDAOJdbc.findChatByTitle(deleteRequest.targetID())).thenThrow(new NotFoundException("Chat"));

        assertThrows(NotFoundException.class, () -> chatService.deleteChatByTitle(deleteRequest));

        verify(chatDAOJdbc, times(1)).findChatByTitle(deleteRequest.targetID());
        verify(chatDAOJdbc, times(0)).deleteChatByTitle(deleteRequest.targetID());
    }
    @Test
    void deleteChatByTitleWenUserIdDoNotMatchShouldReturnFalse() {
        ChatDeleteRequest deleteRequest = new ChatDeleteRequest(2,"Title");
        Chat chat = new Chat(1,1,"Title","user","content");
        when(chatDAOJdbc.findChatByTitle(deleteRequest.targetID())).thenReturn(chat);

        boolean actual = chatService.deleteChatByTitle(deleteRequest);

        assertFalse(actual);
        verify(chatDAOJdbc, times(0)).deleteChatByTitle(deleteRequest.targetID());
    }

    @Test
    void storeChatShouldReturnTrue() {
        ChatRegisterDTO chatRegisterDTO = new ChatRegisterDTO(1,"Title","user","content");
        when(chatDAOJdbc.storeChat(chatRegisterDTO)).thenReturn(true);

        boolean actual = chatService.storeChat(chatRegisterDTO);

        assertTrue(actual);
        verify(chatDAOJdbc,times(1)).storeChat(chatRegisterDTO);
    }
}

package com.rmnnorbert.InquireNet.controller;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.chat.ChatDTO;
import com.rmnnorbert.InquireNet.dto.chat.ChatDeleteRequest;
import com.rmnnorbert.InquireNet.dto.chat.ChatRegisterDTO;
import com.rmnnorbert.InquireNet.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@UnitTest
@ExtendWith(MockitoExtension.class)
class ChatControllerTest {
    private ChatController chatController;
    @Mock
    private ChatService chatService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        this.chatController = new ChatController(chatService);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedList")
    void getAllChatByUserShouldReturnExpectedChatDTOList(List<ChatDTO> expected) {
        long userId = 1;

        when(chatService.getAllChatByUserId(userId)).thenReturn(expected);

        List<ChatDTO> actual = chatController.getAllChatByUser(userId);

        assertEquals(expected, actual);
        verify(chatService,times(1)).getAllChatByUserId(userId);
    }

    @Test
    void storeNewChatShouldReturnExpectedValue() {
        ChatRegisterDTO dto = new ChatRegisterDTO(1,"title","role","content");
        boolean expected = true;

        when(chatService.storeChat(dto)).thenReturn(expected);

        boolean actual = chatController.storeNewChat(dto);

        assertTrue(actual);
        verify(chatService,times(1)).storeChat(dto);
    }

    @ParameterizedTest
    @MethodSource(value = "provideExpectedValue")
    void deleteChatByChatTitleShouldReturnExpectedValue(boolean expected) {
        ChatDeleteRequest deleteRequest = new ChatDeleteRequest(1,"1");

        when(chatService.deleteChatByTitle(deleteRequest)).thenReturn(expected);

        boolean actual = chatController.deleteChatByChatTitle(deleteRequest);

        assertEquals(expected, actual);
        verify(chatService,times(1)).deleteChatByTitle(deleteRequest);
    }
    @Test
    void deleteChatByChatTitleShouldReturnNotFoundException() {
        ChatDeleteRequest deleteRequest = new ChatDeleteRequest(1,"1");

        when(chatService.deleteChatByTitle(deleteRequest)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> chatController.deleteChatByChatTitle(deleteRequest));
        verify(chatService,times(1)).deleteChatByTitle(deleteRequest);

    }
    private static Stream<Arguments> provideExpectedList() {
        return Stream.of(
                Arguments.of(List.of(
                        new ChatDTO(1,
                                1,
                                "title",
                                "user",
                                "contents"),
                        new ChatDTO(2,
                                1,
                                "title",
                                "user",
                                "content")
                )),
                Arguments.of(List.of())
        );
    }
    private static Stream<Arguments> provideExpectedValue() {
        return Stream.of(
                Arguments.of(true),
                Arguments.of(false)
        );
    }
}

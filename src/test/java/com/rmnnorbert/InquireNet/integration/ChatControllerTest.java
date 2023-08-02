package com.rmnnorbert.InquireNet.integration;

import annotations.IntegrationTest;
import com.rmnnorbert.InquireNet.controller.ChatController;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.chat.ChatDTO;
import com.rmnnorbert.InquireNet.dto.chat.ChatDeleteRequest;
import com.rmnnorbert.InquireNet.dto.chat.ChatRegisterDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
@IntegrationTest
@SpringBootTest
@Sql({ "/clear.sql", "/testInit.sql" })
class ChatControllerTest {
    @Autowired
    private ChatController chatController;
    @Test
    void getAllChatByUserShouldReturnExpectedChatDTOList(){
        long userId = 1;
        int expectedSize = 1;

        assertThat(chatController.getAllChatByUser(userId))
                .size().isEqualTo(expectedSize);

        assertThat(chatController.getAllChatByUser(userId))
                .extracting(ChatDTO::title)
                .containsExactly("chat 1");
    }

    @Test
    void storeNewChatShouldReturnExpectedTrue() {
        ChatRegisterDTO dto = new ChatRegisterDTO(1,"title","role","content");
        boolean expected = true;

        assertThat(chatController.storeNewChat(dto))
                .isEqualTo(expected);
    }

    @Test
    void deleteChatByChatTitleShouldReturnTrue() {
        ChatDeleteRequest deleteRequest = new ChatDeleteRequest(1,"chat 1");
        boolean expected = true;

        assertThat(chatController.deleteChatByChatTitle(deleteRequest))
                .isEqualTo(expected);
    }
    @Test
    void deleteChatByChatTitleShouldReturnNotFoundException() {
        ChatDeleteRequest deleteRequest = new ChatDeleteRequest(1,"1");

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> chatController.deleteChatByChatTitle(deleteRequest));

    }
}

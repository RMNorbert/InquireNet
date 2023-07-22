package com.rmnnorbert.InquireNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.chat.ChatDTO;
import com.rmnnorbert.InquireNet.dto.chat.ChatDeleteRequest;
import com.rmnnorbert.InquireNet.dto.chat.ChatRegisterDTO;
import com.rmnnorbert.InquireNet.service.ChatService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static com.rmnnorbert.InquireNet.controller.AnswerControllerTest.MOCK_USERNAME;
import static org.hamcrest.Matchers.emptyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ChatService chatService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedList")
    @WithMockUser(username = MOCK_USERNAME)
    void getAllChatByUserShouldReturnOkStatusAndExpectedChatDTOList(List<ChatDTO> expected) throws Exception {
        long userId = 1;

        when(chatService.getAllChatByUserId(userId)).thenReturn(expected);

        mockMvc.perform(get("/chat/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(expected.size())));

        verify(chatService,times(1)).getAllChatByUserId(userId);
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void storeNewChatShouldReturnOkStatusAndExpectedValue() throws Exception {
        ChatRegisterDTO dto = new ChatRegisterDTO(1,"title","role","content");
        boolean expected = true;

        when(chatService.storeChat(dto)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/chat/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(expected)));

        verify(chatService,times(1)).storeChat(dto);
    }

    @ParameterizedTest
    @MethodSource(value = "provideExpectedValue")
    @WithMockUser(username = MOCK_USERNAME)
    void deleteChatByChatTitleShouldReturnOkStatusAndExpectedValue(boolean expected) throws Exception {
        ChatDeleteRequest deleteRequest = new ChatDeleteRequest(1,"1");

        when(chatService.deleteChatByTitle(deleteRequest)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(deleteRequest);

        mockMvc.perform(delete("/chat/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(expected)));

        verify(chatService,times(1)).deleteChatByTitle(deleteRequest);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void deleteChatByChatTitleShouldReturnNotFoundStatusAndEmptyBody() throws Exception {
        ChatDeleteRequest deleteRequest = new ChatDeleteRequest(1,"1");

        when(chatService.deleteChatByTitle(deleteRequest)).thenThrow(NotFoundException.class);

        String jsonRequest = new ObjectMapper().writeValueAsString(deleteRequest);

        mockMvc.perform(delete("/chat/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().string(emptyString()));

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

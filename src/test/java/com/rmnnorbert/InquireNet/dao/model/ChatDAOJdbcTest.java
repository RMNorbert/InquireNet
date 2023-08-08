package com.rmnnorbert.InquireNet.dao.model;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.ChatRowMapper;
import com.rmnnorbert.InquireNet.dao.model.chat.Chat;
import com.rmnnorbert.InquireNet.dao.model.chat.ChatDAOJdbc;
import com.rmnnorbert.InquireNet.dto.chat.ChatRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@UnitTest
@RunWith(MockitoJUnitRunner.class)
class ChatDAOJdbcTest {
    private ChatDAOJdbc chatDAOJdbc;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.chatDAOJdbc = new ChatDAOJdbc(jdbcTemplate);
    }
    @ParameterizedTest
    @MethodSource("provideIdAndExpectedList")
    void getAllChatByUserIdShouldReturnExpectedList(long id, List<Chat> expected) {
        when(jdbcTemplate.query(anyString(),any(ChatRowMapper.class),eq(id))).thenReturn(expected);

        List<Chat> actual = chatDAOJdbc.getAllChatByUserId(id);

        assertThat(actual).isEqualTo(expected);
        verify(jdbcTemplate,times(1)).query(anyString(), any(ChatRowMapper.class), eq(id));
    }

    @Test
    void findChatByTitleShouldReturnExpectedChat() {
        String searchedTitle = "title";
        List<Chat> foundChat = List.of(new Chat(1,1,"title","role","content"));

        when(jdbcTemplate.query(anyString(),any(ChatRowMapper.class), eq(searchedTitle))).thenReturn(foundChat);

        Chat actual = chatDAOJdbc.findChatByTitle(searchedTitle);
        Chat expected = foundChat.get(0);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).query(anyString(),any(ChatRowMapper.class), eq(searchedTitle));
    }
    @Test
    void findChatByTitleShouldReturnNotFoundException() {
        String searchedTitle = "title";

        when(jdbcTemplate.query(anyString(),any(ChatRowMapper.class), eq(searchedTitle))).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> chatDAOJdbc.findChatByTitle(searchedTitle));
        verify(jdbcTemplate,times(1)).query(anyString(),any(ChatRowMapper.class), eq(searchedTitle));
    }

    @Test
    void storeChatShouldReturnExpectedBoolean() {
        int value = 1;
        ChatRegisterDTO dto = new ChatRegisterDTO(1,"title","role","content");

        when(jdbcTemplate.update(anyString(),
                eq(dto.userId()),
                eq(dto.title()),
                eq(dto.role()),
                eq(dto.content()))).thenReturn(value);

        boolean actual = chatDAOJdbc.storeChat(dto);

        assertTrue(actual);
        verify(jdbcTemplate,times(1)).update(anyString(),eq(dto.userId()),eq(dto.title()),eq(dto.role()),eq(dto.content()));
    }

    @Test
    void deleteChatByTitleShouldReturnExpectedBoolean() {
        String title = "title";
        int value = 1;

        when(jdbcTemplate.update(anyString(),eq(title))).thenReturn(value);

        boolean actual = chatDAOJdbc.deleteChatByTitle(title);

        assertTrue(actual);
        verify(jdbcTemplate,times(1)).update(anyString(),eq(title));
    }

    private static Stream<Arguments> provideIdAndExpectedList() {
        return Stream.of(
                Arguments.of(1, List.of(new Chat(1,1,"title","role","content"))),
                Arguments.of(2, List.of())

        );
    }
}

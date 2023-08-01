package com.rmnnorbert.InquireNet.dao.model;

import com.rmnnorbert.InquireNet.dao.ReplyRowMapper;
import com.rmnnorbert.InquireNet.dao.model.reply.Reply;
import com.rmnnorbert.InquireNet.dao.model.reply.ReplyDAOJdbc;
import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ReplyDAOJdbcTest {
    private ReplyDAOJdbc replyDAOJdbc;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.replyDAOJdbc = new ReplyDAOJdbc(jdbcTemplate);
    }

    @ParameterizedTest
    @MethodSource(value = "provideExpectedList")
    void getAllReplyShouldReturnExpectedReplyList(List<Reply> expected) {
        when(jdbcTemplate.query(anyString(),any(ReplyRowMapper.class))).thenReturn(expected);

        List<Reply> actual = replyDAOJdbc.getAllReply();

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).query(anyString(),any(ReplyRowMapper.class));
    }

    @Test
    void findReplyByIdShouldReturnExpectedReply() {
        long searchedId = 1;
        List<Reply> returnValue = List.of(new Reply(1,1,1,"desc", LocalDateTime.now()));

        when(jdbcTemplate.query(anyString(),any(ReplyRowMapper.class), eq(searchedId))).thenReturn(returnValue);

        Reply expected = returnValue.get(0);
        Reply actual = replyDAOJdbc.findReplyById(searchedId);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).query(anyString(),any(ReplyRowMapper.class),eq(searchedId));
    }

    @ParameterizedTest
    @MethodSource(value = "provideExpectedList")
    void getAllReplyByAnswerIdShouldReturnExpectedReplyList(List<Reply> expected) {
        long id = 1;

        when(jdbcTemplate.query(anyString(),any(ReplyRowMapper.class), eq(id))).thenReturn(expected);

        List<Reply> actual = replyDAOJdbc.getAllReplyByAnswerId(id);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).query(anyString(),any(ReplyRowMapper.class), eq(id));
    }

    @ParameterizedTest
    @MethodSource(value = "provideReturnValueAndExpectedBooleanValue")
    void addReplyShouldReturnExpectedBoolean(int returnValue , boolean expected) {
        NewReplyDTO newReplyDTO = new NewReplyDTO("desc",1,1);

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any())).thenReturn(returnValue);

        boolean actual = replyDAOJdbc.addReply(newReplyDTO);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).update(anyString(), any(), any(), any(), any());
    }

    @ParameterizedTest
    @MethodSource(value = "provideReturnValueAndExpectedBooleanValue")
    void deleteReplyByIdShouldReturnExpectedBoolean(int returnValue , boolean expected) {
        long id = 1;

        when(jdbcTemplate.update(anyString(),eq(id))).thenReturn(returnValue);

        boolean actual = replyDAOJdbc.deleteReplyById(id);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).update(anyString(),eq(id));
    }

    @ParameterizedTest
    @MethodSource(value = "provideReturnValueAndExpectedBooleanValue")
    void deleteReplyByAnswerIdShouldReturnExpectedBoolean(int returnValue, boolean expected) {
        long id = 1;
        when(jdbcTemplate.update(anyString(),eq(id))).thenReturn(returnValue);

        boolean actual = replyDAOJdbc.deleteReplyByAnswerId(id);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).update(anyString(),eq(id));
    }

    @ParameterizedTest
    @MethodSource(value = "provideReturnValueAndExpectedBooleanValue")
    void updateShouldReturnExpectedBoolean(int returnValue, boolean expected) {
        LocalDateTime created = LocalDateTime.now();
        Reply replyToUpdate = new Reply(1,1,1,"desc",created);

        when(jdbcTemplate.update(anyString(),eq(replyToUpdate.description()))).thenReturn(returnValue);

        boolean actual = replyDAOJdbc.update(replyToUpdate);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).update(anyString(),eq(replyToUpdate.description()));
    }

    private static Stream<Arguments> provideExpectedList() {
        return Stream.of(
                Arguments.of(List.of(new Reply(1,1,1,"desc", LocalDateTime.now()))),
                Arguments.of(List.of())
        );
    }
    private static Stream<Arguments> provideReturnValueAndExpectedBooleanValue() {
        return Stream.of(
                Arguments.of(1,true),
                Arguments.of(0,false)
        );
    }
}

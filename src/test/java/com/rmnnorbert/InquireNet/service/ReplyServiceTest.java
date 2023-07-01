package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.model.reply.Reply;
import com.rmnnorbert.InquireNet.dao.model.reply.ReplyDAOJdbc;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReplyServiceTest {
    @Mock
    private ReplyDAOJdbc replyDAOJdbc;
    private ReplyService replyService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        replyService = new ReplyService(replyDAOJdbc);
    }

    @Test
    void getAllReply() {
        List<Reply> replies = List.of(
                new Reply(1,1,1,"desc", LocalDateTime.now()),
                new Reply(2,1,1,"description", LocalDateTime.now())
        );
        when(replyDAOJdbc.getAllReply()).thenReturn(replies);

        List<ReplyDTO> expected = replies.stream()
                                         .map(ReplyDTO::of)
                                         .toList();
        List<ReplyDTO> actual = replyService.getAllReply();

        assertEquals(expected, actual);
    }
    @Test
    void getAllReplyWhenThereIsNoReply() {
        when(replyDAOJdbc.getAllReply()).thenReturn(new ArrayList<>());

        List<ReplyDTO> expected = new ArrayList<>();
        List<ReplyDTO> actual = replyService.getAllReply();

        assertEquals(expected,actual);
    }

    @Test
    void getReplyById() {
        long replyId = 1L;
        Reply reply = new Reply(1,1,1,"desc",LocalDateTime.now());
        when(replyDAOJdbc.findReplyById(replyId)).thenReturn(reply);

        ReplyDTO expected = ReplyDTO.of(reply);
        ReplyDTO actual = replyService.getReplyById(replyId);
        assertEquals(expected,actual);
    }
    @Test
    void getReplyByIdWithWrongId() {
        long replyId = 1L;
        when(replyDAOJdbc.findReplyById(replyId)).thenThrow(new NotFoundException("Reply"));

        assertThrows(NotFoundException.class, () -> replyService.getReplyById(replyId));

        verify(replyDAOJdbc,times(1)).findReplyById(replyId);
    }
    @Test
    void getAllReplyByAnswerId() {
        long answerId = 1;
        List<Reply> replies = List.of(
                new Reply(1,1,1,"desc", LocalDateTime.now()),
                new Reply(2,1,1,"description", LocalDateTime.now())
        );
        when(replyDAOJdbc.getAllReplyByAnswerId(answerId)).thenReturn(replies);

        List<ReplyDTO> expected = replies.stream()
                .map(ReplyDTO::of)
                .toList();
        List<ReplyDTO> actual = replyService.getAllReplyByAnswerId(answerId);

        assertEquals(expected, actual);
    }
    @Test
    void getAllReplyByAnswerIdWhenThereAreNoReplies() {
        long answerId = 1;
        when(replyDAOJdbc.getAllReplyByAnswerId(answerId)).thenReturn(new ArrayList<>());

        List<ReplyDTO> expected = new ArrayList<>();
        List<ReplyDTO> actual = replyService.getAllReply();

        assertEquals(expected,actual);
    }
    @Test
    void deleteReplyById() {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);
        Reply reply = new Reply(1,1,1,"desc",LocalDateTime.now());
        when(replyDAOJdbc.findReplyById(dto.targetId())).thenReturn(reply);
        when(replyDAOJdbc.deleteReplyById(dto.targetId())).thenReturn(true);

        boolean actual = replyService.deleteReplyById(dto);

        assertTrue(actual);
        verify(replyDAOJdbc,times(1)).findReplyById(dto.targetId());
        verify(replyDAOJdbc,times(1)).deleteReplyById(dto.targetId());
    }

    @Test
    void deleteReplyByIdWithWrongReplyId() {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,2);
        when(replyDAOJdbc.findReplyById(dto.targetId())).thenThrow(new NotFoundException("Reply"));

        assertThrows(NotFoundException.class, () -> replyService.getReplyById(dto.targetId()));

        verify(replyDAOJdbc,times(1)).findReplyById(dto.targetId());
        verify(replyDAOJdbc,times(0)).deleteReplyById(dto.targetId());
    }
    @Test
    void deleteReplyByIdWithWrongUserId() {
        DeleteRequestDTO dto = new DeleteRequestDTO(2,1);
        Reply reply = new Reply(1,1,1,"desc",LocalDateTime.now());
        when(replyDAOJdbc.findReplyById(dto.targetId())).thenReturn(reply);

        boolean actual = replyService.deleteReplyById(dto);

        assertFalse(actual);
        verify(replyDAOJdbc,times(0)).findReplyById(dto.targetId());
        verify(replyDAOJdbc,times(0)).deleteReplyById(dto.targetId());
    }
    @Test
    void updateReply() {
        ReplyDTO replyDTO = new ReplyDTO(1,1,1,"desc",LocalDateTime.now());
        when(replyDAOJdbc.update(replyDTO)).thenReturn(true);
        boolean actual = replyService.updateReply(replyDTO);

        assertTrue(actual);
        verify(replyDAOJdbc,times(1)).update(replyDTO);
    }
    @Test
    void updateReplyWhenUserIdIsWrong() {
        ReplyDTO replyDTO = new ReplyDTO(1,1,1,"desc",LocalDateTime.now());
        Reply foundReply = new Reply(1,2,1,"desc",LocalDateTime.now());
        when(replyDAOJdbc.findReplyById(replyDTO.reply_id())).thenReturn(foundReply);

        boolean actual = replyService.updateReply(replyDTO);

        assertFalse(actual);

        verify(replyDAOJdbc,times(1)).findReplyById(replyDTO.reply_id());
        verify(replyDAOJdbc,times(0)).update(replyDTO);
    }
    @Test
    void updateReplyWhenReplyIdIsWrong() {
        ReplyDTO replyDTO = new ReplyDTO(1,1,1,"desc",LocalDateTime.now());
        when(replyDAOJdbc.findReplyById(replyDTO.reply_id())).thenThrow(new NotFoundException("Reply"));

        assertThrows(NotFoundException.class, () -> replyService.getReplyById(replyDTO.reply_id()));

        verify(replyDAOJdbc,times(1)).findReplyById(replyDTO.reply_id());
        verify(replyDAOJdbc,times(0)).update(replyDTO);
    }
    @Test
    void addNewReply() {
        NewReplyDTO dto = new NewReplyDTO("desc",1,1);
        boolean modifiedRows = true;
        when(replyDAOJdbc.addReply(dto)).thenReturn(modifiedRows);

        boolean actual = replyService.addNewReply(dto);

        assertTrue(actual);
        verify(replyDAOJdbc, times(1)).addReply(dto);
    }

    @Test
    void deleteAllReplyOfAnswer() {
        long answerId = 1;
        when(replyDAOJdbc.deleteReplyByAnswerId(answerId)).thenReturn(true);

        boolean actual = replyService.deleteAllReplyOfAnswer(answerId);
        assertTrue(actual);
    }
    @Test
    void deleteAllReplyOfAnswerWithWrongAnswerId() {
        long answerId = 1;
        when(replyDAOJdbc.deleteReplyByAnswerId(answerId)).thenReturn(false);

        boolean actual = replyService.deleteAllReplyOfAnswer(answerId);
        assertFalse(actual);
    }
}

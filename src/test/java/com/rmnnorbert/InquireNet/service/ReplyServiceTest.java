package com.rmnnorbert.InquireNet.service;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.model.reply.Reply;
import com.rmnnorbert.InquireNet.dao.model.reply.ReplyDAOJdbc;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyUpdateDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@UnitTest
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
    void getAllReplyShouldReturnExpectedReplyList() {
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
        verify(replyDAOJdbc,times(1)).getAllReply();
    }
    @Test
    void getAllReplyWhenThereIsNoReplyShouldReturnEmptyList() {
        when(replyDAOJdbc.getAllReply()).thenReturn(new ArrayList<>());

        List<ReplyDTO> expected = new ArrayList<>();
        List<ReplyDTO> actual = replyService.getAllReply();

        assertEquals(expected,actual);
        verify(replyDAOJdbc,times(1)).getAllReply();
    }

    @Test
    void getReplyByIdShouldReturnExpectedReplyDTO() {
        long replyId = 1L;
        Reply reply = new Reply(1,1,1,"desc",LocalDateTime.now());

        when(replyDAOJdbc.findReplyById(replyId)).thenReturn(reply);

        ReplyDTO expected = ReplyDTO.of(reply);
        ReplyDTO actual = replyService.getReplyById(replyId);

        assertEquals(expected,actual);
        verify(replyDAOJdbc,times(1)).findReplyById(replyId);
    }
    @Test
    void getReplyByIdWithWrongIdShouldThrowNotFoundException() {
        long replyId = 1L;

        when(replyDAOJdbc.findReplyById(replyId)).thenThrow(new NotFoundException("Reply"));

        assertThrows(NotFoundException.class, () -> replyService.getReplyById(replyId));

        verify(replyDAOJdbc,times(1)).findReplyById(replyId);
    }
    @Test
    void getAllReplyByAnswerIdShouldReturnExpectedReplyDTOList() {
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
        verify(replyDAOJdbc,times(1)).getAllReplyByAnswerId(answerId);
    }
    @Test
    void getAllReplyByAnswerIdWhenThereAreNoRepliesShouldReturnEmptyList() {
        long answerId = 1;

        when(replyDAOJdbc.getAllReplyByAnswerId(answerId)).thenReturn(new ArrayList<>());

        List<ReplyDTO> expected = new ArrayList<>();
        List<ReplyDTO> actual = replyService.getAllReply();

        assertEquals(expected,actual);
        verify(replyDAOJdbc,times(1)).getAllReply();
    }
    @Test
    void deleteReplyByIdShouldReturnTrue() {
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
    void deleteReplyByIdWithWrongReplyIdShouldThrowNotFoundException() {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,2);

        when(replyDAOJdbc.findReplyById(dto.targetId())).thenThrow(new NotFoundException("Reply"));

        assertThrows(NotFoundException.class, () -> replyService.getReplyById(dto.targetId()));

        verify(replyDAOJdbc,times(1)).findReplyById(dto.targetId());
        verify(replyDAOJdbc,times(0)).deleteReplyById(dto.targetId());
    }
    @Test
    void deleteReplyByIdWithWrongUserIdShouldReturnFalse() {
        DeleteRequestDTO dto = new DeleteRequestDTO(2,1);
        Reply reply = new Reply(1,1,1,"desc",LocalDateTime.now());

        when(replyDAOJdbc.findReplyById(dto.targetId())).thenReturn(reply);

        boolean actual = replyService.deleteReplyById(dto);

        assertFalse(actual);
        verify(replyDAOJdbc,times(1)).findReplyById(dto.targetId());
        verify(replyDAOJdbc,times(0)).deleteReplyById(dto.targetId());
    }
    @Test
    void updateReplyShouldReturnTrue() {
        ReplyUpdateDTO reply = new ReplyUpdateDTO(1,1,"desc");
        Reply searchedReply = new Reply(1,1,1,"des", LocalDateTime.now());
        UpdateDTO updateDTO = new UpdateDTO(1,1,"title","desc");

        when(replyDAOJdbc.findReplyById(updateDTO.id())).thenReturn(searchedReply);
        when(replyDAOJdbc.update(searchedReply.withDescription(reply.description()))).thenReturn(true);

        boolean actual = replyService.updateReply(reply);

        assertTrue(actual);
        verify(replyDAOJdbc,times(1)).update(searchedReply.withDescription(reply.description()));
    }
    @Test
    void updateReplyWhenUserIdIsWrongShouldReturnFalse() {
        ReplyUpdateDTO updateDTO = new ReplyUpdateDTO(1,1,"title");
        Reply foundReply = new Reply(1,2,1,"desc",LocalDateTime.now());

        when(replyDAOJdbc.findReplyById(updateDTO.id())).thenReturn(foundReply);

        boolean actual = replyService.updateReply(updateDTO);

        assertFalse(actual);

        verify(replyDAOJdbc,times(1)).findReplyById(updateDTO.id());
        verify(replyDAOJdbc,times(0)).update(foundReply);
    }
    @Test
    void updateReplyWhenReplyIdIsWrongShouldThrowNotFoundException() {
        ReplyUpdateDTO updateDTO = new ReplyUpdateDTO(1,1,"title");

        when(replyDAOJdbc.findReplyById(updateDTO.id())).thenThrow(new NotFoundException("Reply"));

        assertThrows(NotFoundException.class, () -> replyService.getReplyById(updateDTO.id()));

        verify(replyDAOJdbc,times(1)).findReplyById(updateDTO.id());
    }
    @Test
    void addNewReplyShouldReturnTrue() {
        NewReplyDTO dto = new NewReplyDTO("desc",1,1);
        boolean modifiedRows = true;

        when(replyDAOJdbc.addReply(dto)).thenReturn(modifiedRows);

        boolean actual = replyService.addNewReply(dto);

        assertTrue(actual);
        verify(replyDAOJdbc, times(1)).addReply(dto);
    }

    @Test
    void deleteAllReplyOfAnswerShouldReturnTrue() {
        long answerId = 1;

        when(replyDAOJdbc.deleteReplyByAnswerId(answerId)).thenReturn(true);

        boolean actual = replyService.deleteAllReplyOfAnswer(answerId);
        assertTrue(actual);
    }
    @Test
    void deleteAllReplyOfAnswerWithWrongAnswerIdShouldReturnFalse() {
        long answerId = 1;

        when(replyDAOJdbc.deleteReplyByAnswerId(answerId)).thenReturn(false);

        boolean actual = replyService.deleteAllReplyOfAnswer(answerId);
        assertFalse(actual);
    }
}

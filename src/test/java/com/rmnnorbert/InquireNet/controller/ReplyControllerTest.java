package com.rmnnorbert.InquireNet.controller;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyUpdateDTO;
import com.rmnnorbert.InquireNet.service.ReplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@UnitTest
@ExtendWith(MockitoExtension.class)
class ReplyControllerTest {
    private ReplyController replyController;
    @Mock
    private ReplyService replyService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        this.replyController = new ReplyController(replyService);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedList")
    void getAllReplyShouldReturnExpectedReplyDTOList(List<ReplyDTO> expected) {
        when(replyService.getAllReply()).thenReturn(expected);

        List<ReplyDTO> actual = replyController.getAllReply();

        assertEquals(expected, actual);
        verify(replyService, times(1)).getAllReply();
    }

    @Test
    void getReplyByIdShouldReturnReplyDTO() {
        long searchedId = 1;
        ReplyDTO replyDTO = new ReplyDTO(1,1,1,"desc",LocalDateTime.now());

        when(replyService.getReplyById(searchedId)).thenReturn(replyDTO);

        ReplyDTO actual = replyController.getReplyById(searchedId);

        assertEquals(replyDTO, actual);
        verify(replyService, times(1)).getReplyById(searchedId);
    }

    @Test
    void getReplyByIdShouldReturnNotFoundException() {
        long searchedId = 1;

        when(replyService.getReplyById(searchedId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> replyController.getReplyById(searchedId));
        verify(replyService, times(1)).getReplyById(searchedId);
    }
    @ParameterizedTest
    @MethodSource("provideExpectedList")
    void getAllReplyByAnswerIdShouldReturnExpectedReplyDTOList(List<ReplyDTO> expected) {
        long searchedId = 1;

        when(replyService.getAllReplyByAnswerId(searchedId)).thenReturn(expected);

        List<ReplyDTO> actual = replyController.getAllReplyByAnswerId(searchedId);
        assertEquals(expected, actual);
        verify(replyService,times(1)).getAllReplyByAnswerId(searchedId);
    }

    @Test
    void addNewReplyShouldReturnExpectedValue() {
        boolean expected = true;
        NewReplyDTO dto = new NewReplyDTO("desc",1,1);

        when(replyService.addNewReply(dto)).thenReturn(expected);

        boolean actual = replyController.addNewReply(dto);
        assertTrue(actual);
        verify(replyService,times(1)).addNewReply(dto);
    }

    @ParameterizedTest
    @MethodSource("provideUpdateValueAndExpectedValue")
    void updateReplyShouldReturnExpectedValue(ReplyUpdateDTO value , boolean expected) {
        when(replyService.updateReply(value)).thenReturn(expected);

        boolean actual = replyController.updateReply(value);
        assertEquals(expected, actual);
        verify(replyService,times(1)).updateReply(value);
    }
    @Test
    void updateReplyShouldReturnNotFoundException() {
        ReplyUpdateDTO dto = new ReplyUpdateDTO(1,1,"desc");

        when(replyService.updateReply(dto)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> replyController.updateReply(dto));
        verify(replyService,times(1)).updateReply(dto);
    }

    @ParameterizedTest
    @MethodSource("provideIdAndDeleteValueAndExpectedValue")
    void deleteReplyByIdShouldReturnExpectedValue(DeleteRequestDTO value, boolean expected) {
        when(replyService.deleteReplyById(value)).thenReturn(expected);

        boolean actual = replyController.deleteReplyById(value);

        assertEquals(expected, actual);
        verify(replyService,times(1)).deleteReplyById(value);
    }

    @Test
    void deleteReplyByIdShouldReturnNotFoundException() {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);

        when(replyService.deleteReplyById(dto)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, ()-> replyController.deleteReplyById(dto));
        verify(replyService,times(1)).deleteReplyById(dto);
    }
    private static Stream<Arguments> provideExpectedList() {
        return Stream.of(
                Arguments.of(List.of(
                        new ReplyDTO(1,1,1,"title", LocalDateTime.now()),
                        new ReplyDTO(2,2,1,"titles", LocalDateTime.now())
                )),
                Arguments.of(List.of())
        );
    }
    private static Stream<Arguments> provideUpdateValueAndExpectedValue() {
        return Stream.of(
                Arguments.of( new ReplyUpdateDTO(1,1,"desc"),true),
                Arguments.of( new ReplyUpdateDTO(1,1,"desc"),false)
        );
    }
    private static Stream<Arguments> provideIdAndDeleteValueAndExpectedValue() {
        return Stream.of(
                Arguments.of(new DeleteRequestDTO(1,1),true),
                Arguments.of(new DeleteRequestDTO(1,1),true)
        );
    }
}

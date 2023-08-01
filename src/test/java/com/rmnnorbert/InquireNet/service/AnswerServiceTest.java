package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.model.answer.Answer;
import com.rmnnorbert.InquireNet.dao.model.answer.AnswerDAOJdbc;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.AnswerRequestDTO;
import com.rmnnorbert.InquireNet.dto.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnswerServiceTest {
    @Mock
    private AnswerDAOJdbc answerDAOJdbc;
    @Mock
    private ReplyService replyService;
    private AnswerService answerService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        this.answerService = new AnswerService(answerDAOJdbc, replyService);
    }

    @Test
    void getAllAnswersWhenAnswersExistShouldReturnExpectedAnswerList() {
        List<Answer> answers = List.of(
                new Answer(1,1,1,"desc",LocalDateTime.now(),0,"un voted"),
                new Answer(2,1,2,"title", LocalDateTime.now(),0,"un voted")
        );

        when(answerDAOJdbc.getAllAnswers()).thenReturn(answers);

        List<AnswerDTO> answerDTOS = answerService.getAllAnswers();

        assertEquals(answers.size(), answerDTOS.size());
    }
    @Test
    void getAllAnswersWhenNoAnswersExistShouldReturnEmptyList() {
        int expectedListSize = 0;
        List<AnswerDTO> answerDTOS = answerService.getAllAnswers();

        when(answerDAOJdbc.getAllAnswers()).thenReturn(new ArrayList<>());

        assertEquals(expectedListSize, answerDTOS.size());
        verify(answerDAOJdbc,times(1)).getAllAnswers();
    }
    @Test
    void getAnswerByIdShouldReturnExpectedAnswer() {
        int id = 1;
        Answer answer = new Answer(1,1,1,"desc",LocalDateTime.now(),0,"un voted");

        when(answerDAOJdbc.findAnswerById(id)).thenReturn(answer);

        AnswerDTO foundAnswer = answerService.getAnswerById(id);

        assertAnswerEquals(answer, foundAnswer);
        verify(answerDAOJdbc, times(1)).findAnswerById(id);
    }

    @Test
    void getAnswerByIdWithWrongIdShouldThrowNotFoundException() {
        int id = 1;

        when(answerDAOJdbc.findAnswerById(id)).thenThrow(new NotFoundException("Answer"));

        assertThrows(NotFoundException.class, () -> answerService.getAnswerById(id));
        verify(answerDAOJdbc, times(1)).findAnswerById(id);
    }

    @Test
    void getAllAnswersByQuestionIdShouldReturnExpectedAnswerList() {
        long id = 1;
        List<Answer> mockAnswers = List.of(
                new Answer(1, 1, 1, "User", LocalDateTime.now(), 0, "unvoted"),
                new Answer(2, 1, 1, "User", LocalDateTime.now(), 0, "unvoted")
        );

        when(answerDAOJdbc.getAllAnswersByQuestionId(id)).thenReturn(mockAnswers);

        List<AnswerDTO> expected = mockAnswers.stream()
                                              .map(AnswerDTO::of)
                                              .toList();
        List<AnswerDTO> actual = answerService.getAllAnswersByQuestionId(id);

        assertEquals(expected, actual);
        verify(answerDAOJdbc).getAllAnswersByQuestionId(id);
    }
    @Test
    void getAllAnswersByQuestionIdWhenQuestionHaveNoAnswerShouldReturnEmptyList() {
        long id = 1;
        when(answerDAOJdbc.getAllAnswersByQuestionId(id)).thenReturn(new ArrayList<>());

        List<AnswerDTO> actual = answerService.getAllAnswersByQuestionId(id);
        List<AnswerDTO> expected = new ArrayList<>();

        assertEquals(expected, actual);
        verify(answerDAOJdbc).getAllAnswersByQuestionId(id);
    }

    @Test
    void deleteAnswerByIdWithAppropriateIdShouldReturnTrue() {
        long id = 1;
        Answer answer = new Answer(1,1,1,"desc",LocalDateTime.now(),0,"un voted");

        when(answerDAOJdbc.findAnswerById(id)).thenReturn(answer);
        when(answerDAOJdbc.deleteAnswerById(id)).thenReturn(true);

        boolean actual = answerService.deleteAnswerById(new DeleteRequestDTO(1,1));

        assertTrue(actual);
        verify(answerDAOJdbc, times(1)).deleteAnswerById(answer.question_id());
    }

    @Test
    void deleteAnswerByIdWithWrongAnswerIdShouldThrowNotFoundException() {
        DeleteRequestDTO dto = new DeleteRequestDTO(2L,2L);
        when(answerDAOJdbc.findAnswerById(dto.targetId())).thenThrow(new NotFoundException("Answer"));

        assertThrows(NotFoundException.class, () -> answerService.deleteAnswerById(dto));

        verify(answerDAOJdbc, times(1)).findAnswerById(dto.targetId());
        verify(answerDAOJdbc, times(0)).deleteAnswerById(dto.targetId());
    }

    @Test
    void deleteAnswerByIdWithWrongUserIdShouldReturnFalse() {
        Answer answer = new Answer(2,1,1,"desc",LocalDateTime.now(),0,"un voted");
        DeleteRequestDTO dto = new DeleteRequestDTO(2L,2L);

        when(answerDAOJdbc.findAnswerById(dto.targetId())).thenReturn(answer);

        assertFalse(answerService.deleteAnswerById(dto));
        verify(answerDAOJdbc,times(1)).findAnswerById(dto.targetId());
        verify(answerDAOJdbc,times(0)).deleteAnswerById(dto.targetId());
    }

    @Test
    void updateAnswerWithAppropriateRequestShouldReturnTrue() {
        AnswerRequestDTO answerRequestDTO = new AnswerRequestDTO(1,"desc",1);
        Answer answer = new Answer(1,1,1,"desc",LocalDateTime.now(),0,"un voted");
        UpdateDTO updateDTO = new UpdateDTO(1,1, "desc","1");

        when(answerDAOJdbc.findAnswerById(answerRequestDTO.id())).thenReturn(answer);
        when(answerDAOJdbc.update(updateDTO)).thenReturn(true);

        boolean actual = answerService.update(updateDTO);

        assertTrue(actual);
        verify(answerDAOJdbc, times(1)).findAnswerById(answerRequestDTO.id());
        verify(answerDAOJdbc, times(1)).update(updateDTO);
    }
    @Test
    void updateAnswerWithWrongAnswerIdShouldThrowNotFoundException() {
        UpdateDTO updateDTO = new UpdateDTO(1, 1,"desc","test");
        when(answerDAOJdbc.findAnswerById(updateDTO.id())).thenThrow(new NotFoundException("Answer"));

        assertThrows(NotFoundException.class, () -> answerService.update(updateDTO));
        verify(answerDAOJdbc, times(1)).findAnswerById(updateDTO.id());
        verify(answerDAOJdbc, times(0)).update(updateDTO);
    }
    @Test
    void updateAnswerWithWrongUserIdShouldReturnFalse() {
        UpdateDTO updateDTO = new UpdateDTO(1,1,"title","desc");
        Answer answer = new Answer(1,2,1,"desc",LocalDateTime.now(),0,"un voted");

        when(answerDAOJdbc.findAnswerById(updateDTO.id())).thenReturn(answer);

        boolean actual = answerService.update(updateDTO);

        assertFalse(actual);
        verify(answerDAOJdbc, times(1)).findAnswerById(updateDTO.id());
        verify(answerDAOJdbc, times(0)).update(updateDTO);
    }
    @Test
    void addNewAnswerShouldReturnTrue() {
        AnswerRequestDTO answerRequestDTO = new AnswerRequestDTO(1,"User",1);
        boolean modifiedRows = true;

        when(answerDAOJdbc.addAnswer(answerRequestDTO)).thenReturn(modifiedRows);

        boolean actual = answerService.addNewAnswer(answerRequestDTO);

        assertTrue(actual);
        verify(answerDAOJdbc, times(1)).addAnswer(answerRequestDTO);
    }
    @Test
    void addNewAnswerShouldReturnFalse() {
        AnswerRequestDTO answerRequestDTO = new AnswerRequestDTO(1,"User",1);
        boolean modifiedRows = false;

        when(answerDAOJdbc.addAnswer(answerRequestDTO)).thenReturn(modifiedRows);

        boolean actual = answerService.addNewAnswer(answerRequestDTO);

        assertFalse(actual);
        verify(answerDAOJdbc, times(1)).addAnswer(answerRequestDTO);
    }
    @Test
    void deleteAnswersShouldReturnTrue() {
        List<AnswerDTO> answerDTOS = List.of(
                new AnswerDTO(1,1,1,"desc",LocalDateTime.now(),0,"un voted"),
                new AnswerDTO(2,1,2,"title", LocalDateTime.now(),0,"un voted"));

        boolean actual = answerService.deleteAnswers(answerDTOS);

        assertTrue(actual);
        verify(answerDAOJdbc,times(1)).deleteAnswerById(1);
        verify(answerDAOJdbc,times(1)).deleteAnswerById(2);
    }

    @ParameterizedTest
    @MethodSource(value = "provideExpectedBoolean")
    void updateVoteShouldReturnTrue(boolean expected) {
        VoteDTO dto = new VoteDTO("vote",1,1,1);

        when(answerDAOJdbc.changeVote(dto.vote(),dto.id())).thenReturn(expected);

        boolean actual = answerService.updateVote(dto);

        assertEquals(expected, actual);
        verify(answerDAOJdbc,times(1)).changeVote(dto.vote(),dto.id());
    }

    @ParameterizedTest
    @MethodSource(value = "provideReturnValueAndExpectedNumber")
    void getNumberOfAnswersByUserIdShouldReturnExpectedNumber(int id , int expectedNumber) {
        when(answerDAOJdbc.getNumberOfUserAnswers(id)).thenReturn(expectedNumber);

        int actual = answerService.getNumberOfAnswersByUserId(id);

        assertEquals(expectedNumber,actual);
        verify(answerDAOJdbc,times(1)).getNumberOfUserAnswers(id);
    }

    private void assertAnswerEquals(Answer answer, AnswerDTO answerDTO) {
        assertEquals(answer.answer_id(), answerDTO.answer_id());
        assertEquals(answer.question_id(), answerDTO.question_id());
        assertEquals(answer.description(), answerDTO.description());
    }
    private static Stream<Arguments> provideExpectedBoolean() {
        return Stream.of(
                Arguments.of(true),
                Arguments.of(false)
        );
    }
    private static Stream<Arguments> provideReturnValueAndExpectedNumber() {
        return Stream.of(
                Arguments.of(1,1),
                Arguments.of(2,0)
        );
    }
}


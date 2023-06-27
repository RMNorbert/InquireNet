package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.answer.Answer;
import com.rmnnorbert.InquireNet.dao.model.answer.AnswerDAOJdbc;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.AnswerRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AnswerServiceTest {
    @Mock
    private AnswerDAOJdbc answerDAOJdbc;
    private AnswerService answerService;
    private ReplyService replyService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        answerService = new AnswerService(answerDAOJdbc, replyService);
    }

    @Test
    void getAllAnswersWhenAnswersExist() {
        List<Answer> answers = List.of(
                new Answer(1,1,1,"desc",LocalDateTime.now(),0,"un voted"),
                new Answer(2,1,2,"title", LocalDateTime.now(),0,"un voted")
        );
        when(answerDAOJdbc.getAllAnswers()).thenReturn(answers);

        List<AnswerDTO> answerDTOS = answerService.getAllAnswers();

        assertEquals(answers.size(), answerDTOS.size());
    }
    @Test
    void getAllAnswersWhenNoAnswersExist() {
        when(answerDAOJdbc.getAllAnswers()).thenReturn(new ArrayList<>());

        List<AnswerDTO> answerDTOS = answerService.getAllAnswers();

        assertEquals(0, answerDTOS.size());
    }
    @Test
    void getAnswerById() {
        int id = 1;
        Answer answer = new Answer(1,1,1,"desc",LocalDateTime.now(),0,"un voted");
        when(answerDAOJdbc.findAnswerById(id)).thenReturn(answer);

        AnswerDTO foundAnswer = answerService.getAnswerById(id);

        assertAnswerEquals(answer, foundAnswer);
        verify(answerDAOJdbc, times(1)).findAnswerById(id);
    }

    /*@Test
    void getAllAnswersByQuestionId() {
        int id = 2;
        AnswerRequestDTO answerRequestDTO = new AnswerRequestDTO(1,"User",1);
        AnswerRequestDTO answerRequestDTO2 = new AnswerRequestDTO(1,"User",2);
        answerDAOJdbc.addAnswer(answerRequestDTO);
        answerDAOJdbc.addAnswer(answerRequestDTO2);
        List<Answer> answers = List.of(
        new Answer(1,1,1,"User",LocalDateTime.now(),0,"unvoted"),
        new Answer(2,1,2,"User", LocalDateTime.now(),0,"unvoted")
        );
        List<AnswerDTO> expectedAnswers = List.of(AnswerDTO.of(answers.get(1)));
        when(answerService.getAllAnswersByQuestionId(id)).thenReturn(expectedAnswers);

        List<AnswerDTO> actualAnswers = answerService.getAllAnswersByQuestionId(id);
        assertEquals(expectedAnswers, actualAnswers);
        assertEquals(1, actualAnswers.size());
    }

    @Test
    void deleteAnswerById() {
        int id = 1;
        Answer answer = new Answer(1,1,1,"desc",LocalDateTime.now(),0,"un voted");
        when(answerDAOJdbc.deleteAnswerById(id)).thenReturn(true);

        boolean response = answerService.deleteAnswerById(new DeleteRequestDTO(1,1));

        assertTrue(response);
        verify(answerDAOJdbc, times(1)).deleteAnswerById(answer.question_id());
    }
*/
    @Test
    void addNewAnswer() {
        AnswerRequestDTO answerRequestDTO = new AnswerRequestDTO(1,"User",1);
        boolean modifiedRows = true;
        when(answerDAOJdbc.addAnswer(answerRequestDTO)).thenReturn(modifiedRows);

        boolean response = answerService.addNewAnswer(answerRequestDTO);

        assertEquals(modifiedRows,response);
        verify(answerDAOJdbc, times(1)).addAnswer(answerRequestDTO);
    }
    private void assertAnswerEquals(Answer answer, AnswerDTO answerDTO) {
        assertEquals(answer.answer_id(), answerDTO.answer_id());
        assertEquals(answer.question_id(), answerDTO.question_id());
        assertEquals(answer.description(), answerDTO.description());
    }
}

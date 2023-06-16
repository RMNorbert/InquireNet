package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.answer.Answer;
import com.rmnnorbert.InquireNet.dao.model.answer.AnswerDAOJdbc;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.NewAnswerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AnswerServiceTest {
    @Mock
    private AnswerDAOJdbc answerDAOJdbc;
    private AnswerService answerService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        answerService = new AnswerService(answerDAOJdbc);
    }

    @Test
    void getAllAnswersWhenAnswersExist() {
        List<Answer> answers = List.of(
                new Answer(1,1,"desc",LocalDateTime.now(),0),
                new Answer(2,1,"title", LocalDateTime.now(),0)
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
        Answer answer = new Answer(1,1,"desc",LocalDateTime.now(),0);
        when(answerDAOJdbc.findAnswerById(id)).thenReturn(Optional.of(answer));

        Optional<AnswerDTO> foundAnswer = answerService.getAnswerById(id);

        assertTrue(foundAnswer.isPresent());
        assertAnswerEquals(answer, foundAnswer.get());
        verify(answerDAOJdbc, times(1)).findAnswerById(id);
    }

    @Test
    void getAllAnswersByQuestionId() {
        int id = 2;
        List<Answer> answers = List.of(
                new Answer(1,1,"desc",LocalDateTime.now(),0),
                new Answer(2,1,"title", LocalDateTime.now(),0)
        );
        when(answerDAOJdbc.getAllAnswersByQuestionId(id)).thenReturn(List.of(answers.get(1)));

        List<AnswerDTO> answerDTOS = answerService.getAllAnswersByQuestionId(id);

        assertEquals(answers.size(), answerDTOS.size());
    }

    @Test
    void deleteAnswerById() {
        int id = 1;
        Answer answer = new Answer(1,1,"desc",LocalDateTime.now(),0);
        when(answerDAOJdbc.deleteAnswerById(id)).thenReturn(true);

        boolean response = answerService.deleteAnswerById(id);

        assertTrue(response);
        verify(answerDAOJdbc, times(1)).deleteAnswerById(answer.getQuestion_id());
    }

    @Test
    void addNewAnswer() {
        NewAnswerDTO newAnswerDTO = new NewAnswerDTO("User",1);
        int modifiedRows = 1;
        when(answerDAOJdbc.addAnswer(newAnswerDTO)).thenReturn(modifiedRows);

        int response = answerService.addNewAnswer(newAnswerDTO);

        assertEquals(modifiedRows,response);
        verify(answerDAOJdbc, times(1)).addAnswer(newAnswerDTO);
    }
    private void assertAnswerEquals(Answer answer, AnswerDTO answerDTO) {
        assertEquals(answer.getAnswer_id(), answerDTO.getAnswer_id());
        assertEquals(answer.getQuestion_id(), answerDTO.getQuestion_id());
        assertEquals(answer.getDescription(), answerDTO.getDescription());
    }
}

package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.question.Question;
import com.rmnnorbert.InquireNet.dao.model.question.QuestionsDaoJdbc;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class QuestionServiceTest {
    @Mock
    private QuestionsDaoJdbc questionsDaoJdbc;
    private QuestionService questionService;
    private AnswerService answerService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        questionService = new QuestionService(questionsDaoJdbc, answerService);
    }

    @Test
    void getAllQuestionsWhenQuestionsExist() {
        List<Question> questions = List.of(
                new Question(1,1,"title","desc", LocalDateTime.now(),1),
                new Question(2,1,"title","desc", LocalDateTime.now(),1)
        );
        when(questionsDaoJdbc.getAllQuestion()).thenReturn(questions);

        List<QuestionDTO> questionDTOS = questionService.getAllQuestions();

        assertEquals(questions.size(), questionDTOS.size());
    }
    @Test
    void getAllQuestionsWhenNoQuestionExist() {
        when(questionsDaoJdbc.getAllQuestion()).thenReturn(new ArrayList<>());

        List<QuestionDTO> questionDTOS = questionService.getAllQuestions();

        assertEquals(0, questionDTOS.size());
    }
    @Test
    void getLastQuestion() {
        List<Question> questions = List.of(
                new Question(1,1,"title","desc", LocalDateTime.now(),1),
                new Question(2,1,"title","desc", LocalDateTime.now(),1)
        );
        when(questionsDaoJdbc.findLastQuestionId()).thenReturn(1L);

        long lastQuestionId = questionService.getLastQuestion();
        long expectedId = 1;
        assertEquals(expectedId,lastQuestionId);

        verify(questionsDaoJdbc, times(1)).findLastQuestionId();
    }

    @Test
    void getQuestionById() {
        int id = 1;
        Question question = new Question(1,1,"title","desc", LocalDateTime.now(),1);
        when(questionsDaoJdbc.findQuestionById(id)).thenReturn(question);

        QuestionDTO foundQuestion = questionService.getQuestionById(id);

        assertQuestionEquals(question, foundQuestion);
        verify(questionsDaoJdbc, times(1)).findQuestionById(id);
    }

    @Test
    void deleteQuestionById() {
        int id = 1;
        Question question = new Question(1,1,"title","desc", LocalDateTime.now(),1);
        when(questionsDaoJdbc.deleteQuestionById(id)).thenReturn(true);

        boolean response = questionService.deleteQuestionById(new DeleteRequestDTO(id,id));

        assertTrue(response);
        verify(questionsDaoJdbc, times(1)).deleteQuestionById(question.question_id());
    }

    @Test
    void addNewQuestion() {
        NewQuestionDTO questionDTO = new NewQuestionDTO("User","aka",1);
        boolean modifiedRows = true;
        when(questionsDaoJdbc.addQuestion(questionDTO)).thenReturn(true);

        boolean response = questionService.addNewQuestion(questionDTO);

        assertEquals(modifiedRows,response);
        verify(questionsDaoJdbc, times(1)).addQuestion(questionDTO);
    }
    private void assertQuestionEquals(Question question, QuestionDTO questionDTO) {
        assertEquals(question.question_id(), questionDTO.question_id());
        assertEquals(question.user_id(), questionDTO.user_id());
        assertEquals(question.title(), questionDTO.title());
    }
}

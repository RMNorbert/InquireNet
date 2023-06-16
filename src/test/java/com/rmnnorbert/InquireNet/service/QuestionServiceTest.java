package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.question.Question;
import com.rmnnorbert.InquireNet.dao.model.question.QuestionsDaoJdbc;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
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

class QuestionServiceTest {
    @Mock
    private QuestionsDaoJdbc questionsDaoJdbc;
    private QuestionService questionService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        questionService = new QuestionService(questionsDaoJdbc);
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
        when(questionsDaoJdbc.findLastQuestion()).thenReturn(Optional.of(questions.get(1)));

        Optional<QuestionDTO> questionDTOS = questionService.getLastQuestion();
        assertTrue(questionDTOS.isPresent());

        assertQuestionEquals(questions.get(1), questionDTOS.get());
        verify(questionsDaoJdbc, times(1)).findLastQuestion();
    }

    @Test
    void getQuestionById() {
        int id = 1;
        Question question = new Question(1,1,"title","desc", LocalDateTime.now(),1);
        when(questionsDaoJdbc.findQuestionById(id)).thenReturn(Optional.of(question));

        Optional<QuestionDTO> foundQuestion = questionService.getQuestionById(id);

        assertTrue(foundQuestion.isPresent());
        assertQuestionEquals(question, foundQuestion.get());
        verify(questionsDaoJdbc, times(1)).findQuestionById(id);
    }

    @Test
    void deleteQuestionById() {
        int id = 1;
        Question question = new Question(1,1,"title","desc", LocalDateTime.now(),1);
        when(questionsDaoJdbc.deleteQuestionById(id)).thenReturn(true);

        boolean response = questionService.deleteQuestionById(id);

        assertTrue(response);
        verify(questionsDaoJdbc, times(1)).deleteQuestionById(question.getQuestion_id());
    }

    @Test
    void addNewQuestion() {
        NewQuestionDTO questionDTO = new NewQuestionDTO("User","aka",1);
        int modifiedRows = 1;
        when(questionsDaoJdbc.addQuestion(questionDTO)).thenReturn(modifiedRows);

        int response = questionService.addNewQuestion(questionDTO);

        assertEquals(modifiedRows,response);
        verify(questionsDaoJdbc, times(1)).addQuestion(questionDTO);
    }
    private void assertQuestionEquals(Question question, QuestionDTO questionDTO) {
        assertEquals(question.getQuestion_id(), questionDTO.getQuestion_id());
        assertEquals(question.getUserID(), questionDTO.getUser_id());
        assertEquals(question.getTitle(), questionDTO.getTitle());
    }
}

package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.model.question.Question;
import com.rmnnorbert.InquireNet.dao.model.question.QuestionsDaoJdbc;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
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

class QuestionServiceTest {
    @Mock
    private QuestionsDaoJdbc questionsDaoJdbc;
    @Mock
    private AnswerService answerService;
    private QuestionService questionService;

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

        List<QuestionDTO> expected = questions.stream()
                                              .map(QuestionDTO::of)
                                              .toList();
        List<QuestionDTO> actual = questionService.getAllQuestions();

        assertEquals(expected, actual);
    }
    @Test
    void getAllQuestionsWhenNoQuestionExist() {
        when(questionsDaoJdbc.getAllQuestion()).thenReturn(new ArrayList<>());

        List<QuestionDTO> questionDTOS = questionService.getAllQuestions();

        assertEquals(0, questionDTOS.size());
    }

    @Test
    void getAllQuestionsOfUserWhenTheUserHaveQuestion() {
        long userId = 1;
        List<Question> questions = List.of(
                new Question(1,1,"title","desc", LocalDateTime.now(),1),
                new Question(2,1,"title","desc", LocalDateTime.now(),1)
        );
        when(questionsDaoJdbc.getAllQuestionByUserID(userId)).thenReturn(questions);

        List<QuestionDTO> expected = questions.stream()
                                              .map(QuestionDTO::of)
                                              .toList();
        List<QuestionDTO> actual = questionService.getAllQuestionOfUser(userId);

        assertEquals(expected, actual);
    }

    @Test
    void getAllQuestionsOfUserWhenTheUserDoNotHaveAnyQuestion() {
        long userId = 1;
        when(questionsDaoJdbc.getAllQuestionByUserID(userId)).thenReturn(new ArrayList<>());

        List<QuestionDTO> questionDTOS = questionService.getAllQuestionOfUser(userId);

        assertEquals(0, questionDTOS.size());
    }

    @Test
    void getLastQuestion() {
        when(questionsDaoJdbc.findLastQuestionId()).thenReturn(1L);

        long lastQuestionId = questionService.getLastQuestion();
        long expectedId = 1;
        assertEquals(expectedId,lastQuestionId);

        verify(questionsDaoJdbc, times(1)).findLastQuestionId();
    }
    @Test
    void getLastQuestionWhenThereIsNoQuestion() {
        long noQuestionResponse = 0;
        when(questionsDaoJdbc.findLastQuestionId()).thenReturn(noQuestionResponse);

        long lastQuestionId = questionService.getLastQuestion();
        long expectedId = 0;
        assertEquals(expectedId,lastQuestionId);

        verify(questionsDaoJdbc, times(1)).findLastQuestionId();
    }
    @Test
    void getQuestionById() {
        long id = 1;
        Question question = new Question(1,1,"title","desc", LocalDateTime.now(),1);
        when(questionsDaoJdbc.findQuestionById(id)).thenReturn(question);

        QuestionDTO expected = QuestionDTO.of(question);
        QuestionDTO actual = questionService.getQuestionById(id);

        assertEquals(expected, actual);
        verify(questionsDaoJdbc, times(1)).findQuestionById(id);
    }
    @Test
    void getQuestionByIdWithWrongId() {
        long id = 1;
        when(questionsDaoJdbc.findQuestionById(id)).thenThrow(new NotFoundException("Question"));

        assertThrows(NotFoundException.class, () -> questionService.getQuestionById(id));

        verify(questionsDaoJdbc, times(1)).findQuestionById(id);
    }

    @Test
    void deleteQuestionById() {
        long id = 1;
        Question question = new Question(1, 1, "title", "desc", LocalDateTime.now(), 1);
        when(questionsDaoJdbc.findQuestionById(id)).thenReturn(question);
        when(questionsDaoJdbc.deleteQuestionById(id)).thenReturn(true);

        boolean response = questionService.deleteQuestionById(new DeleteRequestDTO(id, id));

        assertTrue(response);
        verify(questionsDaoJdbc, times(1)).findQuestionById(question.question_id());
        verify(questionsDaoJdbc, times(1)).deleteQuestionById(question.question_id());
    }
    @Test
    void deleteQuestionByIdWithWrongQuestionId() {
        long id = 1;
        when(questionsDaoJdbc.findQuestionById(id)).thenThrow(new NotFoundException("Question"));
        when(questionsDaoJdbc.deleteQuestionById(id)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> questionService.getQuestionById(id));

        verify(questionsDaoJdbc, times(1)).findQuestionById(id);
        verify(questionsDaoJdbc, times(0)).deleteQuestionById(id);
    }
    @Test
    void deleteQuestionByIdWithWrongUserId() {
        DeleteRequestDTO dto = new DeleteRequestDTO(1L, 1L);
        Question question = new Question(1L, 2L, "title", "desc", LocalDateTime.now(), 1);
        when(questionsDaoJdbc.findQuestionById(dto.targetId())).thenReturn(question);

        boolean response = questionService.deleteQuestionById(dto);

        assertFalse(response);
        verify(questionsDaoJdbc, times(1)).findQuestionById(dto.targetId());
        verify(questionsDaoJdbc, times(0)).deleteQuestionById(dto.targetId());
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
    @Test
    void updateQuestion() {
        Question question = new Question(1,1,"title","des",LocalDateTime.now(),0);
        UpdateDTO dto = new UpdateDTO(1,1,"title","desc");
        when(questionsDaoJdbc.findQuestionById(dto.id())).thenReturn(question);
        when(questionsDaoJdbc.update(dto)).thenReturn(true);

        boolean actual = questionService.updateQuestion(dto);

        assertTrue(actual);
        verify(questionsDaoJdbc, times(1)).findQuestionById(dto.id());
        verify(questionsDaoJdbc, times(1)).update(dto);
    }
    @Test
    void updateQuestionWithWrongQuestionId() {
        UpdateDTO dto = new UpdateDTO(1,1,"title","desc");
        when(questionsDaoJdbc.findQuestionById(dto.id())).thenThrow(new NotFoundException("Question"));

        assertThrows(NotFoundException.class, () -> questionService.updateQuestion(dto));

        verify(questionsDaoJdbc, times(1)).findQuestionById(dto.id());
        verify(questionsDaoJdbc, times(0)).update(dto);
    }
    @Test
    void updateQuestionWithWrongUserId() {
        Question question = new Question(1,2,"title","des",LocalDateTime.now(),0);
        UpdateDTO dto = new UpdateDTO(1,1,"title","desc");
        when(questionsDaoJdbc.findQuestionById(dto.id())).thenReturn(question);

        boolean actual = questionService.updateQuestion(dto);

        assertFalse(actual);
        verify(questionsDaoJdbc, times(1)).findQuestionById(dto.id());
        verify(questionsDaoJdbc, times(0)).update(dto);
    }
}

package com.rmnnorbert.InquireNet.controller;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import com.rmnnorbert.InquireNet.service.QuestionService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@UnitTest
@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {
    private QuestionController questionController;
    @Mock
    private QuestionService service;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        this.questionController = new QuestionController(service);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedList")
    void getAllQuestionsShouldReturnQuestionDTOList(List<QuestionDTO> expected) {
        when(service.getAllQuestions()).thenReturn(expected);

        List<QuestionDTO> actual = questionController.getAllQuestions();
        assertEquals(expected, actual);
        verify(service,times(1)).getAllQuestions();
    }

    @ParameterizedTest
    @MethodSource("provideIdAndExpectedList")
    void getAllQuestionsOfUserShouldReturnExpectedQuestionDTOList(long searchedId, List<QuestionDTO> expected) {
        when(service.getAllQuestionOfUser(searchedId)).thenReturn(expected);

        List<QuestionDTO> actual = questionController.getAllQuestionsOfUser(searchedId);
        assertEquals(expected, actual);
        verify(service,times(1)).getAllQuestionOfUser(searchedId);
    }

    @Test
    void getLastQuestionsShouldReturnExpectedValue() {
        long expected = 1;

        when(service.getLastQuestion()).thenReturn(expected);

        long actual = questionController.getLastQuestion();

        assertEquals(expected, actual);
        verify(service,times(1)).getLastQuestion();
    }

    @Test
    void getQuestionByIdShouldReturnQuestionDTO() {
        long searchedId = 1;
        QuestionDTO dto = new QuestionDTO(1,
                                             1,
                                         "title",
                                     "desc",
                                  LocalDateTime.now(),
                                    0);

        when(service.getQuestionById(searchedId)).thenReturn(dto);

        QuestionDTO actual = questionController.getQuestionById(searchedId);
        assertEquals(dto, actual);
        verify(service,times(1)).getQuestionById(searchedId);
    }
    @Test
    void getQuestionByIdShouldReturnOkStatusAndNotFoundException() {
        long searchedId = 1L;

        when(service.getQuestionById(searchedId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> questionController.getQuestionById(searchedId));
        verify(service,times(1)).getQuestionById(searchedId);
    }
    @ParameterizedTest
    @MethodSource("provideExpectedValue")
    void addNewQuestion(boolean expected) {
        NewQuestionDTO dto = new NewQuestionDTO("title","desc",1);

        when(service.addNewQuestion(dto)).thenReturn(expected);

        boolean actual = questionController.addNewQuestion(dto);

        assertEquals(expected, actual);
        verify(service,times(1)).addNewQuestion(dto);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValueAndUpdateValue")
    void updateQuestion(boolean expected, UpdateDTO dto) {
        when(service.updateQuestion(dto)).thenReturn(expected);

        boolean actual = questionController.updateQuestion(dto);

        assertEquals(expected, actual);
        verify(service,times(1)).updateQuestion(dto);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValueAndDeleteValue")
    void deleteQuestionById(boolean expected, DeleteRequestDTO dto) {
        when(service.deleteQuestionById(dto)).thenReturn(expected);

        boolean actual = questionController.deleteQuestionById(dto);

        assertEquals(expected, actual);
        verify(service,times(1)).deleteQuestionById(dto);
    }

    private static Stream<Arguments> provideExpectedList() {
        return Stream.of(
          Arguments.of(List.of(
                  new QuestionDTO(1,1,"title","desc", LocalDateTime.now(),0),
                  new QuestionDTO(2,2,"titles","description", LocalDateTime.now(),0)
          )),
          Arguments.of(List.of())
        );
    }
    private static Stream<Arguments> provideIdAndExpectedList() {
        return Stream.of(
          Arguments.of(1,List.of(
                  new QuestionDTO(1,1,"title","desc", LocalDateTime.now(),0),
                  new QuestionDTO(2,2,"titles","description", LocalDateTime.now(),0)
          )),
          Arguments.of(2,List.of())
        );
    }
    private static Stream<Arguments> provideExpectedValue() {
        return Stream.of(
                Arguments.of(true),
                Arguments.of(false)
        );
    }
    private static Stream<Arguments> provideExpectedValueAndUpdateValue() {
        return Stream.of(
                Arguments.of(true, new UpdateDTO(1,1,"Title","Desc")),
                Arguments.of(false, new UpdateDTO(2,1,"Title","Desc"))
        );
    }
    private static Stream<Arguments> provideExpectedValueAndDeleteValue() {
        return Stream.of(
                Arguments.of(true, new DeleteRequestDTO(1,1)),
                Arguments.of(false, new DeleteRequestDTO(1,2))
        );
    }
}

package com.rmnnorbert.InquireNet.dao.model;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.QuestionRowMapper;
import com.rmnnorbert.InquireNet.dao.model.question.Question;
import com.rmnnorbert.InquireNet.dao.model.question.QuestionsDaoJdbc;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@UnitTest
@RunWith(MockitoJUnitRunner.class)
class QuestionsDaoJdbcTest {
    private QuestionsDaoJdbc questionsDaoJdbc;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.questionsDaoJdbc = new QuestionsDaoJdbc(jdbcTemplate);
    }

    @ParameterizedTest
    @MethodSource(value = "provideExpectedList")
    void getAllQuestionShouldReturnExpectedList(List<Question> expected) {
        when(jdbcTemplate.query(anyString(),any(QuestionRowMapper.class))).thenReturn(expected);

        List<Question> actual = questionsDaoJdbc.getAllQuestion();

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).query(anyString(),any(QuestionRowMapper.class));
    }

    @Test
    void findQuestionByIdShouldReturnExpectedQuestion() {
        long searchedId = 1;
        List<Question> foundQuestion = List.of(new Question(1,1,"title","desc", LocalDateTime.now(),1));

        when(jdbcTemplate.query(anyString(),any(QuestionRowMapper.class),eq(searchedId))).thenReturn(foundQuestion);

        Question actual = questionsDaoJdbc.findQuestionById(searchedId);
        Question expected = foundQuestion.get(0);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).query(anyString(),any(QuestionRowMapper.class),eq(searchedId));
    }
    @Test
    void findQuestionByIdShouldReturnNotFoundException() {
        long searchedId = 1;

        when(jdbcTemplate.query(anyString(),any(QuestionRowMapper.class),eq(searchedId))).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> questionsDaoJdbc.findQuestionById(searchedId));
        verify(jdbcTemplate,times(1)).query(anyString(),any(QuestionRowMapper.class),eq(searchedId));
    }

    @ParameterizedTest
    @MethodSource(value = "provideExpectedList")
    void getAllQuestionByUserIDShouldReturnExpectedList(List<Question> expected) {
        long id = 1;
        when(jdbcTemplate.query(anyString(), any(QuestionRowMapper.class), eq(id))).thenReturn(expected);

        List<Question> actual = questionsDaoJdbc.getAllQuestionByUserID(id);

        assertEquals(expected,actual);
        verify(jdbcTemplate,times(1)).query(anyString(), any(QuestionRowMapper.class), eq(id));
    }

    @ParameterizedTest
    @MethodSource(value = "provideNewQuestionDTOAndExpectedValue")
    void addQuestionShouldReturnExpectedBoolean(NewQuestionDTO dto, boolean expected) {
        int value = 1;
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any())).thenReturn(value);

        boolean actual = questionsDaoJdbc.addQuestion(dto);
        assertEquals(expected,actual);
    }

    @ParameterizedTest
    @MethodSource("provideReturnValueAndExpectedBooleanValue")
    void deleteQuestionByIdShouldReturnExpectedBoolean(int returnValue, boolean expected) {
        long id = 1;

        when(jdbcTemplate.update(anyString(), eq(id))).thenReturn(returnValue);

        boolean actual = questionsDaoJdbc.deleteQuestionById(id);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).update(anyString(), eq(id));
    }

    @ParameterizedTest
    @MethodSource("provideReturnValueAndExpectedBooleanValue")
    void updateShouldReturnExpectedBoolean(int returnValue, boolean expected) {
        UpdateDTO dto = new UpdateDTO(1,1,"title","desc");

        when(jdbcTemplate.update(anyString(), eq(dto.title()), eq(dto.description()))).thenReturn(returnValue);

        boolean actual = questionsDaoJdbc.update(dto);

        assertEquals(expected,actual);
        verify(jdbcTemplate,times(1)).update(anyString(), eq(dto.title()), eq(dto.description()));
    }

    @ParameterizedTest
    @MethodSource(value = "provideReturnValueAndExpectedIntValue")
    void findLastQuestionIdShouldReturnExpectedNumber(Long returnValue, int expected) {
        when(jdbcTemplate.queryForObject(anyString(),eq(Long.class))).thenReturn(returnValue);

        long actual = questionsDaoJdbc.findLastQuestionId();

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).queryForObject(anyString(),eq(Long.class));
    }
    private static Stream<Arguments> provideExpectedList() {
        return Stream.of(
                Arguments.of(List.of(new Question(1,1,"title","desc", LocalDateTime.now(),1))),
                Arguments.of(List.of())
        );
    }
    private static Stream<Arguments> provideNewQuestionDTOAndExpectedValue() {
        return Stream.of(
                Arguments.of(new NewQuestionDTO("title", "desc", 1), true),
                Arguments.of(new NewQuestionDTO("title", "desc", 0), false)
        );
    }
    private static Stream<Arguments> provideReturnValueAndExpectedBooleanValue() {
        return Stream.of(
                Arguments.of(1,true),
                Arguments.of(0,false)
        );
    }
    private static Stream<Arguments> provideReturnValueAndExpectedIntValue() {
        return Stream.of(
                Arguments.of(1L,1),
                Arguments.of(null,0)
        );
    }
}

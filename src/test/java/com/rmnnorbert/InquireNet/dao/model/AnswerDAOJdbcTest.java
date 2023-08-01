package com.rmnnorbert.InquireNet.dao.model;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.AnswerRowMapper;
import com.rmnnorbert.InquireNet.dao.model.answer.Answer;
import com.rmnnorbert.InquireNet.dao.model.answer.AnswerDAOJdbc;
import com.rmnnorbert.InquireNet.dto.answer.AnswerRequestDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AnswerDAOJdbcTest {
    private AnswerDAOJdbc answerDAOJdbc;
    @Mock
    private JdbcTemplate jdbcTemplate;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.answerDAOJdbc = new AnswerDAOJdbc(jdbcTemplate);
    }

    @Test
    void getAllAnswersShouldReturnExpectedAnswerList() {
        List<Answer> expected = List.of(new Answer(1,1,1,"desc",LocalDateTime.now(),1,"voted"));

        when(jdbcTemplate.query(anyString(), any(AnswerRowMapper.class))).thenReturn(expected);

        List<Answer> actual = answerDAOJdbc.getAllAnswers();

        assertThat(actual).extracting(Answer::description).containsOnly("desc");
        verify(jdbcTemplate,times(1)).query(anyString(), any(AnswerRowMapper.class));
    }
    @Test
    void getAllAnswersShouldReturnEmptyList() {
        int expectedSize = 0;
        List<Answer> expectedList = List.of();

        when(jdbcTemplate.query(anyString(), any(AnswerRowMapper.class))).thenReturn(expectedList);

        List<Answer> actual = answerDAOJdbc.getAllAnswers();

        assertEquals(expectedSize,actual.size());
        verify(jdbcTemplate,times(1)).query(anyString(), any(AnswerRowMapper.class));
    }

    @Test
    void findAnswerByIdShouldReturnExpectedAnswer() {
        long searchedId = 1;
        Answer expected = new Answer(1,1,1,"desc",LocalDateTime.now(),1,"voted");

        when(jdbcTemplate.query(anyString(), any(AnswerRowMapper.class), eq(searchedId))).thenReturn(List.of(expected));

        Answer actual = answerDAOJdbc.findAnswerById(searchedId);

        assertEquals(expected,actual);
        verify(jdbcTemplate,times(1)).query(anyString(), any(AnswerRowMapper.class), eq(searchedId));
    }
    @Test
    void findAnswerByIdShouldReturnNotFoundException() {
        long searchedId = 1;

        when(jdbcTemplate.query(anyString(), any(AnswerRowMapper.class), eq(searchedId))).thenReturn(List.of());

        assertThrows(NotFoundException.class,() -> answerDAOJdbc.findAnswerById(searchedId));
        verify(jdbcTemplate,times(1)).query(anyString(), any(AnswerRowMapper.class), eq(searchedId));
    }

    @Test
    void getAllAnswersByQuestionIdShouldReturnExpectedAnswerList() {
        long searchedId = 1;
        Answer expected = new Answer(1,1,1,"desc",LocalDateTime.now(),1,"voted");

        when(jdbcTemplate.query(anyString(), any(AnswerRowMapper.class), eq(searchedId))).thenReturn(List.of(expected));

        List<Answer> actual = answerDAOJdbc.getAllAnswersByQuestionId(searchedId);

        assertThat(actual).extracting(Answer::description).containsOnly("desc");
        verify(jdbcTemplate,times(1)).query(anyString(), any(AnswerRowMapper.class), eq(searchedId));
    }
    @Test
    void getAllAnswersByQuestionIdShouldReturnEmptyList() {
        long searchedId = 1;
        int expectedSize = 0;

        when(jdbcTemplate.query(anyString(), any(AnswerRowMapper.class), eq(searchedId))).thenReturn(List.of());

        List<Answer> actual = answerDAOJdbc.getAllAnswersByQuestionId(searchedId);

        assertEquals(expectedSize,actual.size());
        verify(jdbcTemplate,times(1)).query(anyString(), any(AnswerRowMapper.class), eq(searchedId));
    }
    @Test
    void addAnswerShouldReturnFalse() {
        int value = 0;
        boolean expected = false;
        AnswerRequestDTO dto = new AnswerRequestDTO(1,"desc",1);
        LocalDateTime dateTime = LocalDateTime.of(2000,10,10,10,10);

        when(jdbcTemplate.update(anyString(),
                eq(dto.userId()),
                eq(dto.description()),
                eq(dateTime),
                eq(dto.id()),
                eq("unvoted"))).thenReturn(value);

        boolean actual = answerDAOJdbc.addAnswer(dto);

        assertEquals(expected,actual);
    }
    @ParameterizedTest
    @MethodSource("provideAffectedRowsAndExpectedValue")
    void deleteAnswerByIdShouldReturnExpectedValue(int value, boolean expected) {
        long id = 1;

        when(jdbcTemplate.update(anyString(),
                eq(id))).thenReturn(value);

        boolean actual = answerDAOJdbc.deleteAnswerById(id);

        assertEquals(expected,actual);
        verify(jdbcTemplate,times(1)).update(anyString(), eq(id));
    }

    @ParameterizedTest
    @MethodSource("provideAnswerIdAndUpdateDTOAndExpectedValue")
    void updateShouldReturnExpectedValue(int id, UpdateDTO dto, boolean expected) {
        when(jdbcTemplate.update(anyString(), eq(dto.description()), eq(dto.id()))).thenReturn(id);

        boolean actual = answerDAOJdbc.update(dto);

        assertEquals(expected,actual);
        verify(jdbcTemplate,times(1)).update(anyString(), eq(dto.description()), eq(dto.id()));
    }

    @ParameterizedTest
    @MethodSource("provideAffectedRowsAndExpectedValue")
    void changeVoteShouldReturnExpectedValue(int value, boolean expected) {
        String vote = "vote";
        long id = 1;

        when(jdbcTemplate.update(anyString(), eq(vote), eq(id))).thenReturn(value);

        boolean actual = answerDAOJdbc.changeVote(vote,id);

        assertEquals(expected,actual);
        verify(jdbcTemplate,times(1)).update(anyString(), eq(vote), eq(id));
    }

    @Test
    void getNumberOfUserAnswersShouldReturnExpectedValue() {
        long userId = 1;
        int expectedNumberOfAnswers = 5;

        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(userId)))
                .thenReturn(expectedNumberOfAnswers);

        int actualNumberOfAnswers = answerDAOJdbc.getNumberOfUserAnswers(userId);

        assertEquals(expectedNumberOfAnswers, actualNumberOfAnswers);
        verify(jdbcTemplate,times(1)).queryForObject(anyString(), eq(Integer.class), eq(userId));
    }
    @Test
    void getNumberOfUserAnswersShouldReturnDefaultValue() {
        long userId = 1;
        int expectedNumberOfAnswers = 0;

        int actualNumberOfAnswers = answerDAOJdbc.getNumberOfUserAnswers(userId);

        assertEquals(expectedNumberOfAnswers, actualNumberOfAnswers);
        verify(jdbcTemplate,times(1)).queryForObject(anyString(), eq(Integer.class), eq(userId));
    }
    private static Stream<Arguments> provideAffectedRowsAndExpectedValue() {
        return Stream.of(
                Arguments.of( 0, false),
                Arguments.of( 1, true)
        );
    }
    private static Stream<Arguments> provideAnswerIdAndUpdateDTOAndExpectedValue() {
        return Stream.of(
                Arguments.of( 1,new UpdateDTO(1,1,"title","desc"), true),
                Arguments.of( 0,new UpdateDTO(1,1,"title","desc"), false)
        );
    }
}

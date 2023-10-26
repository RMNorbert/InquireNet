package com.rmnnorbert.InquireNet.dao.model;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.rowMapper.UserRowMapper;
import com.rmnnorbert.InquireNet.dao.model.user.User;
import com.rmnnorbert.InquireNet.dao.model.user.UserDaoJdbc;
import com.rmnnorbert.InquireNet.dao.model.user.data.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@UnitTest
@RunWith(MockitoJUnitRunner.class)
class UserDaoJdbcTest {
    private UserDaoJdbc userDaoJdbc;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userDaoJdbc = new UserDaoJdbc(jdbcTemplate);
    }

    @ParameterizedTest
    @MethodSource(value = "provideExpectedUserList")
    void getAllUserShouldReturnExpectedUserList(List<User> expected) {
        when(jdbcTemplate.query(anyString(), any(UserRowMapper.class))).thenReturn(expected);

        List<User> actual = userDaoJdbc.getAllUser();

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).query(anyString(), any(UserRowMapper.class));
    }

    @Test
    void findUserByIdShouldReturnExpectedUser() {
        long searchedId = 1;
        List<User> searchedUser = List.of(new User(1,Role.USER,"username", "password", LocalDateTime.now()));

        when(jdbcTemplate.query(anyString(), any(UserRowMapper.class), eq(searchedId))).thenReturn(searchedUser);

        User expected = searchedUser.get(0);
        User actual = userDaoJdbc.findUserById(searchedId);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).query(anyString(), any(UserRowMapper.class), eq(searchedId));
    }
    @Test
    void findUserByIdShouldReturnNotFoundException() {
        long searchedId = 1;

        when(jdbcTemplate.query(anyString(),any(UserRowMapper.class),eq(searchedId))).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userDaoJdbc.findUserById(searchedId));
        verify(jdbcTemplate,times(1)).query(anyString(), any(UserRowMapper.class), eq(searchedId));
    }

    @Test
    void findUserByUsernameShouldReturnExpectedUser() {
        String searchedUser = "features/user";
        List<User> searchedUserByUsername = List.of(new User(1,Role.USER,"username", "password", LocalDateTime.now()));

        when(jdbcTemplate.query(anyString(),any(UserRowMapper.class),eq(searchedUser))).thenReturn(searchedUserByUsername);

        User expected = searchedUserByUsername.get(0);
        User actual = userDaoJdbc.findUser(searchedUser);

        assertEquals(expected, actual);
        verify(jdbcTemplate,times(1)).query(anyString(), any(UserRowMapper.class), eq(searchedUser));
    }
    @Test
    void findUserByUsernameShouldReturnNotFoundException() {
        String searchedUser = "features/user";

        when(jdbcTemplate.query(anyString(),any(UserRowMapper.class),eq(searchedUser))).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userDaoJdbc.findUser(searchedUser));
        verify(jdbcTemplate,times(1)).query(anyString(), any(UserRowMapper.class), eq(searchedUser));
    }
    @Test
    void addUserShouldReturnOkStatusAndExpectedMessage() throws SQLException {
        String registrationUsername = "features/user";
        String registrationPassword = "password";
        int returnValue = 1;

        ArgumentCaptor<PreparedStatementCreator> preparedStatementCreatorCaptor = ArgumentCaptor.forClass(PreparedStatementCreator.class);
        ArgumentCaptor<KeyHolder> keyHolderCaptor = ArgumentCaptor.forClass(KeyHolder.class);

        when(jdbcTemplate.update(preparedStatementCreatorCaptor.capture(), keyHolderCaptor.capture())).thenReturn(returnValue);


        ResponseEntity<String> expected = ResponseEntity.ok().body("Created successfully");
        ResponseEntity<String> actual = userDaoJdbc.addUser(registrationUsername,registrationPassword);

        assertEquals(expected, actual);

        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorCaptor.getValue();

        // ArgumentCaptor capture the values set on the PreparedStatement
        PreparedStatement psMock = mock(PreparedStatement.class);
        Connection connectionMock = mock(Connection.class);
        when(psMock.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(psMock);
        // Create the PreparedStatement using the captured PreparedStatementCreator
        PreparedStatement ps = preparedStatementCreator.createPreparedStatement(connectionMock);

        // Verify that the appropriate setters were called on the PreparedStatement
        verify(ps, times(1)).setString(1, registrationUsername);
        verify(ps, times(1)).setString(2, registrationPassword);
        verify(ps, times(1)).setString(4, "USER");

        // Get the captured KeyHolder
        KeyHolder keyHolder = keyHolderCaptor.getValue();
        List<Map<String, Object>> keyList = keyHolder.getKeyList();

        assertNotNull(keyList);
        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }
    @Test
    void addUserShouldReturnDataIntegrityViolationException() {
        String registrationUsername = "features/user";
        String registrationPassword = "password";

        ArgumentCaptor<PreparedStatementCreator> preparedStatementCreatorCaptor = ArgumentCaptor.forClass(PreparedStatementCreator.class);
        ArgumentCaptor<KeyHolder> keyHolderCaptor = ArgumentCaptor.forClass(KeyHolder.class);

        when(jdbcTemplate.update(preparedStatementCreatorCaptor.capture(), keyHolderCaptor.capture())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> userDaoJdbc.addUser(registrationUsername,registrationPassword));
        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @ParameterizedTest
    @MethodSource(value = "provideUserIdAndExpectedBoolean")
    void deleteUserByIdShouldReturnExpectedBoolean(long id, int returnValue, boolean expected) {
        when(jdbcTemplate.update(anyString(),eq(id))).thenReturn(returnValue);

        boolean actual = userDaoJdbc.deleteUserById(id);

        assertEquals(expected, actual);
        verify(jdbcTemplate, times(1)).update(anyString(),eq(id));
    }

    private static Stream<Arguments> provideExpectedUserList() {
        return Stream.of(
          Arguments.of(List.of(new User(1, Role.USER,"name","password", LocalDateTime.now()))),
          Arguments.of(List.of())
        );
    }
    private static Stream<Arguments> provideUserIdAndExpectedBoolean() {
        return Stream.of(
          Arguments.of(1,1,true),
          Arguments.of(1,0, false)
        );
    }
}

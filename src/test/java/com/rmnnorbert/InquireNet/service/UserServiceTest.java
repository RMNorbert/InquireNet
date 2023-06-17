package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.user.User;
import com.rmnnorbert.InquireNet.dao.model.user.UserDaoJdbc;
import com.rmnnorbert.InquireNet.dto.user.NewUserDTO;
import com.rmnnorbert.InquireNet.dto.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserDaoJdbc userDAO;
    private UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userDAO);
    }

    @Test
    void getAllUserReturnsAllUsersWhenUsersExist() {
        List<User> users = List.of(
                new User(1, "User", "aka", "aka", LocalDateTime.now(), 0, 0),
                new User(2, "User", "baka", "baka", LocalDateTime.now(), 0, 0)
        );
        when(userDAO.getAllUser()).thenReturn(users);

        List<UserDTO> userDTOs = userService.getAllUser();

        assertEquals(users.size(), userDTOs.size());

        IntStream.range(0, users.size())
                .forEach(i -> assertUserEquals(users.get(i), userDTOs.get(i)));
    }
    @Test
    void getAllUserReturnsEmptyListWhenUsersDontExist() {
        when(userDAO.getAllUser()).thenReturn(new ArrayList<>());

        List<UserDTO> userDTOs = userService.getAllUser();

        assertEquals(0, userDTOs.size());
    }
    @Test
    void findUserById() {
        int id = 1;
        User user = new User(1,"User","aka","aka", LocalDateTime.now(),0,0);
        when(userDAO.findUserById(id)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findUserById(id);

        assertTrue(foundUser.isPresent());
        assertUserEquals(user, foundUser.get());
        verify(userDAO, times(1)).findUserById(id);
    }

    @Test
    void logInUser() {
        List<User> users = List.of(new User(1, "User", "aka", "aka", LocalDateTime.now(), 0, 0));
        NewUserDTO user = new NewUserDTO("User", "aka");
        when(userDAO.findUser(user)).thenReturn(Optional.of(users.get(0)));

        Optional<User> loggedInUser = userService.logInUser(user);

        assertTrue(loggedInUser.isPresent());
    }

    @Test
    void deleteUserByIdWhenUserExist() {
        int id = 1;
        User user = new User(1,"User","aka","aka", LocalDateTime.now(),0,0);
        when(userDAO.deleteUserById(id)).thenReturn(true);

        boolean response = userService.deleteUserById(id);

        assertTrue(response);
        verify(userDAO, times(1)).deleteUserById(user.getId());
    }
    @Test
    void deleteUserByIdWhenUserDontExist() {
        int id = 1;
        when(userDAO.deleteUserById(id)).thenReturn(false);

        boolean response = userService.deleteUserById(id);

        assertFalse(response);
        verify(userDAO, times(1)).deleteUserById(id);
    }
    @Test
    void addUser() {
        NewUserDTO user = new NewUserDTO("User","aka");
        int modifiedRows = 1;
        when(userDAO.addUser(user)).thenReturn(modifiedRows);

        int response = userService.addUser(user);

        assertEquals(modifiedRows,response);
        verify(userDAO, times(1)).addUser(user);
    }
    private void assertUserEquals(User user, UserDTO userDTO) {
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getStatus(), userDTO.getStatus());
    }
    private void assertUserEquals(User user, User userToCheck) {
        assertEquals(user.getId(), userToCheck.getId());
        assertEquals(user.getName(), userToCheck.getName());
        assertEquals(user.getStatus(), userToCheck.getStatus());
    }
}

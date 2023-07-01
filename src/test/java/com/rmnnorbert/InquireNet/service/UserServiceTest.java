package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.model.user.User;
import com.rmnnorbert.InquireNet.dao.model.user.UserDaoJdbc;
import com.rmnnorbert.InquireNet.dao.model.user.data.Role;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserDaoJdbc userDAO;
    @Mock
    private UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userDAO);
    }

    @Test
    void getAllUserReturnsAllUsersWhenUsersExist() {
        List<User> users = List.of(
                new User(1, Role.USER, "aka", "aka", LocalDateTime.now()),
                new User(2, Role.USER, "baka", "baka", LocalDateTime.now())
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
        User user = new User(1,Role.USER,"aka","aka", LocalDateTime.now());
        when(userDAO.findUserById(id)).thenReturn(user);

        UserDTO foundUser = userService.findUserById(id);

        assertUserEquals(user, foundUser);
        verify(userDAO, times(1)).findUserById(id);
    }
    @Test
    void findUserByIdWithWrongId() {
        long wrongId = 2;
        when(userDAO.findUserById(wrongId)).thenThrow(new NotFoundException("User"));

        assertThrows(NotFoundException.class, () -> userService.findUserById(wrongId));

        verify(userDAO, times(1)).findUserById(wrongId);
    }

    @Test
    void deleteUserByIdWhenUserExist() {
        DeleteRequestDTO id = new DeleteRequestDTO(1,1);
        User user = new User(1,Role.USER,"aka","aka", LocalDateTime.now());
        when(userDAO.findUserById(1)).thenReturn(user);
        when(userDAO.deleteUserById(1)).thenReturn(true);

        boolean response = userService.deleteUserById(id);

        assertTrue(response);
        verify(userDAO, times(1)).findUserById(user.getId());
        verify(userDAO, times(1)).deleteUserById(user.getId());
    }
    @Test
    void deleteUserByIdWhenUserDontExist() {
        DeleteRequestDTO id = new DeleteRequestDTO(1, 1);
        when(userDAO.findUserById(1)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userService.deleteUserById(id));

        verify(userDAO, times(1)).findUserById(id.targetId());
        verify(userDAO, times(0)).deleteUserById(id.targetId());
    }

    private void assertUserEquals(User user, UserDTO userDTO) {
        assertEquals(user.getId(), userDTO.id());
        assertEquals(user.getUsername(), userDTO.username());
        assertEquals(user.getRole(), userDTO.role());
    }
}

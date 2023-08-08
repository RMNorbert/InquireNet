package com.rmnnorbert.InquireNet.controller;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.model.user.data.Role;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.user.UserDTO;
import com.rmnnorbert.InquireNet.service.UserService;
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
class UserControllerTest {
    private UserController userController;
    @Mock
    private UserService userService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        this.userController = new UserController(userService);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedListValue")
    void getAllUsersShouldReturnExpectedValue(List<UserDTO> expected) {
        when(userService.getAllUser()).thenReturn(expected);

        List<UserDTO> actual = userController.getAllUsers();

        assertEquals(expected, actual);
        verify(userService,times(1)).getAllUser();
    }

    @Test
    void getUserByIdShouldReturnExpectedValue() {
        long searchedId = 1;
        UserDTO expected = new UserDTO(1,Role.USER,"username",LocalDateTime.now());

        when(userService.findUserById(1)).thenReturn(expected);

        UserDTO actual = userController.getUserById(searchedId);

        assertEquals(expected, actual);
        verify(userService,times(1)).findUserById(searchedId);
    }
    @Test
    void getUserByIdShouldReturnNotFoundException() {
           long searchedId = 1;

           when(userService.findUserById(searchedId)).thenThrow(NotFoundException.class);

           assertThrows(NotFoundException.class, () -> userController.getUserById(searchedId));
           verify(userService,times(1)).findUserById(searchedId);
    }
    @ParameterizedTest
    @MethodSource("provideExpectedValue")
    void deleteUserByIdShouldReturnExpectedValue(DeleteRequestDTO value , boolean expected) {
        when(userService.deleteUserById(value)).thenReturn(expected);

        boolean actual = userController.deleteUserById(value);
        assertEquals(expected, actual);
        verify(userService,times(1)).deleteUserById(value);
    }
    @Test
    void deleteUserByIdShouldReturnNotFoundException() {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);

        when(userService.deleteUserById(dto)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userController.deleteUserById(dto));
        verify(userService,times(1)).deleteUserById(dto);
    }
    private static Stream<Arguments> provideExpectedListValue() {
        return Stream.of(
                Arguments.of( List.of(
                        new UserDTO(1, Role.USER,"username", LocalDateTime.now()),
                        new UserDTO(2, Role.USER,"user", LocalDateTime.now())
                )),
                Arguments.of( List.of())
        );
    }
    private static Stream<Arguments> provideExpectedValue() {
        return Stream.of(
                Arguments.of(new DeleteRequestDTO(1,1),true),
                Arguments.of(new DeleteRequestDTO(1,2),false)
        );
    }
}

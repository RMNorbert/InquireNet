package com.rmnnorbert.InquireNet.integration;

import annotations.IntegrationTest;
import com.rmnnorbert.InquireNet.controller.UserController;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.user.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@IntegrationTest
@Sql({ "/clear.sql", "/testInit.sql" })
class UserControllerTest {
    @Autowired
    private UserController userController;
    private final static long SEARCHED_ID = 1;
    @Test
    void getAllUsersShouldReturnExpectedUserDTOList() {
        String expectedUserName1 = "username";
        String expectedUserName2 = "username2";

        assertThat(userController.getAllUsers())
                .extracting(UserDTO::username)
                .containsExactly(
                        expectedUserName1,
                        expectedUserName2
                );
    }

    @Test
    void getUserByIdShouldReturnExpectedUser() {
        String expectedUserName1 = "username";

        assertThat(userController.getUserById(SEARCHED_ID))
                .extracting(UserDTO::username)
                .isEqualTo(expectedUserName1);
    }
    @Test
    void getUserByIdShouldReturnNotFoundException() {
        long id = 10;

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userController.getUserById(id));
    }
    @Test
    void deleteUserByIdShouldReturnTrue() {
        boolean expected = true;

        assertThat(userController.getUserById(SEARCHED_ID))
                .extracting(UserDTO::username)
                .isEqualTo(expected);
    }
    @Test
    void deleteUserByIdShouldReturnNotFoundException() {
        DeleteRequestDTO dto = new DeleteRequestDTO(10,10);

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userController.deleteUserById(dto));
    }
}

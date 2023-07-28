package com.rmnnorbert.InquireNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.model.user.data.Role;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.user.UserDTO;
import com.rmnnorbert.InquireNet.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.rmnnorbert.InquireNet.controller.AnswerControllerTest.MOCK_USERNAME;
import static org.hamcrest.Matchers.emptyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedListValue")
    @WithMockUser(username = MOCK_USERNAME)
    void getAllUsersShouldReturnOkStatusAndExpectedValue(List<UserDTO> expected) throws Exception {
        when(userService.getAllUser()).thenReturn(expected);

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(expected.size())));

        verify(userService,times(1)).getAllUser();
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getUserByIdShouldReturnOkStatusAndExpectedValue() throws Exception {
        long searchedId = 1;
        UserDTO dto = new UserDTO(1,Role.USER,"username",LocalDateTime.now());

        when(userService.findUserById(1)).thenReturn(dto);


        mockMvc.perform(get("/user/" + searchedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.username", Matchers.is(dto.username())));

        verify(userService,times(1)).findUserById(searchedId);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getUserByIdShouldReturnNotFoundStatusAndEmptyBody() throws Exception {
           long searchedId = 1;

           when(userService.findUserById(searchedId)).thenThrow(NotFoundException.class);

           mockMvc.perform(get("/user/" + searchedId))
                   .andExpect(status().isNotFound())
                   .andExpect(content().string(emptyString()));

           verify(userService,times(1)).findUserById(searchedId);
    }
    @ParameterizedTest
    @MethodSource("provideExpectedValue")
    @WithMockUser(username = MOCK_USERNAME)
    void deleteUserByIdShouldReturnOkStatusAndExpectedValue(DeleteRequestDTO value , boolean expected) throws Exception {
        when(userService.deleteUserById(value)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(value);

        mockMvc.perform(delete("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.is(expected)));

        verify(userService,times(1)).deleteUserById(value);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void deleteUserByIdShouldReturnNotFoundStatusAndEmptyBody() throws Exception {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);

        when(userService.deleteUserById(dto)).thenThrow(NotFoundException.class);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(delete("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().string(emptyString()));

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

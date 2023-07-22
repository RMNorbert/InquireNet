package com.rmnnorbert.InquireNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmnnorbert.InquireNet.customExceptionHandler.InvalidLoginException;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationDTO;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationResponse;
import com.rmnnorbert.InquireNet.security.auth.AuthenticationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.rmnnorbert.InquireNet.controller.AnswerControllerTest.MOCK_USERNAME;
import static org.hamcrest.Matchers.emptyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationService authenticationService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void registerShouldReturnOkStatusAndExpectedValue() throws Exception {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");
        AuthenticationResponse response = new AuthenticationResponse("token", LocalDateTime.now());

        when(authenticationService.register(dto)).thenReturn(response);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.is(response.token())));

        verify(authenticationService,times(1)).register(dto);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void registerShouldReturnIsUnprocessableEntityStatusAndEmptyBody() throws Exception {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");

        when(authenticationService.register(dto)).thenThrow(DataIntegrityViolationException.class);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(emptyString()));

        verify(authenticationService,times(1)).register(dto);
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void authenticateShouldReturnOkStatusAndExpectedValue() throws Exception {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");
        AuthenticationResponse response = new AuthenticationResponse("token", LocalDateTime.now());

        when(authenticationService.authenticate(dto)).thenReturn(response);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.is(response.token())));

        verify(authenticationService,times(1)).authenticate(dto);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void authenticateShouldReturnStatusAnd() throws Exception {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");

        when(authenticationService.register(dto)).thenThrow(InvalidLoginException.class);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(emptyString()));

        verify(authenticationService,times(1)).authenticate(dto);
    }
}

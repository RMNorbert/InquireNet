package com.rmnnorbert.InquireNet.controller;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.InvalidLoginException;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationDTO;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationResponse;
import com.rmnnorbert.InquireNet.security.auth.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@UnitTest
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    private AuthenticationController authenticationController;
    @Mock
    private AuthenticationService authenticationService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        this.authenticationController = new AuthenticationController(authenticationService);
    }
    @Test
    void registerShouldReturnExpectedValue() {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");
        AuthenticationResponse response = new AuthenticationResponse("token", LocalDateTime.now());

        when(authenticationService.register(dto)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> actual = authenticationController.register(dto);

        assertEquals(response, actual.getBody());
        verify(authenticationService,times(1)).register(dto);
    }
    @Test
    void registerShouldReturnIsUnprocessableEntityWithDataIntegrityViolationException() {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");

        when(authenticationService.register(dto)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> authenticationController.register(dto));
        verify(authenticationService,times(1)).register(dto);
    }

    @Test
    void authenticateShouldReturnExpectedValue() {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");
        AuthenticationResponse response = new AuthenticationResponse("token", LocalDateTime.now());

        when(authenticationService.authenticate(dto)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> actual = authenticationController.authenticate(dto);

        assertEquals(response, actual.getBody());
        verify(authenticationService,times(1)).authenticate(dto);
    }
    @Test
    void authenticateShouldReturnInvalidLoginException() {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");

        when(authenticationService.authenticate(dto)).thenThrow(InvalidLoginException.class);

        assertThrows(InvalidLoginException.class, () -> authenticationController.authenticate(dto));
        verify(authenticationService,times(1)).authenticate(dto);
    }
}

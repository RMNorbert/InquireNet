package com.rmnnorbert.InquireNet.security.auth;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.model.user.User;
import com.rmnnorbert.InquireNet.dao.model.user.UserDaoJdbc;
import com.rmnnorbert.InquireNet.dao.model.user.data.Role;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationDTO;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationResponse;
import com.rmnnorbert.InquireNet.security.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {
    @Mock
    private UserDaoJdbc userDaoJdbc;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    private AuthenticationService authenticationService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationService(userDaoJdbc, passwordEncoder, jwtService, authenticationManager);
    }
    @Test
    void registerShouldReturnExpectedAuthenticationResponse() {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");
        User user = new User(1, Role.USER,"username","password",LocalDateTime.now());

        when(userDaoJdbc.findUser(dto.username())).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("token");

        AuthenticationResponse expected = new AuthenticationResponse("token", LocalDateTime.now());
        AuthenticationResponse actual = authenticationService.register(dto);

        assertAuthenticationResponse(expected,actual);
        verify(userDaoJdbc,times(1)).findUser(dto.username());
        verify(jwtService,times(1)).generateToken(user);
    }
    @Test
    void registerShouldReturnDataIntegrityViolationException() {
        AuthenticationDTO dto = new AuthenticationDTO("username","passw");

        when(userDaoJdbc.findUser(dto.username())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> authenticationService.register(dto));
        verify(userDaoJdbc,times(1)).findUser(dto.username());
    }

    @Test
    void authenticateShouldReturnExpectedValue() {
        AuthenticationDTO dto = new AuthenticationDTO("username","passw");
        User user = new User(1, Role.USER,"username","password",LocalDateTime.now());
        HashMap<String, Object> additionalClaims = new HashMap<>();

        when(userDaoJdbc.findUser(dto.username())).thenReturn(user);
        when(jwtService.generateToken(additionalClaims,user)).thenReturn("token");

        AuthenticationResponse expected = new AuthenticationResponse("token", LocalDateTime.now().plusHours(1));
        AuthenticationResponse actual = authenticationService.authenticate(dto);
        assertAuthenticationResponse(expected,actual);
        verify(userDaoJdbc,times(1)).findUser(dto.username());
    }
    @Test
    void authenticateShouldReturnNotFoundException() {
        AuthenticationDTO dto = new AuthenticationDTO("username","passw");

        when(userDaoJdbc.findUser(dto.username())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> authenticationService.authenticate(dto));
        verify(userDaoJdbc,times(1)).findUser(dto.username());
    }
    private boolean assertAuthenticationResponse(AuthenticationResponse expected, AuthenticationResponse actual) {
        return expected.token().equals(actual.token()) &&
                expected.time().toLocalTime().equals(actual.time().toLocalTime());
    }
}

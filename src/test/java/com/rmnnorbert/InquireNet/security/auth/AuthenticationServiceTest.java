package com.rmnnorbert.InquireNet.security.auth;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.InvalidLoginException;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.model.user.User;
import com.rmnnorbert.InquireNet.dao.model.user.UserDaoJdbc;
import com.rmnnorbert.InquireNet.dao.model.user.data.Role;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationDTO;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationResponse;
import com.rmnnorbert.InquireNet.service.security.JwtService;
import com.rmnnorbert.InquireNet.service.security.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@UnitTest
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
        User user = new User(1, Role.USER,"username","password",LocalDateTime.of(2000,10,10,10,10));

        when(userDaoJdbc.findUser(dto.username())).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("token");

        AuthenticationResponse expected = new AuthenticationResponse("token", LocalDateTime.now().plusHours(3));
        AuthenticationResponse actual = authenticationService.register(dto);

        assertTrue(assertAuthenticationResponse(expected,actual));
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
    void authenticateShouldReturnFalse() {
        AuthenticationDTO dto = new AuthenticationDTO("username","passw");
        User user = new User(1, Role.USER,"username","password",LocalDateTime.now());
        HashMap<String, Object> additionalClaims = new HashMap<>();

        when(userDaoJdbc.findUser(dto.username())).thenReturn(user);
        when(jwtService.generateToken(additionalClaims,user)).thenReturn("token");

        AuthenticationResponse expected = new AuthenticationResponse("token", LocalDateTime.now().plusHours(1));
        AuthenticationResponse actual = authenticationService.authenticate(dto);
        assertFalse(assertAuthenticationResponse(expected,actual));
        verify(userDaoJdbc,times(1)).findUser(dto.username());
    }
    @Test
    void authenticateShouldReturnNotFoundException() {
        AuthenticationDTO dto = new AuthenticationDTO("username","passw");

        when(userDaoJdbc.findUser(dto.username())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> authenticationService.authenticate(dto));
        verify(userDaoJdbc,times(1)).findUser(dto.username());
    }
    @Test
    void authenticateShouldReturnInvalidLoginException() {
        AuthenticationDTO dto = new AuthenticationDTO("username","passw");
        User user = new User(1, Role.USER,"username","password",LocalDateTime.now());

        when(userDaoJdbc.findUser(dto.username())).thenReturn(user);
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password())))
                .thenThrow(InvalidLoginException.class);

        assertThrows(InvalidLoginException.class, () -> authenticationService.authenticate(dto));
        verify(userDaoJdbc,times(1)).findUser(dto.username());
    }
    private boolean assertAuthenticationResponse(AuthenticationResponse expected, AuthenticationResponse actual) {
        return expected.token().equals(actual.token()) &&
                expected.time().toLocalDate().equals(actual.time().toLocalDate());
    }
}

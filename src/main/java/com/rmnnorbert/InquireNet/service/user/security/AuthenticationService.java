package com.rmnnorbert.InquireNet.service.user.security;

import com.rmnnorbert.InquireNet.customExceptionHandler.InvalidLoginException;
import com.rmnnorbert.InquireNet.dao.model.user.User;
import com.rmnnorbert.InquireNet.dao.model.user.UserDaoJdbc;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationResponse;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationDTO;
import com.rmnnorbert.InquireNet.service.user.security.loginHistory.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class AuthenticationService {
    private final UserDaoJdbc userDaoJdbc;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LoginHistoryService analyticsService;
    @Autowired
    public AuthenticationService(UserDaoJdbc userDaoJdbc, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, LoginHistoryService analyticsService) {
        this.userDaoJdbc = userDaoJdbc;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.analyticsService = analyticsService;
    }
    public AuthenticationResponse register(AuthenticationDTO request){
       String password = passwordEncoder.encode(request.password());
       userDaoJdbc.addUser(request.username(),password);
       User user = userDaoJdbc.findUser(request.username());

       String jwtToken = jwtService.generateToken(user);
       return AuthenticationResponse.builder()
               .token(jwtToken)
               .time(LocalDateTime.now())
               .build();
    }

    public AuthenticationResponse authenticate(AuthenticationDTO request){
        User user = userDaoJdbc.findUser(request.username());

        try {
            authenticationManager.authenticate (
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
            analyticsService.successfulLogin(user.getId());
            HashMap<String, Object> additionalClaims = new HashMap<>();
            additionalClaims.put("role", user.getRole());
            additionalClaims.put("userId",user.getId());
            String jwtToken = jwtService.generateToken(additionalClaims, user);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .time(LocalDateTime.now().plusHours(3))
                    .build();
        } catch (Exception e) {
            analyticsService.unSuccessfulLogin(user.getId());
            throw new InvalidLoginException();
        }
    }

}

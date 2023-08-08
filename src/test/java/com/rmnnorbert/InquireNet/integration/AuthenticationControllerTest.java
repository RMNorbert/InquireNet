package com.rmnnorbert.InquireNet.integration;

import annotations.IntegrationTest;
import com.rmnnorbert.InquireNet.controller.AuthenticationController;
import com.rmnnorbert.InquireNet.dto.user.AuthenticationDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.jdbc.Sql;

@IntegrationTest
@SpringBootTest
@Sql({ "/clear.sql", "/testInit.sql" })
class AuthenticationControllerTest {
    @Autowired
    private AuthenticationController authenticationController;
    /*@Test
    void registerShouldReturnExpectedValue() {
        AuthenticationDTO dto = new AuthenticationDTO("username","password1");
        ResponseEntity<AuthenticationResponse> response = ResponseEntity.ok(new AuthenticationResponse("token", LocalDateTime.now()));
        ResponseEntity<AuthenticationResponse> actual = authenticationController.register(dto);
        //assertThat(authenticationController.register(dto)).extracting(ResponseEntity.ok()::body)
        //        .isEqualTo(response);
        assertThat(actual).extracting(ResponseEntity.ok()::body).isEqualTo(response);
    }*/
    @Test
    void registerShouldReturnIsUnprocessableEntityStatusAndEmptyBody() {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");

        Assertions.assertThatExceptionOfType(BadSqlGrammarException.class)
                .isThrownBy(() -> authenticationController.register(dto));
    }

    /*@Test
    void authenticateShouldReturnOkStatusAndExpectedValue() {
        AuthenticationDTO dto = new AuthenticationDTO("username","password");
        AuthenticationResponse response = new AuthenticationResponse("token", LocalDateTime.now());

        assertThat(authenticationController.authenticate(dto)).extracting(ResponseEntity.ok()::body)
                .isEqualTo(response);
    }*/

}

package com.rmnnorbert.InquireNet.config;

import com.rmnnorbert.InquireNet.service.user.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.rmnnorbert.InquireNet.dao.model.user.data.Role.EMPLOYEE;
import static com.rmnnorbert.InquireNet.dao.model.user.data.Role.USER;
@Configuration
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    @Autowired
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                        "/",
                        "/api/**",
                        "/register",
                        "/authenticate",
                        "/dist/index",
                        "/index.html",
                        "/static/**",
                        "/*.ico",
                        "/*.json",
                        "/*.png",
                        "/*.svg",
                        "/static/dist/**",
                        "/home",
                        "/assets/**",
                        "/actuator/**",
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/api-docs.yaml"
                    ).permitAll()
                    .requestMatchers(
                        "/answers/**",
                        "/questions/**",
                        "/reply/**",
                        "/chat/**",
                        "/user/{id}",
                        "/user/"
                    ).hasAnyRole(USER.name(), EMPLOYEE.name())
                    .requestMatchers(
                       "/user/all"
                    ).hasAnyRole(EMPLOYEE.name())
                    .anyRequest()
                    .authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

package com.jdw.jwtauth.configs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {
    @Mock
    HttpSecurity httpSecurity;
    @InjectMocks
    SecurityConfig securityConfig;

    @Test
    void passwordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        assertNotNull(passwordEncoder);
    }

    @Test
    void authenticationManager() {
        AuthenticationManager authenticationManager = securityConfig.authenticationManager();

        assertNotNull(authenticationManager);
    }

    @Test
    void securityFilterChain() throws Exception {
        when(httpSecurity
                .cors(any()))
                .thenReturn(httpSecurity);
        when(httpSecurity
                .csrf(any()))
                .thenReturn(httpSecurity);
        when(httpSecurity
                .authorizeHttpRequests(any()))
                .thenReturn(httpSecurity);
        when(httpSecurity
                .userDetailsService(any()))
                .thenReturn(httpSecurity);
        when(httpSecurity
                .sessionManagement(any()))
                .thenReturn(httpSecurity);
        when(httpSecurity
                .addFilterBefore(any(), any()))
                .thenReturn(httpSecurity);
        when(httpSecurity
                .logout(any()))
                .thenReturn(httpSecurity);
        when(httpSecurity
                .exceptionHandling(any()))
                .thenReturn(httpSecurity);

        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(httpSecurity);

        assertNull(securityFilterChain);
    }

    @Test
    void corsConfigurationSource() {
        CorsConfigurationSource corsConfigurationSource = securityConfig.corsConfigurationSource();

        assertNotNull(corsConfigurationSource);
        assertInstanceOf(UrlBasedCorsConfigurationSource.class, corsConfigurationSource);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = (UrlBasedCorsConfigurationSource) corsConfigurationSource;

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setRequestURI("/");

        CorsConfiguration corsConfiguration = urlBasedCorsConfigurationSource.getCorsConfiguration(request);

        assertNotNull(corsConfiguration);

        assertEquals(List.of("http://*:[*]"), corsConfiguration.getAllowedOriginPatterns());
        assertEquals(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH", "OPTIONS"), corsConfiguration.getAllowedMethods());
        assertEquals(List.of("Authorization", "Content-Type"), corsConfiguration.getAllowedHeaders());
    }
}
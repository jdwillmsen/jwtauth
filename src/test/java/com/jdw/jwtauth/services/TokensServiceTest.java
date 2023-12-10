package com.jdw.jwtauth.services;

import com.jdw.jwtauth.repositories.tokens.TokensRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static com.jdw.jwtauth.fixtures.SharedFixtures.TOKEN_STRING;
import static com.jdw.jwtauth.fixtures.SharedFixtures.VALID_BEARER_TOKEN;
import static com.jdw.jwtauth.fixtures.TokenFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokensServiceTest {
    @Mock
    TokensRepository tokensRepository;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    Authentication authentication;
    @InjectMocks
    TokensService tokensService;

    @Test
    void logout() {
        when(httpServletRequest.getHeader(anyString())).thenReturn(VALID_BEARER_TOKEN);
        when(tokensRepository.get(anyString())).thenReturn(OPTIONAL_TOKEN);

        tokensService.logout(httpServletRequest, httpServletResponse, authentication);

        verify(tokensRepository, times(1)).update(DEACTIVE_TOKEN);
    }

    @Test
    void isTokenActive() {
        when(tokensRepository.get(anyString())).thenReturn(OPTIONAL_TOKEN);

        boolean result = tokensService.isTokenActive(TOKEN_STRING);

        assertTrue(result);
    }
}
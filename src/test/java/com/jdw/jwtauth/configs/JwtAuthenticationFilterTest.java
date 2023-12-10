package com.jdw.jwtauth.configs;

import com.jdw.jwtauth.models.SecurityUser;
import com.jdw.jwtauth.repositories.roles.RolesRepository;
import com.jdw.jwtauth.services.JwtService;
import com.jdw.jwtauth.services.JwtUserDetailService;
import com.jdw.jwtauth.services.TokensService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static com.jdw.jwtauth.fixtures.UserFixtures.USER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    JwtUserDetailService jwtUserDetailService;
    @Mock
    TokensService tokensService;
    @Mock
    RolesRepository rolesRepository;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    FilterChain filterChain;

    @Test
    void doFilterInternal() throws ServletException, IOException {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUserDetailService, tokensService);
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);
        String token = JwtService.generateToken(userDetails);
        when(httpServletRequest.getHeader(AUTHORIZATION_HEADER)).thenReturn(token);

        jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        verify(httpServletResponse, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(httpServletRequest, atLeastOnce()).getHeader(AUTHORIZATION_HEADER);
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    void shouldNotFilterErrorDispatch() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUserDetailService, tokensService);

        boolean shouldFilter = jwtAuthenticationFilter.shouldNotFilterErrorDispatch();

        assertFalse(shouldFilter);
    }
}
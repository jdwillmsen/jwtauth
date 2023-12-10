package com.jdw.jwtauth.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.*;

class CustomAuthenticationEntryPointTest {

    @Test
    void commence() throws IOException {
        CustomAuthenticationEntryPoint customEntryPoint = new CustomAuthenticationEntryPoint();
        HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockedResponse = mock(HttpServletResponse.class);
        AuthenticationException mockedAuthenticationException = mock(AuthenticationException.class);

        customEntryPoint.commence(mockedRequest, mockedResponse, mockedAuthenticationException);

        verify(mockedResponse).addHeader("Access-Denied-Reason", "Authentication Required");
        verify(mockedResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied " + mockedAuthenticationException.getMessage());
        verifyNoMoreInteractions(mockedResponse);
    }
}
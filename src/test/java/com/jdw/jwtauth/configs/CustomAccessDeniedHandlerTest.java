package com.jdw.jwtauth.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomAccessDeniedHandlerTest {

    @Test
    void handle() throws IOException {
        CustomAccessDeniedHandler customHandler = new CustomAccessDeniedHandler(); // Replace with your class name
        HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockedResponse = mock(HttpServletResponse.class);
        AccessDeniedException mockedAccessDeniedException = mock(AccessDeniedException.class);

        customHandler.handle(mockedRequest, mockedResponse, mockedAccessDeniedException);

        verify(mockedResponse).addHeader("Access-Denied-Reason", "Not Authorized");
        verify(mockedResponse).sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied " + mockedAccessDeniedException.getMessage());
        verifyNoMoreInteractions(mockedResponse);
    }
}
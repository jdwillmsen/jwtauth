package com.jdw.jwtauth.controllers;

import com.jdw.jwtauth.models.AuthenticateRequest;
import com.jdw.jwtauth.models.RegisterRequest;
import com.jdw.jwtauth.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.jdw.jwtauth.fixtures.AuthenticateRequestFixtures.AUTHENTICATE_REQUEST;
import static com.jdw.jwtauth.fixtures.RegisterRequestFixtures.REGISTER_REQUEST;
import static com.jdw.jwtauth.fixtures.SharedFixtures.TOKEN_STRING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @Mock
    AuthenticationService authenticationService;
    @InjectMocks
    AuthenticationController authenticationController;

    @Test
    void register() {
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(TOKEN_STRING);

        String response = authenticationController.register(REGISTER_REQUEST);

        assertEquals(TOKEN_STRING, response);
    }

    @Test
    void authenticate() {
        when(authenticationService.authenticate(any(AuthenticateRequest.class))).thenReturn(TOKEN_STRING);

        String response = authenticationController.authenticate(AUTHENTICATE_REQUEST);

        assertEquals(TOKEN_STRING, response);
    }
}
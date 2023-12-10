package com.jdw.jwtauth.fixtures;

import com.jdw.jwtauth.models.AuthenticateRequest;

import static com.jdw.jwtauth.fixtures.SharedFixtures.EMAIL_ADDRESS;
import static com.jdw.jwtauth.fixtures.SharedFixtures.PASSWORD;

public class AuthenticateRequestFixtures {
    public static final AuthenticateRequest AUTHENTICATE_REQUEST = AuthenticateRequest.builder()
            .emailAddress(EMAIL_ADDRESS)
            .password(PASSWORD)
            .build();
}

package com.jdw.jwtauth.fixtures;

import com.jdw.jwtauth.models.RegisterRequest;

import static com.jdw.jwtauth.fixtures.SharedFixtures.*;

public class RegisterRequestFixtures {
    public static final RegisterRequest REGISTER_REQUEST = RegisterRequest.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .emailAddress(EMAIL_ADDRESS)
            .password(PASSWORD)
            .build();
}

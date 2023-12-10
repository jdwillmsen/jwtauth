package com.jdw.jwtauth.fixtures;

import com.jdw.jwtauth.models.UserDetails;

import java.util.List;
import java.util.Optional;

import static com.jdw.jwtauth.fixtures.SharedFixtures.*;

public class UserDetailsFixtures {
    public static final UserDetails USER_DETAILS = UserDetails.builder()
            .userId(USER_ID_1)
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .emailAddress(EMAIL_ADDRESS)
            .password(PASSWORD)
            .build();
    public static final Optional<UserDetails> OPTIONAL_USER_DETAILS = Optional.of(USER_DETAILS);
    public static final List<UserDetails> USER_DETAILS_LIST = List.of(USER_DETAILS);
}

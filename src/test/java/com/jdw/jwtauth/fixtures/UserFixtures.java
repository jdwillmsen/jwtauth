package com.jdw.jwtauth.fixtures;

import com.jdw.jwtauth.models.User;

import java.util.List;
import java.util.Optional;

import static com.jdw.jwtauth.fixtures.SharedFixtures.USER_ID_1;
import static com.jdw.jwtauth.fixtures.UserDetailsFixtures.USER_DETAILS;

public class UserFixtures {
    public static final User USER = User.builder()
            .userId(USER_ID_1)
            .userDetails(USER_DETAILS)
            .roles(List.of())
            .build();
    public static final User UPDATE_USER = User.builder()
            .userDetails(USER_DETAILS)
            .build();
    public static final Optional<User> OPTIONAL_USER = Optional.of(USER);
    public static final List<User> USER_LIST = List.of(USER);
}

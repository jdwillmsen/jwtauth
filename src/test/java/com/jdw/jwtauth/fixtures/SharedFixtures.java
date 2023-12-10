package com.jdw.jwtauth.fixtures;

import java.util.List;
import java.util.Optional;

public class SharedFixtures {
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Smith";
    public static final String EMAIL_ADDRESS = "john.smith@test.com";
    public static final String PASSWORD = "default_password";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String VALID_BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLnNtaXRoQHRlc3QuY29tIiwiaWF0IjoxNzAxOTMwNDQ0LCJleHAiOjE3MDE5Mzc2NDR9.w1_yCfKVRCD4bF46oKws1bspYNomwIV35sBPyxjKBgk";
    public static final String TOKEN_STRING = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLnNtaXRoQHRlc3QuY29tIiwiaWF0IjoxNzAxOTMwNDQ0LCJleHAiOjE3MDE5Mzc2NDR9.w1_yCfKVRCD4bF46oKws1bspYNomwIV35sBPyxjKBgk";
    public static final String ROLE_NAME = "ADMIN";
    public static final String ROLE_DESCRIPTION = "An administrator role";
    public static final List<String> ROLE_NAME_LIST = List.of(ROLE_NAME);

    public static final Long USER_ID_1 = 1L;
    public static final Long ROLE_ID_1 = 1L;
    public static final Long TOKEN_ID_1 = 1L;
    public static final Optional<Long> OPTIONAL_ROLE_ID_1 = Optional.of(ROLE_ID_1);
    public static final Optional<Long> OPTIONAL_USER_ID_1 = Optional.of(USER_ID_1);
    public static final Optional<Long> OPTIONAL_TOKEN_ID_1 = Optional.of(TOKEN_ID_1);
    public static final List<Long> USERS_LIST = List.of(USER_ID_1);
    public static final List<Long> ROLES_LIST = List.of(ROLE_ID_1);
    public static final List<Long> TOKENS_LIST = List.of(TOKEN_ID_1);
}

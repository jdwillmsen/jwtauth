package com.jdw.jwtauth.fixtures;

import com.jdw.jwtauth.models.Token;

import java.util.List;
import java.util.Optional;

import static com.jdw.jwtauth.fixtures.SharedFixtures.*;

public class TokenFixtures {
    public static final Token TOKEN = Token.builder()
            .tokenId(TOKEN_ID_1)
            .userId(USER_ID_1)
            .token(TOKEN_STRING)
            .active(true)
            .build();
    public static final Token SAVE_TOKEN = Token.builder()
            .userId(USER_ID_1)
            .token(TOKEN_STRING)
            .build();
    public static final Token DEACTIVE_TOKEN = Token.builder()
            .tokenId(TOKEN_ID_1)
            .active(false)
            .build();
    public static final Optional<Token> OPTIONAL_TOKEN = Optional.of(TOKEN);
    public static final List<Token> TOKEN_LIST = List.of(TOKEN);
}

package com.jdw.jwtauth.repositories.tokens;

import com.jdw.jwtauth.models.Token;

import java.util.List;
import java.util.Optional;

public interface TokensRepository {
    List<Token> getActive(Long userId);
    Optional<Token> get(String jwtToken);
    Optional<Long> add(Token token);
    void update(Token token);
}

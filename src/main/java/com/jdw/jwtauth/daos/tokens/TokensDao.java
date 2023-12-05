package com.jdw.jwtauth.daos.tokens;

import com.jdw.jwtauth.models.Token;

import java.util.List;
import java.util.Optional;

public interface TokensDao {
    List<Token> getActive(Long userId);
    Optional<Token> get(String jwtToken);
    Optional<Long> save(Token token);
    void update(Token token);
}

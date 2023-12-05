package com.jdw.jwtauth.repositories.tokens;

import com.jdw.jwtauth.daos.tokens.TokensDao;
import com.jdw.jwtauth.models.Token;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
@Transactional
public class TokensRepositoryImpl implements TokensRepository {
    private final TokensDao tokensDao;

    @Override
    public List<Token> getActive(Long userId) {
        log.debug("Getting all active tokens with: userId={}", userId);
        return tokensDao.getActive(userId);
    }

    @Override
    public Optional<Token> get(String jwtToken) {
        log.debug("Getting token with: jwtToken={}", jwtToken);
        return tokensDao.get(jwtToken);
    }

    @Override
    public Optional<Long> add(Token token) {
        log.debug("Adding token with: userId={}, jwtToken={}", token.userId(), token.token());
        return tokensDao.save(token);
    }

    @Override
    public void update(Token token) {
        log.debug("Updating token with: tokenId={}", token.tokenId());
        tokensDao.update(token);
    }
}

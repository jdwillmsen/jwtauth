package com.jdw.jwtauth.daos.tokens;

import com.jdw.jwtauth.models.Token;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class TokensDaoPostgres implements TokensDao {
    protected static final String SELECT_ACTIVE_TOKENS_SQL = """
            SELECT *
            FROM jdw.jwtauth.tokens
            WHERE user_id = ?
                AND active = true;
            """;
    protected static final String SELECT_TOKEN_SQL = """
            SELECT *
            FROM jdw.jwtauth.tokens
            WHERE token = ?;
            """;
    protected static final String INSERT_TOKEN_SQL = """
            INSERT INTO jdw.jwtauth.tokens (user_id, token, created_time, expired_time, modified_time)
            VALUES (?, ?, ?, ?, ?)
            RETURNING token_id;
            """;
    protected static final String UPDATE_TOKEN_SQL = """
            UPDATE jdw.jwtauth.tokens
            SET active = ?,
                modified_time = ?
            WHERE token_id = ?;
            """;
    private final JdbcTemplate jdbcTemplate;

    protected static Token mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.debug("Mapping row: rs={}, rowNum={}", rs, rowNum);
        return Token.builder()
                .tokenId(rs.getLong("token_id"))
                .userId(rs.getLong("user_id"))
                .token(rs.getString("token"))
                .active(rs.getBoolean("active"))
                .build();
    }

    @Override
    public List<Token> getActive(Long userId) {
        log.info("Getting all active tokens with: userId={}", userId);
        return jdbcTemplate.query(SELECT_ACTIVE_TOKENS_SQL, TokensDaoPostgres::mapRow, userId);
    }

    @Override
    public Optional<Token> get(String jwtToken) {
        log.info("Getting token with: jwtToken={}", jwtToken);
        return jdbcTemplate
                .query(SELECT_TOKEN_SQL, TokensDaoPostgres::mapRow, jwtToken)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Long> save(Token token) {
        log.info("Creating token with: userId={}, jwtToken={}", token.userId(), token.token());
        return jdbcTemplate.query(
                        INSERT_TOKEN_SQL,
                        (rs, rowNum) -> rs.getLong("token_id"),
                        token.userId(),
                        token.token(),
                        Timestamp.from(Instant.now()),
                        Timestamp.from(Instant.now().plus(2, ChronoUnit.HOURS)),
                        Timestamp.from(Instant.now())
                )
                .stream()
                .findFirst();
    }

    @Override
    public void update(Token token) {
        log.info("Updating token with: tokenId={}", token.tokenId());
        jdbcTemplate.update(UPDATE_TOKEN_SQL, token.active(), Timestamp.from(Instant.now()), token.tokenId());
    }
}

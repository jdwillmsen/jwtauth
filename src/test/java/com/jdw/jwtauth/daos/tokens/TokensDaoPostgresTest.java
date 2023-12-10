package com.jdw.jwtauth.daos.tokens;

import com.jdw.jwtauth.models.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static com.jdw.jwtauth.fixtures.TokenFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokensDaoPostgresTest {
    @Mock
    ResultSet rs;
    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    TokensDaoPostgres tokensDaoPostgres;

    @Test
    void mapRow() throws SQLException {
        when(rs.getLong("token_id")).thenReturn(TOKEN_ID_1);
        when(rs.getLong("user_id")).thenReturn(USER_ID_1);
        when(rs.getString("token")).thenReturn(TOKEN_STRING);
        when(rs.getBoolean("active")).thenReturn(true);

        Token result = TokensDaoPostgres.mapRow(rs, 1);

        assertEquals(TOKEN, result);
    }

    @Test
    void getActive() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Token>>any(), anyLong())).thenReturn(TOKEN_LIST);

        List<Token> result = tokensDaoPostgres.getActive(USER_ID_1);

        assertEquals(TOKEN_LIST, result);
    }

    @Test
    void get() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Token>>any(), anyString())).thenReturn(TOKEN_LIST);

        Optional<Token> result = tokensDaoPostgres.get(TOKEN_STRING);

        assertEquals(OPTIONAL_TOKEN, result);
    }

    @Test
    void save() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Long>>any(), any(), any(), any(), any(), any())).thenReturn(TOKENS_LIST);

        Optional<Long> result = tokensDaoPostgres.save(TOKEN);

        assertEquals(OPTIONAL_TOKEN_ID_1, result);
    }

    @Test
    void update() {
        tokensDaoPostgres.update(TOKEN);

        verify(jdbcTemplate, times(1)).update(
                anyString(),
                eq(true),
                any(),
                eq(TOKEN_ID_1)
        );
    }
}
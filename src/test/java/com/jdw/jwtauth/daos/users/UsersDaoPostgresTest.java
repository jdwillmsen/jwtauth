package com.jdw.jwtauth.daos.users;

import com.jdw.jwtauth.models.UserDetails;
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
import static com.jdw.jwtauth.fixtures.UserDetailsFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersDaoPostgresTest {
    @Mock
    ResultSet rs;
    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    UsersDaoPostgres usersDaoPostgres;

    @Test
    void mapRow() throws SQLException {
        when(rs.getLong("user_id")).thenReturn(USER_ID_1);
        when(rs.getString("first_name")).thenReturn(FIRST_NAME);
        when(rs.getString("last_name")).thenReturn(LAST_NAME);
        when(rs.getString("email_address")).thenReturn(EMAIL_ADDRESS);
        when(rs.getString("password")).thenReturn(PASSWORD);

        UserDetails result = UsersDaoPostgres.mapRow(rs, 1);

        assertEquals(USER_DETAILS, result);
    }

    @Test
    void getAll() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserDetails>>any())).thenReturn(USER_DETAILS_LIST);

        List<UserDetails> result = usersDaoPostgres.getAll();

        assertEquals(USER_DETAILS_LIST, result);
    }

    @Test
    void getUserId() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserDetails>>any(), any())).thenReturn(USER_DETAILS_LIST);

        Optional<UserDetails> result = usersDaoPostgres.get(USER_ID_1);

        assertEquals(OPTIONAL_USER_DETAILS, result);
    }

    @Test
    void getEmailAddress() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserDetails>>any(), any())).thenReturn(USER_DETAILS_LIST);

        Optional<UserDetails> result = usersDaoPostgres.get(EMAIL_ADDRESS);

        assertEquals(OPTIONAL_USER_DETAILS, result);
    }

    @Test
    void save() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Long>>any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(USERS_LIST);

        Optional<Long> result = usersDaoPostgres.save(USER_DETAILS, USER_ID_1);

        assertEquals(OPTIONAL_USER_ID_1, result);
    }

    @Test
    void update() {
        usersDaoPostgres.update(USER_DETAILS, USER_ID_1);

        verify(jdbcTemplate, times(1)).update(
                anyString(),
                eq(FIRST_NAME),
                eq(LAST_NAME),
                eq(EMAIL_ADDRESS),
                eq(PASSWORD),
                eq(USER_ID_1),
                any(),
                eq(USER_ID_1)
        );
    }

    @Test
    void delete() {
        usersDaoPostgres.delete(USER_ID_1, USER_ID_1);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(USER_ID_1));
    }
}
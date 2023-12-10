package com.jdw.jwtauth.daos.roles;

import com.jdw.jwtauth.models.RoleDetails;
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

import static com.jdw.jwtauth.fixtures.RoleDetailsFixture.*;
import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolesDaoPostgresTest {
    @Mock
    ResultSet rs;
    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    RolesDaoPostgres rolesDaoPostgres;

    @Test
    void mapRow() throws SQLException {
        when(rs.getLong("role_id")).thenReturn(ROLE_ID_1);
        when(rs.getString("role_name")).thenReturn(ROLE_NAME);
        when(rs.getString("role_description")).thenReturn(ROLE_DESCRIPTION);
        when(rs.getBoolean("active")).thenReturn(true);

        RoleDetails result = RolesDaoPostgres.mapRow(rs, 1);

        assertEquals(ROLE_DETAILS, result);
    }

    @Test
    void getAll() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<RoleDetails>>any())).thenReturn(ROLE_DETAILS_LIST);

        List<RoleDetails> result = rolesDaoPostgres.getAll();

        assertEquals(ROLE_DETAILS_LIST, result);
    }

    @Test
    void get() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<RoleDetails>>any(), anyLong())).thenReturn(ROLE_DETAILS_LIST);

        Optional<RoleDetails> result = rolesDaoPostgres.get(ROLE_ID_1);

        assertEquals(OPTIONAL_ROLE_DETAILS, result);
    }

    @Test
    void getNames() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<String>>any(), any())).thenReturn(ROLE_NAME_LIST);

        List<String> result = rolesDaoPostgres.getNames(ROLES_LIST);

        assertEquals(result, ROLE_NAME_LIST);
    }

    @Test
    void save() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Long>>any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(ROLES_LIST);

        Optional<Long> result = rolesDaoPostgres.save(ROLE_DETAILS, USER_ID_1);

        assertEquals(OPTIONAL_ROLE_ID_1, result);
    }

    @Test
    void update() {
        rolesDaoPostgres.update(ROLE_DETAILS, USER_ID_1);

        verify(jdbcTemplate, times(1)).update(
                anyString(),
                eq(ROLE_NAME),
                eq(ROLE_DESCRIPTION),
                eq(true),
                eq(USER_ID_1),
                any(),
                eq(ROLE_ID_1));
    }
}
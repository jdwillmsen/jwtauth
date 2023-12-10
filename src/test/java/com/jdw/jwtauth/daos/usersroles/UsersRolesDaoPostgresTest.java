package com.jdw.jwtauth.daos.usersroles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersRolesDaoPostgresTest {
    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    UsersRolesDaoPostgres usersRolesDaoPostgres;

    @Test
    void getRoles() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Long>>any(), anyLong())).thenReturn(ROLES_LIST);

        List<Long> result = usersRolesDaoPostgres.getRoles(USER_ID_1);

        assertEquals(ROLES_LIST, result);
    }

    @Test
    void getUsers() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Long>>any(), anyLong())).thenReturn(USERS_LIST);

        List<Long> result = usersRolesDaoPostgres.getUsers(ROLE_ID_1);

        assertEquals(USERS_LIST, result);
    }

    @Test
    void save() {
        usersRolesDaoPostgres.save(USER_ID_1, ROLE_ID_1, USER_ID_1);

        verify(jdbcTemplate, times(1)).update(
                anyString(),
                eq(USER_ID_1),
                eq(ROLE_ID_1),
                eq(USER_ID_1),
                any()
        );
    }

    @Test
    void delete() {
        usersRolesDaoPostgres.delete(USER_ID_1, ROLE_ID_1, USER_ID_1);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(USER_ID_1), eq(ROLE_ID_1));
    }

    @Test
    void deleteNullUserId() {
        usersRolesDaoPostgres.delete(null, ROLE_ID_1, USER_ID_1);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(ROLE_ID_1));
    }

    @Test
    void deleteNullRoleId() {
        usersRolesDaoPostgres.delete(USER_ID_1, null, USER_ID_1);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(USER_ID_1));
    }

    @Test
    void deleteBothNull() {
        usersRolesDaoPostgres.delete(null, null, USER_ID_1);

        verify(jdbcTemplate, times(0)).update(anyString(), anyLong());
        verify(jdbcTemplate, times(0)).update(anyString(), anyLong(), anyLong());
    }

}
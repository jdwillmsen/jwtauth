package com.jdw.jwtauth.daos.usersroles;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class UsersRolesDaoPostgres implements UsersRolesDao {
    protected static final String SELECT_ROLES_SQL = """
            SELECT (role_id)
            FROM jdw.jwtauth.users_roles
            WHERE user_id = ?;
            """;
    protected static final String SELECT_USERS_SQL = """
            SELECT (user_id)
            FROM jdw.jwtauth.users_roles
            WHERE role_id = ?;
            """;
    protected static final String INSERT_USERS_ROLES_SQL = """
            INSERT INTO jdw.jwtauth.users_roles (user_id, role_id, created_by_user_id, created_time)
            VALUES (?, ?, ?, ?);
            """;
    protected static final String DELETE_USERS_ROLES_SQL = """
            DELETE FROM jdw.jwtauth.users_roles
            WHERE user_id = ?
                AND role_id = ?;
            """;
    protected static final String DELETE_USERS_ROLES_FOR_USER_ID_SQL = """
            DELETE FROM jdw.jwtauth.users_roles
            WHERE user_id = ?;
            """;
    protected static final String DELETE_USERS_ROLES_FOR_ROLE_ID_SQL = """
            DELETE FROM jdw.jwtauth.users_roles
            WHERE role_id = ?;
            """;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Long> getRoles(Long userId) {
        log.info("Getting roles with: userId={}", userId);
        return jdbcTemplate.query(SELECT_ROLES_SQL, ((rs, rowNum) -> rs.getLong("role_id")), userId);
    }

    @Override
    public List<Long> getUsers(Long roleId) {
        log.info("Getting users with: roleId={}", roleId);
        return jdbcTemplate.query(SELECT_USERS_SQL, ((rs, rowNum) -> rs.getLong("user_id")), roleId);
    }

    @Override
    public void save(Long userId, Long roleId, Long changeImplementerId) {
        log.info("Creating users roles with: userId={}, roleId={}, changeImplementerId={}", userId, roleId, changeImplementerId);
        jdbcTemplate.update(INSERT_USERS_ROLES_SQL, userId, roleId, changeImplementerId, Timestamp.from(Instant.now()));
    }

    @Override
    public void delete(Long userId, Long roleId, Long changeImplementerId) {
        log.info("Deleting users roles with: userId={}, roleId={}, changeImplementerId={}", userId, roleId, changeImplementerId);
        if (userId != null && roleId != null) {
            jdbcTemplate.update(DELETE_USERS_ROLES_SQL, userId, roleId);
        } else if (userId != null) {
            jdbcTemplate.update(DELETE_USERS_ROLES_FOR_USER_ID_SQL, userId);
        } else if (roleId != null) {
            jdbcTemplate.update(DELETE_USERS_ROLES_FOR_ROLE_ID_SQL, roleId);
        } else {
            log.error("Users roles was not deleted due to null userId and roleId");
        }
    }
}

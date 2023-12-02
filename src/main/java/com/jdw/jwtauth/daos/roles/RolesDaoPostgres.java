package com.jdw.jwtauth.daos.roles;

import com.jdw.jwtauth.models.RoleDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class RolesDaoPostgres implements RolesDao {
    protected static final String SELECT_ALL_ROLES_SQL = """
            SELECT *
            FROM jdw.jwtauth.roles;
            """;
    protected static final String SELECT_ROLE_SQL = """
            SELECT *
            FROM jdw.jwtauth.roles
            WHERE role_id = ?;
            """;
    protected static final String SELECT_ROLES_NAMES_SQL = """
            SELECT *
            FROM jdw.jwtauth.roles
            WHERE role_id in (%s);
            """;
    protected static final String INSERT_ROLE_SQL = """
            INSERT INTO jdw.jwtauth.roles (role_name, role_description, active,
                                          created_by_user_id, created_time, modified_by_user_id, modified_time)
            VALUES(?, ?, ?, ?, ?, ?, ?)
            RETURNING role_id;
            """;
    protected static final String UPDATE_ROLE_SQL = """
            UPDATE jdw.jwtauth.roles
            SET role_name = ?,
                role_description = ?,
                active = ?,
                modified_by_user_id = ?,
                modified_time = ?
            WHERE role_id = ?;
            """;
    private final JdbcTemplate jdbcTemplate;

    protected static RoleDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.debug("Mapping row: rs={}, rowNum={}", rs, rowNum);
        return new RoleDetails(
                rs.getLong("role_id"),
                rs.getString("role_name"),
                rs.getString("role_description"),
                rs.getBoolean("active")
        );
    }

    @Override
    public List<RoleDetails> getAll() {
        log.info("Getting all roles");
        return jdbcTemplate.query(SELECT_ALL_ROLES_SQL, RolesDaoPostgres::mapRow);
    }

    @Override
    public Optional<RoleDetails> get(Long roleId) {
        log.info("Getting role with: roleId={}", roleId);
        return jdbcTemplate
                .query(SELECT_ROLE_SQL, RolesDaoPostgres::mapRow, roleId)
                .stream()
                .findFirst();
    }

    @Override
    public List<String> getNames(List<Long> roleIdList) {
        log.info("Getting role names with: roleIdList={}", roleIdList);
        if (roleIdList == null || roleIdList.isEmpty()) return List.of();
        String ids = String.join(", ", Collections.nCopies(roleIdList.size(), "?"));
        return jdbcTemplate.query(String.format(SELECT_ROLES_NAMES_SQL, ids), (rs, nowNum) -> rs.getString("role_name"), roleIdList.toArray());
    }

    @Override
    public Optional<Long> save(RoleDetails roleDetails, Long changeImplementerId) {
        log.info("Creating role with: roleName={}", roleDetails.roleName());
        return jdbcTemplate.query(
                        INSERT_ROLE_SQL,
                        (rs, rowNum) -> rs.getLong("role_id"),
                        roleDetails.roleName(),
                        roleDetails.roleDescription(),
                        roleDetails.active(),
                        changeImplementerId,
                        Timestamp.from(Instant.now()),
                        changeImplementerId,
                        Timestamp.from(Instant.now())
                )
                .stream()
                .findFirst();
    }

    @Override
    public void update(RoleDetails roleDetails, Long changeImplementerId) {
        log.info("Updating role with: roleId={}, changeImplementerId={}", roleDetails.roleId(), changeImplementerId);
        jdbcTemplate.update(
                UPDATE_ROLE_SQL,
                roleDetails.roleName(),
                roleDetails.roleDescription(),
                roleDetails.active(),
                changeImplementerId,
                Timestamp.from(Instant.now()),
                roleDetails.roleId());
    }
}

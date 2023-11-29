package com.jdw.jwtauth.daos.users;

import com.jdw.jwtauth.models.UserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class UsersDaoPostgres implements UsersDao {
    protected static final String SELECT_ALL_USERS_SQL = """
            SELECT *
            FROM jdw.jwtauth.user;
            """;
    protected static final String SELECT_USER_SQL = """
            SELECT *
            FROM jdw.jwtauth.user
            WHERE user_id = ?;
            """;
    protected static final String SELECT_USER_BY_EMAIL_SQL = """
            SELECT *
            FROM jdw.jwtauth.user
            WHERE email_address = ?;
            """;
    protected static final String INSERT_USER_SQL = """
            INSERT INTO jdw.jwtauth.user (first_name, last_name, email_address, password,
                                        created_by_user_id, created_time, modified_by_user_id, modified_time)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING user_id;
            """;
    protected static final String UPDATE_USER_SQL = """
            UPDATE jdw.jwtauth.user
            SET first_name = ?,
                last_name = ?,
                email_address = ?,
                password = ?,
                modified_by_user_id = ?,
                modified_time = ?
            WHERE user_id = ?;
            """;
    protected static final String DELETE_USER_SQL = """
            DELETE FROM jdw.jwtauth.user
            WHERE user_id = ?;
            """;
    private final JdbcTemplate jdbcTemplate;

    protected static UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.debug("Mapping row: rs={}, rowNum={}", rs, rowNum);
        return new UserDetails(
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email_address"),
                rs.getString("password")
        );
    }

    @Override
    public List<UserDetails> getAll() {
        log.info("Getting all users");
        return jdbcTemplate.query(SELECT_ALL_USERS_SQL, UsersDaoPostgres::mapRow);
    }

    @Override
    public Optional<UserDetails> get(Long userId) {
        log.info("Getting user with: userId={}", userId);
        return jdbcTemplate
                .query(SELECT_USER_SQL, UsersDaoPostgres::mapRow, userId)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<UserDetails> get(String emailAddress) {
        log.info("Getting user with: emailAddress={}", emailAddress);
        return jdbcTemplate
                .query(SELECT_USER_BY_EMAIL_SQL, UsersDaoPostgres::mapRow, emailAddress)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Long> save(UserDetails userDetails, Long changeImplementerId) {
        log.info("Creating user with: firstName={}, lastName={}, emailAddress={}, changeImplementerId={}",
                userDetails.firstName(), userDetails.lastName(), userDetails.emailAddress(), changeImplementerId);
        return jdbcTemplate.query(
                        INSERT_USER_SQL,
                        (rs, rowNum) -> rs.getLong("user_id"),
                        userDetails.firstName(),
                        userDetails.lastName(),
                        userDetails.emailAddress(),
                        userDetails.password(),
                        changeImplementerId,
                        Timestamp.from(Instant.now()),
                        changeImplementerId,
                        Timestamp.from(Instant.now())
                )
                .stream()
                .findFirst();
    }

    @Override
    public void update(UserDetails userDetails, Long changeImplementerId) {
        log.info("Updating user with: userId={}, changeImplementerId={}", userDetails.userId(), changeImplementerId);
        jdbcTemplate.update(
                UPDATE_USER_SQL,
                userDetails.firstName(),
                userDetails.lastName(),
                userDetails.emailAddress(),
                userDetails.password(),
                changeImplementerId,
                Timestamp.from(Instant.now()),
                userDetails.userId()
        );
    }

    @Override
    public void delete(Long userId, Long changeImplementerId) {
        log.info("Deleting user with: userId={}, changeImplementerId={}", userId, changeImplementerId);
        jdbcTemplate.update(DELETE_USER_SQL, userId);
    }
}

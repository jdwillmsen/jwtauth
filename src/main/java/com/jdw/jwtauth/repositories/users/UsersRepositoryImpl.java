package com.jdw.jwtauth.repositories.users;

import com.jdw.jwtauth.daos.users.UsersDao;
import com.jdw.jwtauth.daos.usersroles.UsersRolesDao;
import com.jdw.jwtauth.models.User;
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
public class UsersRepositoryImpl implements UsersRepository {
    private final UsersDao usersDao;
    private final UsersRolesDao usersRolesDao;

    @Override
    public List<User> getAll() {
        log.debug("Getting all users");
        return usersDao
                .getAll()
                .stream()
                .map(userDetails -> User
                        .builder()
                        .userId(userDetails.userId())
                        .userDetails(userDetails)
                        .roles(usersRolesDao.getRoles(userDetails.userId()))
                        .build())
                .toList();
    }

    @Override
    public Optional<User> get(Long userId) {
        log.debug("Getting user with: userId={}", userId);
        return usersDao.get(userId).map(userDetails -> User.builder()
                .userId(userId)
                .userDetails(userDetails)
                .roles(usersRolesDao.getRoles(userId))
                .build());
    }

    @Override
    public Optional<User> get(String emailAddress) {
        log.debug("Getting user with: emailAddress={}", emailAddress);
        return usersDao.get(emailAddress).map(userDetails -> User.builder()
                .userId(userDetails.userId())
                .userDetails(userDetails)
                .roles(usersRolesDao.getRoles(userDetails.userId()))
                .build());
    }

    @Override
    public Optional<Long> add(User user, Long changeImplementerId) {
        log.debug("Adding user with: firstName={}, lastName={}, emailAddress={}, changeImplementerId={}",
                user.getUserDetails().firstName(), user.getUserDetails().lastName(), user.getUserDetails().emailAddress(), changeImplementerId);
        return usersDao.save(user.getUserDetails(), changeImplementerId);
    }

    @Override
    public void update(User user, Long changeImplementerId) {
        log.debug("Updating user with: userId={}, changeImplementerId={}", user.getUserId(), changeImplementerId);
        usersDao.update(user.getUserDetails(), changeImplementerId);
    }

    @Override
    public void delete(Long userId, Long changeImplementerId) {
        log.debug("Deleting user with: userId={}, changeImplementerId={}", userId, changeImplementerId);
        usersRolesDao.delete(userId, null, changeImplementerId);
        usersDao.delete(userId, changeImplementerId);
    }

    @Override
    public void grantRoles(Long userId, List<Long> roles, Long changeImplementerId) {
        log.debug("Granting roles with: userId={}, roles={}, changeImplementerId={}", userId, roles, changeImplementerId);
        roles.forEach(roleId -> usersRolesDao.save(userId, roleId, changeImplementerId));
    }

    @Override
    public void revokeRoles(Long userId, List<Long> roles, Long changeImplementerId) {
        log.debug("Revoking roles with: userId={}, roles={}, changeImplementerId={}", userId, roles, changeImplementerId);
        roles.forEach(roleId -> usersRolesDao.delete(userId, roleId, changeImplementerId));
    }
}

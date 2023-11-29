package com.jdw.jwtauth.repositories.roles;

import com.jdw.jwtauth.daos.roles.RolesDao;
import com.jdw.jwtauth.daos.usersroles.UsersRolesDao;
import com.jdw.jwtauth.models.Role;
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
public class RolesRepositoryImpl implements RolesRepository {
    private final RolesDao rolesDao;
    private final UsersRolesDao usersRolesDao;

    @Override
    public List<Role> getAll() {
        log.debug("Getting all roles");
        return rolesDao
                .getAll()
                .stream()
                .map(roleDetails -> Role
                        .builder()
                        .roleId(roleDetails.roleId())
                        .roleDetails(roleDetails)
                        .users(usersRolesDao.getUsers(roleDetails.roleId()))
                        .build())
                .toList();
    }

    @Override
    public Optional<Role> get(Long roleId) {
        log.debug("Getting role with: roleId={}", roleId);
        return rolesDao.get(roleId).map(roleDetails -> Role.builder()
                .roleId(roleId)
                .roleDetails(roleDetails)
                .users(usersRolesDao.getUsers(roleId))
                .build());
    }

    @Override
    public Optional<Long> add(Role role, Long changeImplementerId) {
        log.debug("Adding role with: roleName={}", role.getRoleDetails().roleName());
        return rolesDao.save(role.getRoleDetails(), changeImplementerId);
    }

    @Override
    public void update(Role role, Long changeImplementerId) {
        log.debug("Updating role with: roleId={}, changeImplementerId={}", role.getRoleDetails().roleId(), changeImplementerId);
        rolesDao.update(role.getRoleDetails(), changeImplementerId);
    }

    @Override
    public void addUsers(Long roleId, List<Long> users, Long changeImplementerId) {
        log.debug("Adding users with: roleId={}, users={}, changeImplementerId={}", roleId, users, changeImplementerId);
        users.forEach(userId -> usersRolesDao.save(userId, roleId, changeImplementerId));
    }

    @Override
    public void removeUsers(Long roleId, List<Long> users, Long changeImplementerId) {
        log.debug("Removing users with: roleId={}, users={}, changeImplementerId={}", roleId, users, changeImplementerId);
        users.forEach(userId -> usersRolesDao.delete(userId, roleId, changeImplementerId));
    }
}

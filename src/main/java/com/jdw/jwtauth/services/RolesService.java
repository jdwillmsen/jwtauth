package com.jdw.jwtauth.services;

import com.jdw.jwtauth.exceptions.NotFoundException;
import com.jdw.jwtauth.models.Role;
import com.jdw.jwtauth.models.RoleDetails;
import com.jdw.jwtauth.repositories.roles.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RolesService {
    private final RolesRepository rolesRepository;

    public List<Role> getRoles() {
        log.debug("Getting all roles");
        return rolesRepository.getAll();
    }

    public Role getRole(Long roleId) {
        log.debug("Getting role with: roleId={}", roleId);
        return rolesRepository.get(roleId).orElseThrow(() -> new NotFoundException(String.format("Role not found with: roleId=%s", roleId)));
    }

    public void addRole(RoleDetails roleDetails, Long changeImplementerId) {
        log.debug("Adding role with: roleName={}, changeImplementerId={}", roleDetails.roleName(), changeImplementerId);
        rolesRepository.add(
                Role.builder()
                        .roleDetails(roleDetails)
                        .build(),
                changeImplementerId
        );
    }

    public void updateRole(Long roleId, RoleDetails roleDetails, Long changeImplementerId) {
        log.debug("Updating role with: userId={}, changeImplementerId={}", roleId, changeImplementerId);
        rolesRepository.update(
                Role.builder()
                        .roleId(roleId)
                        .roleDetails(RoleDetails.builder()
                                .roleId(roleId)
                                .roleName(roleDetails.roleName())
                                .roleDescription(roleDetails.roleDescription())
                                .active(roleDetails.active())
                                .build())
                        .build(),
                changeImplementerId
        );
    }

    public void addUsersToRole(Long roleId, List<Long> users, Long changeImplementerId) {
        log.debug("Adding users with: roleId={}, users={}, changeImplementerId={}", roleId, users, changeImplementerId);
        rolesRepository.addUsers(roleId, users, changeImplementerId);
    }

    public void removeUsersFromRole(Long roleId, List<Long> users, Long changeImplementerId) {
        log.debug("Removing users with: roleId={}, users={}, changeImplementerId={}", roleId, users, changeImplementerId);
        rolesRepository.removeUsers(roleId, users, changeImplementerId);
    }

}

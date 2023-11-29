package com.jdw.jwtauth.repositories.roles;

import com.jdw.jwtauth.models.Role;

import java.util.List;
import java.util.Optional;

public interface RolesRepository {
    List<Role> getAll();
    Optional<Role> get(Long roleId);
    Optional<Long> add(Role role, Long changeImplementerId);
    void update(Role role, Long changeImplementerId);
    void addUsers(Long roleId, List<Long> users, Long changeImplementerId);
    void removeUsers(Long roleId, List<Long> users, Long changeImplementerId);
}

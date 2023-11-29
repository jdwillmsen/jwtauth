package com.jdw.jwtauth.daos.roles;

import com.jdw.jwtauth.models.RoleDetails;

import java.util.List;
import java.util.Optional;

public interface RolesDao {
    List<RoleDetails> getAll();
    Optional<RoleDetails> get(Long roleId);
    Optional<Long> save(RoleDetails roleDetails, Long changeImplementerId);
    void update(RoleDetails roleDetails, Long changeImplementerId);
}

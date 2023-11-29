package com.jdw.jwtauth.daos.usersroles;

import java.util.List;

public interface UsersRolesDao {
    List<Long> getRoles(Long userId);
    List<Long> getUsers(Long roleId);
    void save(Long userId, Long roleId, Long changeImplementerId);
    void delete(Long userId, Long roleId, Long changeImplementerId);
}

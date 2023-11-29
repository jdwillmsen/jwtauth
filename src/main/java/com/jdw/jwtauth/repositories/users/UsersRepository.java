package com.jdw.jwtauth.repositories.users;

import com.jdw.jwtauth.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    List<User> getAll();
    Optional<User> get(Long userId);
    Optional<User> get(String emailAddress);
    Optional<Long> add(User user, Long changeImplementerId);
    void update(User user, Long changeImplementerId);
    void remove(Long userId, Long changeImplementerId);
    void grantRoles(Long userId, List<Long> roles, Long changeImplementerId);
    void revokeRoles(Long userId, List<Long> roles, Long changeImplementerId);
}

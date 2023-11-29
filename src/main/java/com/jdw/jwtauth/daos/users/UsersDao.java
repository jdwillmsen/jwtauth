package com.jdw.jwtauth.daos.users;

import com.jdw.jwtauth.models.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UsersDao {
    List<UserDetails> getAll();
    Optional<UserDetails> get(Long userId);
    Optional<UserDetails> get(String emailAddress);
    Optional<Long> save(UserDetails userDetails, Long changeImplementerId);
    void update(UserDetails userDetails, Long changeImplementerId);
    void delete(Long userId, Long changeImplementerId);
}

package com.jdw.jwtauth.services;

import com.jdw.jwtauth.exceptions.NotFoundException;
import com.jdw.jwtauth.models.RegisterRequest;
import com.jdw.jwtauth.models.User;
import com.jdw.jwtauth.models.UserDetails;
import com.jdw.jwtauth.repositories.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        log.debug("Getting all users");
        return usersRepository.getAll();
    }

    public User getUser(Long userId) {
        log.debug("Getting user with: userId={}", userId);
        return usersRepository.get(userId).orElseThrow(() -> new NotFoundException(String.format("User not found with: userId=%s", userId)));
    }

    public void updateUser(Long userId, RegisterRequest registerRequest, Long changeImplementerId) {
        log.debug("Updating user with: userId={}, changeImplementerId={}", userId, changeImplementerId);
        usersRepository.update(
                User.builder()
                        .userDetails(UserDetails.builder()
                                .userId(userId)
                                .firstName(registerRequest.firstName())
                                .lastName(registerRequest.lastName())
                                .emailAddress(registerRequest.emailAddress())
                                .password(passwordEncoder.encode(registerRequest.password()))
                                .build())
                        .build(),
                changeImplementerId
        );
    }

    public void deleteUser(Long userId, Long changeImplementerId) {
        log.debug("Deleting user with: userId={}, changeImplementerId={}", userId, changeImplementerId);
        usersRepository.delete(userId, changeImplementerId);
    }

    public void grantUserRoles(Long userId, List<Long> roles, Long changeImplementerId) {
        log.debug("Granting roles with: userId={}, roles={}, changeImplementerId={}", userId, roles, changeImplementerId);
        usersRepository.grantRoles(userId, roles, changeImplementerId);
    }

    public void revokeUserRoles(Long userId, List<Long> roles, Long changeImplementerId) {
        log.debug("Revoking roles with: userId={}, roles={}, changeImplementerId={}", userId, roles, changeImplementerId);
        usersRepository.revokeRoles(userId, roles, changeImplementerId);
    }

    public Long getUserId(String emailAddress) {
        return usersRepository.get(emailAddress)
                .orElseThrow(() -> new NotFoundException(String.format("User not found with: emailAddress=%s", emailAddress)))
                        .getUserId();
    }

}

package com.jdw.jwtauth.controllers;

import com.jdw.jwtauth.models.RegisterRequest;
import com.jdw.jwtauth.models.User;
import com.jdw.jwtauth.services.JwtService;
import com.jdw.jwtauth.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<User> getUsers() {
        log.debug("Getting all users");
        return usersService.getUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.getUserId()")
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        log.debug("Getting user with: userId={}", userId);
        return usersService.getUser(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.getUserId()")
    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody RegisterRequest registerRequest, @RequestHeader(name = "Authorization") String authorizationHeader) {
        log.debug("Updating user with: userId={}", userId);
        usersService.updateUser(userId, registerRequest, usersService.getUserId(JwtService.getEmailAddress(authorizationHeader)));
    }

    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.getUserId()")
    @DeleteMapping("/{userId}")
    public void deleteUpdate(@PathVariable Long userId, @RequestHeader(name = "Authorization") String authorizationHeader) {
        log.debug("Deleting user with: userId={}", userId);
        usersService.deleteUser(userId, usersService.getUserId(JwtService.getEmailAddress(authorizationHeader)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/roles")
    public void grantUserRoles(@PathVariable Long userId, @RequestBody List<Long> roles, @RequestHeader(name = "Authorization") String authorizationHeader) {
        log.debug("Granting roles with: userId={}, roles={}", userId, roles);
        usersService.grantUserRoles(userId, roles, usersService.getUserId(JwtService.getEmailAddress(authorizationHeader)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}/roles")
    public void revokeUserRoles(@PathVariable Long userId, @RequestBody List<Long> roles, @RequestHeader(name = "Authorization") String authorizationHeader) {
        log.debug("Revoking roles with: userId={}, roles={}", userId, roles);
        usersService.revokeUserRoles(userId, roles, usersService.getUserId(JwtService.getEmailAddress(authorizationHeader)));
    }
}

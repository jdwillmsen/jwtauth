package com.jdw.jwtauth.controllers;

import com.jdw.jwtauth.models.Role;
import com.jdw.jwtauth.models.RoleDetails;
import com.jdw.jwtauth.services.JwtService;
import com.jdw.jwtauth.services.RolesService;
import com.jdw.jwtauth.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/roles")
public class RolesController {
    private final RolesService rolesService;
    private final UsersService usersService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<Role> getAllRoles() {
        log.debug("Getting all roles");
        return rolesService.getRoles();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{roleId}")
    public Role getRole(@PathVariable Long roleId) {
        log.debug("Getting role with: roleId={}", roleId);
        return rolesService.getRole(roleId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addRole(@RequestBody RoleDetails roleDetails, @RequestHeader(name = "Authorization") String authorizationHeader) {
        log.debug("Adding role with: roleName={}", roleDetails.roleName());
        rolesService.addRole(roleDetails, usersService.getUserId(JwtService.getEmailAddress(authorizationHeader)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{roleId}")
    public void updateRole(@PathVariable Long roleId, @RequestBody RoleDetails roleDetails, @RequestHeader(name = "Authorization") String authorizationHeader) {
        log.debug("Updating role with: roleId={}", roleDetails.roleId());
        rolesService.updateRole(roleId, roleDetails, usersService.getUserId(JwtService.getEmailAddress(authorizationHeader)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{roleId}/users")
    public void addUsersToRole(@PathVariable Long roleId, @RequestBody List<Long> users, @RequestHeader(name = "Authorization") String authorizationHeader) {
        log.debug("Adding users with: roleId={}, users={}", roleId, users);
        rolesService.addUsersToRole(roleId, users, usersService.getUserId(JwtService.getEmailAddress(authorizationHeader)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{roleId}/users")
    public void removeUsersFromRole(@PathVariable Long roleId, @RequestBody List<Long> users, @RequestHeader(name = "Authorization") String authorizationHeader) {
        log.debug("Removing users with: roleId={}, users={}", roleId, users);
        rolesService.removeUsersFromRole(roleId, users, usersService.getUserId(JwtService.getEmailAddress(authorizationHeader)));
    }

}

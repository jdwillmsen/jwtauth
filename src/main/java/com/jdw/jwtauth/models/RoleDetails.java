package com.jdw.jwtauth.models;

public record RoleDetails(
   Long roleId,
   String roleName,
   String roleDescription,
   Boolean active
) {}

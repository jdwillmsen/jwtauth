package com.jdw.jwtauth.models;

import lombok.Builder;

@Builder
public record RoleDetails(
   Long roleId,
   String roleName,
   String roleDescription,
   Boolean active
) {}

package com.jdw.jwtauth.fixtures;

import com.jdw.jwtauth.models.Role;

import java.util.List;
import java.util.Optional;

import static com.jdw.jwtauth.fixtures.RoleDetailsFixture.ROLE_DETAILS;
import static com.jdw.jwtauth.fixtures.SharedFixtures.ROLE_ID_1;
import static com.jdw.jwtauth.fixtures.SharedFixtures.USERS_LIST;

public class RoleFixtures {
    public static final Role ROLE = Role.builder()
            .roleId(ROLE_ID_1)
            .roleDetails(ROLE_DETAILS)
            .users(USERS_LIST)
            .build();
    public static final Role ADD_ROLE = Role.builder()
            .roleDetails(ROLE_DETAILS)
            .build();
    public static final Role UPDATE_ROLE = Role.builder()
            .roleId(ROLE_ID_1)
            .roleDetails(ROLE_DETAILS)
            .build();
    public static final Optional<Role> OPTIONAL_ROLE = Optional.of(ROLE);
    public static final List<Role> ROLE_LIST = List.of(ROLE);
}

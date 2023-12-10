package com.jdw.jwtauth.fixtures;

import com.jdw.jwtauth.models.RoleDetails;

import java.util.List;
import java.util.Optional;

import static com.jdw.jwtauth.fixtures.SharedFixtures.*;

public class RoleDetailsFixture {
    public static final RoleDetails ROLE_DETAILS = RoleDetails.builder()
            .roleId(ROLE_ID_1)
            .roleName(ROLE_NAME)
            .roleDescription(ROLE_DESCRIPTION)
            .active(true)
            .build();
    public static final Optional<RoleDetails> OPTIONAL_ROLE_DETAILS = Optional.of(ROLE_DETAILS);
    public static final List<RoleDetails> ROLE_DETAILS_LIST = List.of(ROLE_DETAILS);
}

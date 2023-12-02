package com.jdw.jwtauth.models;

import lombok.Builder;

@Builder
public record UserDetails(
        Long userId,
        String firstName,
        String lastName,
        String emailAddress,
        String password
) {}

package com.jdw.jwtauth.models;

public record UserDetails(
        Long userId,
        String firstName,
        String lastName,
        String emailAddress,
        String password
) {}

package com.jdw.jwtauth.models;

import lombok.Builder;

@Builder
public record RegisterRequest(
   String firstName,
   String lastName,
   String emailAddress,
   String password
) {}

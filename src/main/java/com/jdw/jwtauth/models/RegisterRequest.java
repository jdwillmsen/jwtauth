package com.jdw.jwtauth.models;

public record RegisterRequest(
   String firstName,
   String lastName,
   String emailAddress,
   String password
) {}

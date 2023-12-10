package com.jdw.jwtauth.models;

import lombok.Builder;

@Builder
public record AuthenticateRequest(String emailAddress, String password) {}

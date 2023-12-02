package com.jdw.jwtauth.models;

public record AuthenticateRequest(String emailAddress, String password) {}

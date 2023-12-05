package com.jdw.jwtauth.models;

import lombok.Builder;

@Builder
public record Token(
        Long tokenId,
        Long userId,
        String token,
        Boolean active
) {}

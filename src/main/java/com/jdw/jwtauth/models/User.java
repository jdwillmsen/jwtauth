package com.jdw.jwtauth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    private UserDetails userDetails;
    private List<Long> roles;
}

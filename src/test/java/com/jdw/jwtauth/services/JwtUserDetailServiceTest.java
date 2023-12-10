package com.jdw.jwtauth.services;

import com.jdw.jwtauth.models.SecurityUser;
import com.jdw.jwtauth.repositories.roles.RolesRepository;
import com.jdw.jwtauth.repositories.users.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static com.jdw.jwtauth.fixtures.SharedFixtures.EMAIL_ADDRESS;
import static com.jdw.jwtauth.fixtures.UserFixtures.OPTIONAL_USER;
import static com.jdw.jwtauth.fixtures.UserFixtures.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailServiceTest {
    @Mock
    UsersRepository usersRepository;
    @Mock
    RolesRepository rolesRepository;
    @InjectMocks
    JwtUserDetailService jwtUserDetailService;

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);
        when(usersRepository.get(anyString())).thenReturn(OPTIONAL_USER);

        UserDetails result = jwtUserDetailService.loadUserByUsername(EMAIL_ADDRESS);

        assertEquals(userDetails.getUsername(), result.getUsername());
        assertEquals(userDetails.getAuthorities(), result.getAuthorities());
        assertEquals(userDetails.getPassword(), result.getPassword());
    }
}
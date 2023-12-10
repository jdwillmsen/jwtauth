package com.jdw.jwtauth.services;

import com.jdw.jwtauth.repositories.roles.RolesRepository;
import com.jdw.jwtauth.repositories.tokens.TokensRepository;
import com.jdw.jwtauth.repositories.users.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;

import static com.jdw.jwtauth.fixtures.AuthenticateRequestFixtures.AUTHENTICATE_REQUEST;
import static com.jdw.jwtauth.fixtures.RegisterRequestFixtures.REGISTER_REQUEST;
import static com.jdw.jwtauth.fixtures.SharedFixtures.TOKEN_STRING;
import static com.jdw.jwtauth.fixtures.SharedFixtures.USER_ID_1;
import static com.jdw.jwtauth.fixtures.TokenFixtures.*;
import static com.jdw.jwtauth.fixtures.UserFixtures.OPTIONAL_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    UsersRepository usersRepository;
    @Mock
    RolesRepository rolesRepository;
    @Mock
    TokensRepository tokensRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    AuthenticationService authenticationService;

    @Test
    void register() {
        when(usersRepository.get(anyString())).thenReturn(OPTIONAL_USER);

        String result = authenticationService.register(REGISTER_REQUEST);

        assertNotNull(result);
    }

    @Test
    void authenticate() {
        when(usersRepository.get(anyString())).thenReturn(OPTIONAL_USER);

        String result = authenticationService.authenticate(AUTHENTICATE_REQUEST);

        assertNotNull(result);
    }

    @Test
    void saveToken() {
        authenticationService.saveToken(USER_ID_1, TOKEN_STRING);

        verify(tokensRepository, times(1)).add(SAVE_TOKEN);
    }

    @Test
    void deactivateActiveTokens() {
        when(tokensRepository.getActive(anyLong())).thenReturn(TOKEN_LIST);

        authenticationService.deactivateActiveTokens(USER_ID_1);

        verify(tokensRepository, times(1)).update(DEACTIVE_TOKEN);
    }
}
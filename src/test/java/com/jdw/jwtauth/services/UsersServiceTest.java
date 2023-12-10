package com.jdw.jwtauth.services;

import com.jdw.jwtauth.models.User;
import com.jdw.jwtauth.repositories.users.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.jdw.jwtauth.fixtures.RegisterRequestFixtures.REGISTER_REQUEST;
import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static com.jdw.jwtauth.fixtures.UserFixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {
    @Mock
    UsersRepository usersRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UsersService usersService;

    @Test
    void getUsers() {
        when(usersRepository.getAll()).thenReturn(USER_LIST);

        List<User> result = usersService.getUsers();

        assertEquals(USER_LIST, result);
    }

    @Test
    void getUser() {
        when(usersRepository.get(anyLong())).thenReturn(OPTIONAL_USER);

        User result = usersService.getUser(USER_ID_1);

        assertEquals(USER, result);
    }

    @Test
    void updateUser() {
        when(passwordEncoder.encode(any())).thenReturn(PASSWORD);
        usersService.updateUser(USER_ID_1, REGISTER_REQUEST, USER_ID_1);

        verify(usersRepository, times(1)).update(UPDATE_USER, USER_ID_1);
    }

    @Test
    void deleteUser() {
        usersService.deleteUser(USER_ID_1, USER_ID_1);

        verify(usersRepository, times(1)).delete(USER_ID_1, USER_ID_1);
    }

    @Test
    void grantUserRoles() {
        usersService.grantUserRoles(USER_ID_1, ROLES_LIST, USER_ID_1);

        verify(usersRepository, times(1)).grantRoles(USER_ID_1, USERS_LIST, USER_ID_1);
    }

    @Test
    void revokeUserRoles() {
        usersService.revokeUserRoles(USER_ID_1, ROLES_LIST, USER_ID_1);

        verify(usersRepository, times(1)).revokeRoles(USER_ID_1, USERS_LIST, USER_ID_1);
    }

    @Test
    void getUserId() {
        when(usersRepository.get(anyString())).thenReturn(OPTIONAL_USER);

        Long result = usersService.getUserId(EMAIL_ADDRESS);

        assertEquals(USER_ID_1, result);
    }
}
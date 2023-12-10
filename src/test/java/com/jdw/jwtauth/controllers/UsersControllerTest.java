package com.jdw.jwtauth.controllers;

import com.jdw.jwtauth.models.User;
import com.jdw.jwtauth.services.JwtService;
import com.jdw.jwtauth.services.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.jdw.jwtauth.fixtures.RegisterRequestFixtures.REGISTER_REQUEST;
import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static com.jdw.jwtauth.fixtures.UserFixtures.USER;
import static com.jdw.jwtauth.fixtures.UserFixtures.USER_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {
    @Mock
    UsersService usersService;
    @InjectMocks
    UsersController usersController;


    @Test
    void getUsers() {
        when(usersService.getUsers()).thenReturn(USER_LIST);

        List<User> response = usersController.getUsers();

        assertEquals(USER_LIST, response);
    }

    @Test
    void getUser() {
        when(usersService.getUser(USER_ID_1)).thenReturn(USER);

        User response = usersController.getUser(USER_ID_1);

        assertEquals(USER, response);
    }

    @Test
    void updateUser() {
        try (MockedStatic<JwtService> jwtServiceMockedStatic = Mockito.mockStatic(JwtService.class)) {
            jwtServiceMockedStatic.when(() -> JwtService.getJwtToken(VALID_BEARER_TOKEN)).thenReturn(EMAIL_ADDRESS);
            when(usersService.getUserId(any())).thenReturn(USER_ID_1);

            usersController.updateUser(USER_ID_1, REGISTER_REQUEST, VALID_BEARER_TOKEN);

            verify(usersService, times(1)).updateUser(USER_ID_1, REGISTER_REQUEST, USER_ID_1);
        }
    }

    @Test
    void deleteUser() {
        try (MockedStatic<JwtService> jwtServiceMockedStatic = Mockito.mockStatic(JwtService.class)) {
            jwtServiceMockedStatic.when(() -> JwtService.getJwtToken(VALID_BEARER_TOKEN)).thenReturn(EMAIL_ADDRESS);
            when(usersService.getUserId(any())).thenReturn(USER_ID_1);

            usersController.deleteUser(USER_ID_1, VALID_BEARER_TOKEN);

            verify(usersService, times(1)).deleteUser(USER_ID_1, USER_ID_1);
        }
    }

    @Test
    void grantUserRoles() {
        try (MockedStatic<JwtService> jwtServiceMockedStatic = Mockito.mockStatic(JwtService.class)) {
            jwtServiceMockedStatic.when(() -> JwtService.getJwtToken(VALID_BEARER_TOKEN)).thenReturn(EMAIL_ADDRESS);
            when(usersService.getUserId(any())).thenReturn(USER_ID_1);

            usersController.grantUserRoles(USER_ID_1, ROLES_LIST, VALID_BEARER_TOKEN);

            verify(usersService, times(1)).grantUserRoles(USER_ID_1, ROLES_LIST, USER_ID_1);
        }
    }

    @Test
    void revokeUserRoles() {
        try (MockedStatic<JwtService> jwtServiceMockedStatic = Mockito.mockStatic(JwtService.class)) {
            jwtServiceMockedStatic.when(() -> JwtService.getJwtToken(VALID_BEARER_TOKEN)).thenReturn(EMAIL_ADDRESS);
            when(usersService.getUserId(any())).thenReturn(USER_ID_1);

            usersController.revokeUserRoles(USER_ID_1, ROLES_LIST, VALID_BEARER_TOKEN);

            verify(usersService, times(1)).revokeUserRoles(USER_ID_1, ROLES_LIST, USER_ID_1);
        }
    }
}
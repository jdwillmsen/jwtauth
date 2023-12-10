package com.jdw.jwtauth.controllers;

import com.jdw.jwtauth.models.Role;
import com.jdw.jwtauth.services.JwtService;
import com.jdw.jwtauth.services.RolesService;
import com.jdw.jwtauth.services.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.jdw.jwtauth.fixtures.RoleDetailsFixture.ROLE_DETAILS;
import static com.jdw.jwtauth.fixtures.RoleFixtures.ROLE;
import static com.jdw.jwtauth.fixtures.RoleFixtures.ROLE_LIST;
import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolesControllerTest {
    @Mock
    RolesService rolesService;
    @Mock
    UsersService usersService;
    @InjectMocks
    RolesController rolesController;

    @Test
    void getAllRoles() {
        when(rolesService.getRoles()).thenReturn(ROLE_LIST);

        List<Role> response = rolesController.getAllRoles();

        assertEquals(response, ROLE_LIST);
    }

    @Test
    void getRole() {
        when(rolesService.getRole(ROLE_ID_1)).thenReturn(ROLE);

        Role response = rolesController.getRole(ROLE_ID_1);

        assertEquals(response, ROLE);
    }

    @Test
    void addRole() {
        try (MockedStatic<JwtService> jwtServiceMockedStatic = Mockito.mockStatic(JwtService.class)) {
            jwtServiceMockedStatic.when(() -> JwtService.getJwtToken(VALID_BEARER_TOKEN)).thenReturn(EMAIL_ADDRESS);
            when(usersService.getUserId(any())).thenReturn(USER_ID_1);

            rolesController.addRole(ROLE_DETAILS, VALID_BEARER_TOKEN);

            verify(rolesService, times(1)).addRole(ROLE_DETAILS, USER_ID_1);
        }
    }

    @Test
    void updateRole() {
        try (MockedStatic<JwtService> jwtServiceMockedStatic = Mockito.mockStatic(JwtService.class)) {
            jwtServiceMockedStatic.when(() -> JwtService.getJwtToken(VALID_BEARER_TOKEN)).thenReturn(EMAIL_ADDRESS);
            when(usersService.getUserId(any())).thenReturn(USER_ID_1);

            rolesController.updateRole(ROLE_ID_1, ROLE_DETAILS, VALID_BEARER_TOKEN);

            verify(rolesService, times(1)).updateRole(ROLE_ID_1, ROLE_DETAILS, USER_ID_1);
        }
    }

    @Test
    void addUsersToRole() {
        try (MockedStatic<JwtService> jwtServiceMockedStatic = Mockito.mockStatic(JwtService.class)) {
            jwtServiceMockedStatic.when(() -> JwtService.getJwtToken(VALID_BEARER_TOKEN)).thenReturn(EMAIL_ADDRESS);
            when(usersService.getUserId(any())).thenReturn(USER_ID_1);

            rolesController.addUsersToRole(ROLE_ID_1, USERS_LIST, VALID_BEARER_TOKEN);

            verify(rolesService, times(1)).addUsersToRole(ROLE_ID_1, USERS_LIST, USER_ID_1);
        }
    }

    @Test
    void removeUsersFromRole() {
        try (MockedStatic<JwtService> jwtServiceMockedStatic = Mockito.mockStatic(JwtService.class)) {
            jwtServiceMockedStatic.when(() -> JwtService.getJwtToken(VALID_BEARER_TOKEN)).thenReturn(EMAIL_ADDRESS);
            when(usersService.getUserId(any())).thenReturn(USER_ID_1);

            rolesController.removeUsersFromRole(ROLE_ID_1, USERS_LIST, VALID_BEARER_TOKEN);

            verify(rolesService, times(1)).removeUsersFromRole(ROLE_ID_1, USERS_LIST, USER_ID_1);
        }
    }
}
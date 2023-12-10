package com.jdw.jwtauth.services;

import com.jdw.jwtauth.models.Role;
import com.jdw.jwtauth.repositories.roles.RolesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.jdw.jwtauth.fixtures.RoleDetailsFixture.ROLE_DETAILS;
import static com.jdw.jwtauth.fixtures.RoleFixtures.*;
import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolesServiceTest {
    @Mock
    RolesRepository rolesRepository;
    @InjectMocks
    RolesService rolesService;

    @Test
    void getRoles() {
        when(rolesRepository.getAll()).thenReturn(ROLE_LIST);

        List<Role> result = rolesService.getRoles();

        assertEquals(ROLE_LIST, result);
    }

    @Test
    void getRole() {
        when(rolesRepository.get(anyLong())).thenReturn(OPTIONAL_ROLE);

        Role result = rolesService.getRole(ROLE_ID_1);

        assertEquals(ROLE, result);
    }

    @Test
    void addRole() {
        rolesService.addRole(ROLE_DETAILS, USER_ID_1);

        verify(rolesRepository, times(1)).add(ADD_ROLE, USER_ID_1);
    }

    @Test
    void updateRole() {
        rolesService.updateRole(ROLE_ID_1, ROLE_DETAILS, USER_ID_1);

        verify(rolesRepository, times(1)).update(UPDATE_ROLE, USER_ID_1);
    }

    @Test
    void addUsersToRole() {
        rolesService.addUsersToRole(ROLE_ID_1, USERS_LIST, USER_ID_1);

        verify(rolesRepository, times(1)).addUsers(ROLE_ID_1, USERS_LIST, USER_ID_1);
    }

    @Test
    void removeUsersFromRole() {
        rolesService.removeUsersFromRole(ROLE_ID_1, USERS_LIST, USER_ID_1);

        verify(rolesRepository, times(1)).removeUsers(ROLE_ID_1, USERS_LIST, USER_ID_1);
    }
}
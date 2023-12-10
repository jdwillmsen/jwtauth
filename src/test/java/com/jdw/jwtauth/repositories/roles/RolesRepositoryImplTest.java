package com.jdw.jwtauth.repositories.roles;

import com.jdw.jwtauth.daos.roles.RolesDao;
import com.jdw.jwtauth.daos.usersroles.UsersRolesDao;
import com.jdw.jwtauth.models.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.jdw.jwtauth.fixtures.RoleDetailsFixture.*;
import static com.jdw.jwtauth.fixtures.RoleFixtures.*;
import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolesRepositoryImplTest {
    @Mock
    RolesDao rolesDao;
    @Mock
    UsersRolesDao usersRolesDao;
    @InjectMocks
    RolesRepositoryImpl rolesRepository;

    @Test
    void getAll() {
        when(rolesDao.getAll()).thenReturn(ROLE_DETAILS_LIST);
        when(usersRolesDao.getUsers(anyLong())).thenReturn(USERS_LIST);

        List<Role> result = rolesRepository.getAll();

        assertEquals(ROLE_LIST, result);
    }

    @Test
    void get() {
        when(rolesDao.get(anyLong())).thenReturn(OPTIONAL_ROLE_DETAILS);
        when(usersRolesDao.getUsers(anyLong())).thenReturn(USERS_LIST);

        Optional<Role> result = rolesRepository.get(ROLE_ID_1);

        assertEquals(OPTIONAL_ROLE, result);
    }

    @Test
    void getNames() {
        when(rolesDao.getNames(ArgumentMatchers.any())).thenReturn(ROLE_NAME_LIST);

        List<String> result = rolesRepository.getNames(ROLES_LIST);

        assertEquals(ROLE_NAME_LIST, result);
    }

    @Test
    void add() {
        when(rolesDao.save(ROLE_DETAILS, USER_ID_1)).thenReturn(OPTIONAL_ROLE_ID_1);

        Optional<Long> result = rolesRepository.add(ROLE, USER_ID_1);

        assertEquals(OPTIONAL_ROLE_ID_1, result);
    }

    @Test
    void update() {
        rolesRepository.update(ROLE, USER_ID_1);

        verify(rolesDao, times(1)).update(ROLE_DETAILS, USER_ID_1);
    }

    @Test
    void addUsers() {
        rolesRepository.addUsers(ROLE_ID_1, USERS_LIST, USER_ID_1);

        verify(usersRolesDao, times(1)).save(USER_ID_1, ROLE_ID_1, USER_ID_1);
    }

    @Test
    void removeUsers() {
        rolesRepository.removeUsers(ROLE_ID_1, USERS_LIST, USER_ID_1);

        verify(usersRolesDao, times(1)).delete(USER_ID_1, ROLE_ID_1, USER_ID_1);
    }
}
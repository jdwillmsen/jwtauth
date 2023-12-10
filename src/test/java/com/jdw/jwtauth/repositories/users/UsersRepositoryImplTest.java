package com.jdw.jwtauth.repositories.users;

import com.jdw.jwtauth.daos.users.UsersDao;
import com.jdw.jwtauth.daos.usersroles.UsersRolesDao;
import com.jdw.jwtauth.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static com.jdw.jwtauth.fixtures.UserDetailsFixtures.*;
import static com.jdw.jwtauth.fixtures.UserFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersRepositoryImplTest {
    @Mock
    UsersDao usersDao;
    @Mock
    UsersRolesDao usersRolesDao;
    @InjectMocks
    UsersRepositoryImpl usersRepository;

    @Test
    void getAll() {
        when(usersDao.getAll()).thenReturn(USER_DETAILS_LIST);
        when(usersRolesDao.getRoles(anyLong())).thenReturn(List.of());

        List<User> result = usersRepository.getAll();

        assertEquals(USER_LIST, result);
    }

    @Test
    void getUserId() {
        when(usersDao.get(anyLong())).thenReturn(OPTIONAL_USER_DETAILS);
        when(usersRolesDao.getRoles(anyLong())).thenReturn(List.of());

        Optional<User> result = usersRepository.get(USER_ID_1);

        assertEquals(OPTIONAL_USER, result);
    }

    @Test
    void getEmailAddress() {
        when(usersDao.get(anyString())).thenReturn(OPTIONAL_USER_DETAILS);
        when(usersRolesDao.getRoles(anyLong())).thenReturn(List.of());

        Optional<User> result = usersRepository.get(EMAIL_ADDRESS);

        assertEquals(OPTIONAL_USER, result);
    }

    @Test
    void add() {
        when(usersDao.save(any(), anyLong())).thenReturn(OPTIONAL_USER_ID_1);

        Optional<Long> result = usersRepository.add(USER, USER_ID_1);

        assertEquals(OPTIONAL_USER_ID_1, result);
    }

    @Test
    void update() {
        usersRepository.update(USER, USER_ID_1);

        verify(usersDao, times(1)).update(USER_DETAILS, USER_ID_1);
    }

    @Test
    void delete() {
        usersRepository.delete(USER_ID_1, USER_ID_1);

        verify(usersDao, times(1)).delete(USER_ID_1, USER_ID_1);
        verify(usersRolesDao, times(1)).delete(USER_ID_1, null, USER_ID_1);
    }

    @Test
    void grantRoles() {
        usersRepository.grantRoles(USER_ID_1, ROLES_LIST, USER_ID_1);

        verify(usersRolesDao, times(1)).save(USER_ID_1, ROLE_ID_1, USER_ID_1);
    }

    @Test
    void revokeRoles() {
        usersRepository.revokeRoles(USER_ID_1, ROLES_LIST, USER_ID_1);

        verify(usersRolesDao, times(1)).delete(USER_ID_1, ROLE_ID_1, USER_ID_1);
    }
}
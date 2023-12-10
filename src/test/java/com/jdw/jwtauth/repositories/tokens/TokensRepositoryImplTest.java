package com.jdw.jwtauth.repositories.tokens;

import com.jdw.jwtauth.daos.tokens.TokensDao;
import com.jdw.jwtauth.models.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static com.jdw.jwtauth.fixtures.TokenFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokensRepositoryImplTest {
    @Mock
    TokensDao tokensDao;
    @InjectMocks
    TokensRepositoryImpl tokensRepository;

    @Test
    void getActive() {
        when(tokensDao.getActive(anyLong())).thenReturn(TOKEN_LIST);

        List<Token> result = tokensRepository.getActive(USER_ID_1);

        assertEquals(TOKEN_LIST, result);
    }

    @Test
    void get() {
        when(tokensDao.get(anyString())).thenReturn(OPTIONAL_TOKEN);

        Optional<Token> result = tokensRepository.get(TOKEN_STRING);

        assertEquals(OPTIONAL_TOKEN, result);
    }

    @Test
    void add() {
        when(tokensDao.save(any())).thenReturn(OPTIONAL_TOKEN_ID_1);

        Optional<Long> result = tokensRepository.add(TOKEN);

        assertEquals(OPTIONAL_TOKEN_ID_1, result);
    }

    @Test
    void update() {
        tokensRepository.update(TOKEN);

        verify(tokensDao, times(1)).update(TOKEN);
    }
}
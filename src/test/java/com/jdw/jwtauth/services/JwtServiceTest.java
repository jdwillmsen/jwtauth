package com.jdw.jwtauth.services;

import com.jdw.jwtauth.models.SecurityUser;
import com.jdw.jwtauth.repositories.roles.RolesRepository;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import static com.jdw.jwtauth.fixtures.SharedFixtures.*;
import static com.jdw.jwtauth.fixtures.UserFixtures.USER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    @Mock
    RolesRepository rolesRepository;

    @Test
    void getEmailAddress() {
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);
        String token = JwtService.generateToken(userDetails);

        String result = JwtService.getEmailAddress("Bearer " + token);

        assertEquals(EMAIL_ADDRESS, result);
    }

    @Test
    void getJwtToken() {
        String result = JwtService.getJwtToken(VALID_BEARER_TOKEN);

        assertEquals(TOKEN_STRING, result);
    }

    @Test
    void extractEmailAddress() {
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);
        String token = JwtService.generateToken(userDetails);

        String result = JwtService.extractEmailAddress(token);

        assertEquals(EMAIL_ADDRESS, result);
    }

    @Test
    void extractClaim() {
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);
        String token = JwtService.generateToken(userDetails);

        String result = JwtService.extractClaim(token, Claims::getSubject);

        assertEquals(EMAIL_ADDRESS, result);
    }

    @Test
    void generateToken() {
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);

        String result = JwtService.generateToken(userDetails);

        assertNotNull(result);
    }

    @Test
    void generateTokenExtraClaims() {
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);

        String result = JwtService.generateToken(new HashMap<>(), userDetails);

        assertNotNull(result);
    }

    @Test
    void isTokenValid() {
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);
        String token = JwtService.generateToken(userDetails);

        boolean result = JwtService.isTokenValid(token, userDetails);

        assertTrue(result);
    }

    @Test
    void isTokenExpired() {
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);
        String token = JwtService.generateToken(userDetails);

        boolean result = JwtService.isTokenExpired(token);

        assertFalse(result);
    }

    @Test
    void extractExpiration() {
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);
        String token = JwtService.generateToken(userDetails);

        Date result = JwtService.extractExpiration(token);

        assertNotNull(result);
    }

    @Test
    void extractAllClaims() {
        UserDetails userDetails = new SecurityUser(USER, rolesRepository);
        String token = JwtService.generateToken(userDetails);

        Claims result = JwtService.extractAllClaims(token);

        assertNotNull(result);
        assertEquals(EMAIL_ADDRESS, result.getSubject());
    }

    @Test
    void getSignInKey() {
        Key result = JwtService.getSignInKey();

        assertNotNull(result);
    }
}
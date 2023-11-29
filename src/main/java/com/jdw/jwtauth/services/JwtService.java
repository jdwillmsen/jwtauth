package com.jdw.jwtauth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    protected static final String SECRET_KEY = generateRsaKey().getPublic().toString().split(" ")[11];
    protected static final long JWT_EXPIRATION_TIME_MS = Duration.of(15, ChronoUnit.MINUTES).toMillis();
    public static String getEmailAddress(String authorizationHeader) {
        log.info("Retrieving email address with: authorizationHeader={}", authorizationHeader);
        return extractEmailAddress(getJwtToken(authorizationHeader));
    }

    public static String getJwtToken(String authorizationHeader) {
        log.info("Retrieving JWT token with: authorizationHeader={}", authorizationHeader);
        return authorizationHeader.substring(7);
    }

    public static String extractEmailAddress(String jwtToken) {
        log.info("Extracting email address with: jwtToken={}", jwtToken);
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public static <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        log.info("Extracting claim with: jwtToken={}", jwtToken);
        return claimsResolver.apply(extractAllClaims(jwtToken));
    }

    public static String generateToken(UserDetails userDetails) {
        log.info("Generating empty extra claims token with: emailAddress={}", userDetails.getUsername());
        return generateToken(new HashMap<>(), userDetails);
    }

    public static String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        log.info("Generating token with: emailAddress={}", userDetails.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME_MS))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        log.info("Checking token validation with: jwtToken={}", jwtToken);
        return extractEmailAddress(jwtToken).equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    public static boolean isTokenExpired(String jwtToken) {
        log.info("Checking token expiration with: jwtToken={}", jwtToken);
        return extractExpiration(jwtToken).before(new Date());
    }

    protected static Date extractExpiration(String jwtToken) {
        log.info("Extracting expiration with: jwtToken={}", jwtToken);
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    protected static Claims extractAllClaims(String jwtToken) {
        log.info("Extracting all claims with: jwtToken={}", jwtToken);
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    protected static Key getSignInKey() {
        log.info("Retrieving sign in key");
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    protected static KeyPair generateRsaKey() {
        log.info("Generating RSA key");
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException ignored) {
            log.error("Failed to generate RSA key");
        }
        return keyPair;
    }
}

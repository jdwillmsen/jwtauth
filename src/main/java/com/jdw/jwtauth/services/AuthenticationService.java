package com.jdw.jwtauth.services;

import com.jdw.jwtauth.exceptions.NotFoundException;
import com.jdw.jwtauth.models.*;
import com.jdw.jwtauth.repositories.roles.RolesRepository;
import com.jdw.jwtauth.repositories.tokens.TokensRepository;
import com.jdw.jwtauth.repositories.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final TokensRepository tokensRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest registerRequest) {
        log.info("Registering user with: firstName={}, lastName={}, emailAddress={}", registerRequest.firstName(), registerRequest.lastName(), registerRequest.emailAddress());
        User user = User.builder()
                .userDetails(UserDetails.builder()
                        .firstName(registerRequest.firstName())
                        .lastName(registerRequest.lastName())
                        .emailAddress(registerRequest.emailAddress())
                        .password(passwordEncoder.encode(registerRequest.password()))
                        .build())
                .build();
        usersRepository.add(user, 1L);
        user = usersRepository
                .get(registerRequest.emailAddress())
                .orElseThrow(() -> new NotFoundException(String.format("User not found with: emailAddress=%s", registerRequest.emailAddress())));
        String jwtToken = JwtService.generateToken(new SecurityUser(user, rolesRepository));
        saveToken(user.getUserId(), jwtToken);
        return jwtToken;
    }

    public String authenticate(AuthenticateRequest authenticateRequest) {
        log.info("Authenticating with: emailAddress={}", authenticateRequest.emailAddress());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticateRequest.emailAddress(),
                authenticateRequest.password()
        ));
        User user = usersRepository.get(authenticateRequest.emailAddress()).orElseThrow(() -> new NotFoundException(String.format("User not found with: emailAddress=%s", authenticateRequest.emailAddress())));
        deactivateActiveTokens(user.getUserId());
        String jwtToken = JwtService.generateToken(new SecurityUser(user, rolesRepository));
        saveToken(user.getUserId(), jwtToken);
        return jwtToken;
    }

    protected void saveToken(Long userId, String jwtToken) {
        log.debug("Saving token with: userId={}, jwtToken={}", userId, jwtToken);
        tokensRepository.add(
                Token.builder()
                        .userId(userId)
                        .token(jwtToken)
                        .build()
        );
    }

    protected void deactivateActiveTokens(Long userId) {
        log.debug("Deactivating active tokens with: userId={}", userId);
        List<Token> activeTokens = tokensRepository.getActive(userId);
        if (activeTokens == null || activeTokens.isEmpty()) return;
        activeTokens.forEach(
                token -> tokensRepository.update(Token.builder()
                        .tokenId(token.tokenId())
                        .active(false)
                        .build()));
    }


}

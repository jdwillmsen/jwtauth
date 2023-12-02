package com.jdw.jwtauth.services;

import com.jdw.jwtauth.models.*;
import com.jdw.jwtauth.repositories.roles.RolesRepository;
import com.jdw.jwtauth.repositories.users.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final RolesRepository rolesRepository;
    public String register(RegisterRequest registerRequest)  {
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
        return JwtService.generateToken(new SecurityUser(user, rolesRepository));
    }

    public String authenticate(AuthenticateRequest authenticateRequest) {
        log.info("Authenticating with: emailAddress={}", authenticateRequest.emailAddress());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticateRequest.emailAddress(),
                authenticateRequest.password()
        ));
        User user = usersRepository.get(authenticateRequest.emailAddress()).orElseThrow();
        return JwtService.generateToken(new SecurityUser(user, rolesRepository));
    }
}

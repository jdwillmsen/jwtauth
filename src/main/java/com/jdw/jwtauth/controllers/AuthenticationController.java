package com.jdw.jwtauth.controllers;

import com.jdw.jwtauth.models.AuthenticateRequest;
import com.jdw.jwtauth.models.RegisterRequest;
import com.jdw.jwtauth.services.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        log.debug("Registering user with: firstName={}, lastName={}, emailAddress={}", request.firstName(), request.lastName(), request.emailAddress());
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthenticateRequest request) {
        log.debug("Authenticating with: emailAddress={}", request.emailAddress());
        return authenticationService.authenticate(request);
    }
}

package com.jdw.jwtauth.services;

import com.jdw.jwtauth.models.Token;
import com.jdw.jwtauth.repositories.tokens.TokensRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokensService implements LogoutHandler {
    private final TokensRepository tokensRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.debug("Logging out user");
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) return;
        final String jwtToken = JwtService.getJwtToken(authorizationHeader);
        tokensRepository.get(jwtToken).ifPresent(token -> tokensRepository.update(Token.builder()
                .tokenId(token.tokenId())
                .active(false)
                .build()));
    }

    public boolean isTokenActive(String jwtToken) {
        log.info("Checking if token is active with: jwtToken={}", jwtToken);
        return tokensRepository.get(jwtToken).map(Token::active).orElse(false);
    }
}
